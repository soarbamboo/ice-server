package com.example.iceserver.common;

import lombok.Data;

@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
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
        result.setMsg("成功");
        result.setStatus(true);
        return result;
    }

    public static <T> Result<T> successByToken(T data,String token){
        Result<T> result = new Result<>(data);
        result.setCode(200);
        result.setMsg("成功");
        result.setToken(token);
        result.setStatus(true);
        return result;
    }

    public static  Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code | 400);
        result.setMsg( msg == "" ? "xx" :msg);
        result.setStatus(false);
        return result;
    }
}