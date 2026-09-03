package com.example.captcha.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ICaptcha;
import com.example.captcha.constant.Constant;
import com.example.captcha.entity.CaptchaProperties;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("captcha")
public class CaptchaController {

//    @Value("${captcha.width}")
//    private Integer width;
//
//    @Value("${captcha.height}")
//    private Integer height;

    @Autowired
    private CaptchaProperties captchaProperties;
    @RequestMapping("/getCaptcha")
    public void getCaptcha(HttpServletResponse response, HttpSession session){
        ICaptcha captcha = CaptchaUtil.createLineCaptcha(captchaProperties.getWidth(),
                captchaProperties.getHeight(),
                captchaProperties.getCodeNum(),
                captchaProperties.getLineCount());
        try {
            response.setContentType("image/png");
//            session.setAttribute(Constant.SESSION_CAPTCHA,captcha.getCode());
            session.setAttribute(captchaProperties.getSession().getSessionName(),captcha.getCode());
            session.setAttribute(captchaProperties.getSession().getSessionDate(), System.currentTimeMillis());
            response.setHeader("Pragma" , "No-cache");
            captcha.write(response.getOutputStream());
            response.getOutputStream();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("check")
    public Boolean check(String captcha,HttpSession session){
        if(!StringUtils.hasLength(captcha)){
            return false;
        }
        String captchaCode = (String) session.getAttribute(captchaProperties.getSession().getSessionName());
        Long startTime = (Long)session.getAttribute(captchaProperties.getSession().getSessionDate());
        if(!StringUtils.hasLength(captchaCode) || startTime == null){
            return false;
        }
        if(captchaCode.equalsIgnoreCase(captcha) && (System.currentTimeMillis() - startTime) < Constant.VILDID_TIME_OUT){
            return true;
        }else {
            return false;
        }
    }
}
