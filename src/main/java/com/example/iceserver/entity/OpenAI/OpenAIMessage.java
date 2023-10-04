package com.example.iceserver.entity.OpenAI;

import lombok.Data;


/**
 * TODO 文本模型消息体
 */
@Data
public class OpenAIMessage {
    private String role;
    private String content;
}


