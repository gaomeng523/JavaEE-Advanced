package com.example.springblogdemo.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * MD5 加盐加密工具
 *
 * <p><b>存储格式</b>：{@code finalPassword(32位) + salt(32位)}，总长度固定 64 位。
 * 前 32 位是「密码 + 盐」的 MD5 值，后 32 位是盐本身。</p>
 *
 * <p><b>关于安全性</b>：MD5 加盐足以抵挡彩虹表，但 MD5 计算速度极快，
 * 面对暴力破解依然偏弱。学习阶段够用，生产环境请换 BCrypt / Argon2。</p>
 */
@Slf4j
public class Md5Util {

    /** MD5 十六进制字符串的长度 */
    private static final int MD5_LENGTH = 32;

    /** 加盐后存储的总长度：MD5(32) + salt(32) */
    private static final int STORED_LENGTH = MD5_LENGTH * 2;

    /**
     * 对明文密码加盐加密。
     *
     * <p>每次调用都会生成一个全新的随机盐，因此同一个密码两次加密的结果必然不同——
     * 这正是加盐的意义：攻击者无法通过「密文相同」判断两个用户密码是否一致。</p>
     *
     * @param password 明文密码
     * @return 32 位 MD5 值 + 32 位盐，共 64 位
     */
    public static String encrypt(String password) {
        // UUID 去掉横线后正好是 32 位十六进制字符，天然适合当盐
        String salt = UUID.randomUUID().toString().replace("-", "");
        // 把盐拼在密码后面一起做摘要，这样相同密码也会得到不同结果
        String finalPassword = DigestUtils.md5DigestAsHex(
                (password + salt).getBytes(StandardCharsets.UTF_8));
        // 盐必须和密文一起存下来，否则后续无法校验
        return finalPassword + salt;
    }

    /**
     * 校验明文密码是否与数据库中存储的密文匹配。
     *
     * @param inputPassword 用户本次输入的明文密码
     * @param storedPassword 数据库中存储的 64 位密文（MD5 + salt）
     * @return 匹配返回 true，否则 false
     */
    public static Boolean verify(String inputPassword, String storedPassword) {
        if (!StringUtils.hasText(inputPassword) || !StringUtils.hasText(storedPassword)) {
            log.warn("密码校验失败：输入密码或数据库密码为空");
            return false;
        }
        // 长度不符说明数据有问题（比如历史数据没加盐），直接判定失败，避免 substring 越界
        if (storedPassword.length() != STORED_LENGTH) {
            log.warn("密码校验失败：密文长度不是 {} 位，实际 {} 位",
                    STORED_LENGTH, storedPassword.length());
            return false;
        }

        // 注意：存储格式是「密文在前、盐在后」
        // 前 32 位是已存好的摘要，后 32 位才是盐
        String finalPassword = storedPassword.substring(0, MD5_LENGTH);
        String salt = storedPassword.substring(MD5_LENGTH);

        // 用取出的盐重新计算一遍摘要，与数据库里的摘要比对
        // 这里必须是 (输入的密码 + 盐)，顺序要和 encrypt 保持一致
        String computedPassword = DigestUtils.md5DigestAsHex(
                (inputPassword + salt).getBytes(StandardCharsets.UTF_8));

        return computedPassword.equals(finalPassword);
    }
}
