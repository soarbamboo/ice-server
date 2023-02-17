package com.example.iceserver.controller;

import com.example.iceserver.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * 测试！
     * @return
     */
    @GetMapping
    public Result<String> test(){
        return  Result.success("测试成功！");
    }
}
