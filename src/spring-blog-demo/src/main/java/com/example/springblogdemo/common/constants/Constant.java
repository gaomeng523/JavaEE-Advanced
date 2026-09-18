package com.example.springblogdemo.common.constants;

/**
 * 项目通用常量
 */
public class Constant {
    /** 逻辑删除：已删除 */
    public static final int DELETE = 1;
    /** 逻辑删除：未删除 */
    public static final int UNDELETE = 0;

    /** 前端传递 token 的请求头名称（注意要和前端写的完全一致） */
    public static final String USER_TOKEN_HEADER = "User-Token";

    /**
     * 拦截器解析 token 后，把当前登录用户 id 存入 request 域时使用的 key。
     * Controller 通过它拿到「当前登录人」，而不是信任前端传来的 userId。
     */
    public static final String CURRENT_USER_ID = "currentUserId";

    /** JWT 载荷中存放用户 id 的 key */
    public static final String JWT_CLAIM_ID = "id";
    /** JWT 载荷中存放用户名的 key */
    public static final String JWT_CLAIM_USERNAME = "username";
}
