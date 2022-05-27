package com.hetongxue.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetongxue.lang.Const;
import com.hetongxue.response.ResponseCode;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: 自定义登录拦截器
 * @ClassNmae: LoginFilter
 * @Author: 何同学
 * @DateTime: 2022-05-26 12:33
 **/
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private String captchaParameter = "captcha";

    public String getCaptchaParameter() {
        return captchaParameter;
    }

    public void setCaptchaParameter(String captchaParameter) {
        this.captchaParameter = captchaParameter;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getRequestURI().equals("/login") && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("请求方法不对");
        }
        try {
            // 获取相关参数
            Map<String, String> userinfo = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = userinfo.get(getUsernameParameter());
            String password = userinfo.get(getPasswordParameter());
            String captcha = userinfo.get(getCaptchaParameter());
            // 验证码验证
            String sessionCode = (String) request.getSession().getAttribute(Const.CAPTCHA_KEY);
            if (!ObjectUtils.isEmpty(captcha) && !ObjectUtils.isEmpty(sessionCode) && captcha.equalsIgnoreCase(sessionCode)) {
                // 认证用户名、密码
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        throw new CaptchaException(ResponseCode.VALIDATION_ERROR.getMessage());
    }
}
