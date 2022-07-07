package com.heshijia.myblog.pojo;

import lombok.Data;

import java.util.HashMap;

@Data
public class Msg {
    private String code;
    private  String message;

    private HashMap<String,Object>  hashMap = new HashMap<String,Object>();
}
