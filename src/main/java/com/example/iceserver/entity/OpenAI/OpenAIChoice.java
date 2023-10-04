package com.example.iceserver.entity.OpenAI;
import com.google.protobuf.Message;
import lombok.Data;

/**
 * TODO 文本模型返回内容
 */
@Data
public class OpenAIChoice {
    private String text;
    private Integer index;
    private Message message;
}
