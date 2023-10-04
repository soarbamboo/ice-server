package com.example.iceserver.entity.OpenAI;


import lombok.Data;

import java.util.List;


/**
 * TODO 问题请求体
 */
@Data
public class OpenAIRequest {

    /*** 问题*/
    private String askStr;
    /*** 文本回答*/
    private String replyStr;
    /*** 图形回答*/
    private List<OpenAIData> replyImg;
}
