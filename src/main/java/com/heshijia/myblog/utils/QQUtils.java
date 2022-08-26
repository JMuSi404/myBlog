package com.heshijia.myblog.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heshijia.myblog.pojo.QQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class QQUtils {

    private  static  String getQQJsonStr(Long qq){
        StringBuilder jsonString=new StringBuilder(  );
        URLConnection connection=null;
        try {
            URL urlObject=new URL("https://r.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?g_tk=1518561325&uins="+qq);
            connection = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"GBK"));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                jsonString.append(inputLine);
            }
            in.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonString.toString().substring("portraitCallBack(".length(),jsonString.length()-1);
    }


    public static QQ getQQInfo(Long qqId) {
        QQ qq = new QQ();
        String jsonString = getQQJsonStr(qqId);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(String.valueOf(qqId));
        qq.setId(qqId);
        qq.setName((String) jsonArray.get(6));
        qq.setAvatar("https://q2.qlogo.cn/headimg_dl?dst_uin="+qqId+"&spec=100");
        return qq;
    }

    public static void main (String[] args) {
        QQ qqInfo = getQQInfo(1870562227L);
        System.out.println(qqInfo );
    }
}
