package com.example.iceserver.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result <T> {

    private  Integer code;

    private  String msg;

    private  Boolean status;

    private  T data;

    private String token;

    public Result() {
    }
    public Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode(200);
        result.setMsg("请求成功！");
        result.setStatus(true);
        return result;
    }

    public static  Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code | 400);
        result.setMsg( msg == "" ? "请求失败！" :msg);
        result.setStatus(false);
        return result;
    }
}