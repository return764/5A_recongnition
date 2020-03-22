package com.example.demo03.utils;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;

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
    public static String doPost(String url,JSONObject json){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<String>(json.toString(), headers);
        //  执行HTTP请求
        ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);
        return response.getBody();
    }

}
