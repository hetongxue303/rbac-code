package com.hetongxue.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hetongxue.lang.Const;
import com.hetongxue.response.ResponseCode;
import com.hetongxue.response.Result;
import com.hetongxue.security.CaptchaException;
import com.hetongxue.security.CustomizeUserDetailsService;
import com.hetongxue.security.LoginFilter;
import com.hetongxue.system.domain.User;
import com.hetongxue.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description: SpringSecurity配置类
 * @ClassNmae: SpringSecurityConfiguration
 * @Author: 何同学
 * @DateTime: 2022-05-25 19:05
 **/
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final String[] WHITE_LIST = {"/login", "/getVerify"};
    private final JwtUtils jwtUtils;
    private final CustomizeUserDetailsService customizeUserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {// 自定义密码加密方式
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {// 暴露自定义的认证管理器
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {// 使用数据库数据源进行认证
        auth.userDetailsService(customizeUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/login");// 自定义登录请求
        loginFilter.setUsernameParameter("username");// 设置用户名属性名
        loginFilter.setPasswordParameter("password");// 设置密码属性名
        loginFilter.setCaptchaParameter("verifyCode");// 设置验证码属性名
        loginFilter.setAuthenticationManager(authenticationManagerBean());// 指定暴露的认证管理器
        loginFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {// 认证成功处理
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.OK.value());
            response.setHeader(Const.AUTHORIZATION_KEY, jwtUtils.generateToken(((User) authentication.getPrincipal()).getUsername()));
            response.getWriter().println(new ObjectMapper().writeValueAsString(Result.Success().setMessage("登陆成功")));
        });
        loginFilter.setAuthenticationFailureHandler((request, response, exception) -> { // 认证失败处理
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpStatus.OK.value());
            Result result = Result.Fail();
            if (exception instanceof AccountExpiredException) result.setMessage("登录过期");
            if (exception instanceof BadCredentialsException) result.setMessage("用户名或密码错误");
            if (exception instanceof CredentialsExpiredException) result.setMessage("密码过期");
            if (exception instanceof DisabledException) result.setMessage("账户被禁用");
            if (exception instanceof LockedException) result.setMessage("账户被锁");
            if (exception instanceof AuthenticationServiceException) result.setMessage(exception.getMessage());
            if (exception instanceof InternalAuthenticationServiceException) result.setMessage("账户不存在");
            if (exception instanceof CaptchaException)
                result.setMessage(exception.getMessage()).setCode(ResponseCode.VALIDATION_ERROR.getCode());
            response.getWriter().println(new ObjectMapper().writeValueAsString(result));
        });
        return loginFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()// 开启权限认证
                .antMatchers(WHITE_LIST).permitAll()// 设置过滤白名单
                .anyRequest().authenticated()// 除白名单外所有请求认证
                .and()
                .formLogin()// 使用form表单登录
                .and()
                .logout()// 开启注销
                .logoutUrl("/logout")// 设置注销URL
                .logoutSuccessHandler((request, response, authentication) -> { // 注销处理
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.OK.value());
                    response.getWriter().println(new ObjectMapper().writeValueAsString(Result.Success().setMessage("注销成功")));
                })
                .and()
                .exceptionHandling()// 开启异常处理
                .authenticationEntryPoint((request, response, exception) -> {// 未登录访问异常处理
                    System.out.println(exception.getMessage());
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().println(new ObjectMapper().writeValueAsString(Result.Fail()
                            .setCode(ResponseCode.UNAUTHORIZED.getCode())
                            .setMessage(ResponseCode.UNAUTHORIZED.getMessage())));
                })
                .accessDeniedHandler((request, response, accessDeniedException) -> {// 未授权访问异常处理
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().println(new ObjectMapper().writeValueAsString(Result.Fail()
                            .setCode(ResponseCode.FORBIDDEN.getCode())
                            .setMessage(ResponseCode.FORBIDDEN.getMessage())));
                })
                .and()
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class)// 使用自定义登录拦截器 替换 UsernamePasswordAuthenticationFilter
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 关闭session
                .and().cors()// 开启跨域
                .and().csrf().disable();// 关闭csrf攻击
    }
}