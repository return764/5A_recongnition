package com.example.demo03.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 这是一个发送Http请求的类
 */
public class HttpUtils {
    /**
     * 发送post请求
     * @param url 请求地址
     * @param json 携带的json数据
     * @return 返回结果
     */
    public static String doPost(String url, JSONObject json)throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(json.toJSONString(), headers);
        //  执行HTTP请求
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        return response.getBody();
    }

    public static boolean isConnection(String urlStr) throws IOException {
        URL url;
        int states = -1;
        url = new URL(urlStr);
        HttpURLConnection oc = (HttpURLConnection) url.openConnection();
        oc.setUseCaches(false);
        oc.setConnectTimeout(3000);
        states = oc.getResponseCode();
        if (200 == states){
            return true;
        }
        return false;
    }
}
