package com.example.iceserver.controller;

import com.example.iceserver.common.Result;
import com.example.iceserver.entity.Qiniu;
import com.example.iceserver.utils.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequestMapping("/api/common")
public class CommonController {


    /**
     * 获取七牛云上传token
     * @return
     */
    @GetMapping("/getToken")
    public Result<Qiniu> getToken(){
        Qiniu qiNiu =  QiniuUtils.getToken();
        return Result.success(qiNiu);
    }
}
