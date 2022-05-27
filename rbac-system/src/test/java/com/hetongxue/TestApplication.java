package com.hetongxue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description: 测试类
 * @ClassNmae: TestApplication
 * @Author: 何同学
 * @DateTime: 2022-05-23 19:44
 **/
@SpringBootTest
public class TestApplication {

    @Test
    void test() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("testPassword"));
    }

}
