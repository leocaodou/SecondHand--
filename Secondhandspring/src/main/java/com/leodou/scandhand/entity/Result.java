package com.leodou.scandhand.entity;

import lombok.Data;

@Data
public class Result {
    //相应码
    private Integer code;
    //信息
    private String message;
    //返回数据
    private Object data;
    
    public Result(){
    	code = 0;
    	message = "failed";
    	data = null;
    }
}