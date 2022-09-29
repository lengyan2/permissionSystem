package com.example.permissionsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R {
    private Integer code;

    private String message;

    private Object data;

    private R(){

    }

    // success
    public static  R ok(Object data,String message){
        return  new R(200,message,data);
    }
    public static R ok(Object data){
        return R.ok(data,"success");
    }
    public static R error(Integer code,String message){
        return new R(code,message,"");
    }
}
