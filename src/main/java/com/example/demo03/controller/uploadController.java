package com.example.demo03.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo03.entity.ImageInfo;
import com.example.demo03.entity.enums.ResponseEnum;
import com.example.demo03.entity.vo.ResponseMessage;
import com.example.demo03.utils.HttpUtils;
import com.example.demo03.utils.ImageUtils;
import com.example.demo03.utils.NumUtils;
import com.example.demo03.utils.ResponseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class uploadController {

    /**
     * 接收前端的请求并返回结果
     * @param imgBase64
     * @return
     */
    @RequestMapping("/uploadImage")
    @ResponseBody
    public ResponseMessage uploadImage(@RequestParam(value="imgBase64",defaultValue="") String imgBase64,@RequestParam String url){
        ImageInfo imageInfo = ImageUtils.decodeBase64(imgBase64);
        ImageInfo tfImageInfo = ImageUtils.channelTransfrom(imageInfo);
        System.out.println(tfImageInfo);
        Map map = new HashMap();
        map.put("input_12",ImageUtils.mapTo4(tfImageInfo.pixel));
        map.put("isTraining",false);
        Map input = new HashMap();
        input.put("inputs",map);
        JSONObject inputs = new JSONObject(input);
        url = url + ":predict";
        String result = null;
        try {
            result = HttpUtils.doPost(url,inputs);
            JSONArray nums = ((JSONObject) JSON.parse(result)).getJSONArray("outputs");
            List<BigDecimal> finalNums = ((JSONArray)nums.get(0)).toJavaList(BigDecimal.class);
            int subject = NumUtils.max(finalNums);
            return ResponseUtils.success(NumUtils.exchange(subject));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtils.error(ResponseEnum.MODEL_ERROR);
        }
    }

    @RequestMapping("/uploadInfo")
    @ResponseBody
    public ResponseMessage uploadInfo(@RequestParam String height,
                             @RequestParam String width,
                             @RequestParam String channel,
                             @RequestParam String url){
        ImageUtils.height = Integer.valueOf(height);
        ImageUtils.width = Integer.valueOf(width);
        ImageUtils.channel = Integer.valueOf(channel);
        try {
            if (HttpUtils.isConnection(url)){
                return ResponseUtils.success(ResponseEnum.BIND_SERVER_SUCCESS,url);
            }else {
                return ResponseUtils.error(ResponseEnum.TIME_OUT);
            }
        } catch (IOException e) {
            return ResponseUtils.error(ResponseEnum.SERVER_ERROR);
        }
    }
}
