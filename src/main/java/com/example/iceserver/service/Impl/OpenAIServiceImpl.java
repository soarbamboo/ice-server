package com.example.iceserver.service.Impl;

import cn.hutool.core.convert.ConvertException;
import cn.hutool.http.*;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.example.iceserver.service.OpenAIService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${ChatGPT.variables.apiKey}")
    private String apiKey;
    @Value("${ChatGPT.variables.maxTokens}")
    private String maxTokens;
    @Value("${ChatGPT.variables.model}")
    private String model;
    @Value("${ChatGPT.variables.temperature}")
    private String temperature;
    @Value("${ChatGPT.variables.reqUrl}")
    private String reqUrl;


    @Override
    public Map<String,Object> send(String prompt) {

        Map<String,Object> parmMap = new HashMap<>();
        parmMap.put("model", model);
        parmMap.put("max_tokens", Integer.parseInt(maxTokens));
        parmMap.put("temperature", Double.parseDouble(temperature));
        List<Map<String,String>> dataList =  new ArrayList<>();
        dataList.add(new HashMap<String,String>(){{
            put("role","user");
            put("content",prompt);
        }});
        parmMap.put("messages",dataList);

        Map<String,Object> resultMap = new HashMap<>();
        JSONObject message = null;
        try{
            String body = HttpRequest.post(reqUrl)
                    .header(Header.AUTHORIZATION, "Bearer " + apiKey)
                    .header(Header.CONTENT_TYPE, "application/json")
                    .body(JSONUtil.toJsonStr(parmMap))
                    .execute()
                    .body();

            JSONObject jsonObject  = JSONUtil.parseObj(body);
            log.info("jsonObject:",body);
            JSONArray choices = jsonObject.getJSONArray("choices");
            if(choices == null){
                log.info(String.valueOf(jsonObject.getJSONObject("error")));
                resultMap.put("status",false);
                resultMap.put("msg",jsonObject.getJSONObject("error").getStr("code"));
                return resultMap;
            }else {
                JSONObject result = choices.get(0,JSONObject.class,Boolean.TRUE);
                message = result.getJSONObject("message");
            }
        }catch (HttpException e){
            resultMap.put("status",false);
            resultMap.put("msg",e.getMessage());
            return resultMap;
        }catch (ConvertException e){
            resultMap.put("status",false);
            resultMap.put("msg",e.getMessage());
            return resultMap;
        }
        resultMap.put("status",true);
        resultMap.put("result",message.getStr("content"));
        return  resultMap;
    }
//    @Override
//    public List<OpenAIData> sendImg(String prompt) {
//
//    }
}
