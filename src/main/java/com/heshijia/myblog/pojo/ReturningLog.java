package com.heshijia.myblog.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturningLog {
   private  String url;
    private  String ip;
    private  String classMethod;
    private  Object[] args;

}
