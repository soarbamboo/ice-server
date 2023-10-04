package com.example.iceserver.service;

import com.example.iceserver.entity.OpenAI.OpenAIData;

import java.util.List;
import java.util.Map;

public interface OpenAIService {
        /**
         * 调用文本模型ai
         * @param prompt
         * @return
         */
        public Map<String,Object> send(String prompt);

        /**
         * 调用图片模型ai
         * @param prompt
         * @return
         */
//        public List<OpenAIData> sendImg(String prompt);
}
