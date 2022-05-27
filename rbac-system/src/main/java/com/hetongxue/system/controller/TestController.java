package com.hetongxue.system.controller;

import com.hetongxue.response.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 测试模块
 * @ClassNmae: TestController
 * @Author: 何同学
 * @DateTime: 2022-05-23 19:37
 **/
@RestController
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/test")
    public Result test() {
        return Result.Success();
    }

}
