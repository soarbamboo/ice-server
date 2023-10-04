package com.example.iceserver.controller;


import com.example.iceserver.common.Result;
import com.example.iceserver.service.OpenAIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/openai")
public class OpenAIController {

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/chat")
    public  Result<String> reqOpenai(@RequestBody Map parmas){
        String prompt = (String) parmas.get("prompt");

        Map<String,Object>  result = openAIService.send(prompt);

        Boolean status = (Boolean) result.get("status");
        String content = (String) result.get("result");

        if(!status){
            return  Result.error(400, (String) result.get("status"));
        }
        return  Result.success(content);
    }
}
