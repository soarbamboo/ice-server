package com.example.iceserver.entity.OpenAI;

import lombok.Data;

import java.util.List;

/**
 * TODO 文本模型返回响应体
 */
@Data
public class OpenAIResponse {

    private String id;
    private String object;
    private String created;
    private String model;
    private List<OpenAIChoice> choices;
}
