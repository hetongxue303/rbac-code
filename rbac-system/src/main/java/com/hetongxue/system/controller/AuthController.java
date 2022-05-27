package com.hetongxue.system.controller;

import com.hetongxue.lang.Const;
import com.hetongxue.response.Result;
import com.hetongxue.utils.CaptchaUtil;
import com.hetongxue.utils.HttpUtil;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 安全模块
 * @ClassNmae: AuthController
 * @Author: 何同学
 * @DateTime: 2022-05-23 19:34
 **/
@RestController
@RequiredArgsConstructor
public class AuthController {

    /**
     * 获取验证码
     **/
    @GetMapping("/getVerify")
    public Result getVerify() {
        Captcha captcha = CaptchaUtil.getCaptcha();
        HttpUtil.getSession().setAttribute(Const.CAPTCHA_KEY, captcha.text());
        // 存入redis  这里先使用session进行验证 后期在更换
        return Result.Success(captcha.toBase64());
    }

}
