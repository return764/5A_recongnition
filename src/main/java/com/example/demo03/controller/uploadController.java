package com.example.demo03.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.demo03.entity.ImageInfo;
import com.example.demo03.utils.HttpUtils;
import com.example.demo03.utils.ImageUtils;
import com.example.demo03.utils.NumUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class uploadController {

    static String url ="http://47.94.144.190:8501/v1/models/demo_model:predict";

    /**
     * 接收前端的请求并返回结果
     * @param imgBase64
     * @return
     */
    @RequestMapping("/uploadImage")
    @ResponseBody
    public String uploadImage(@RequestParam(value="imgBase64",defaultValue="") String imgBase64){
        System.out.println("图片长度"+imgBase64.length());
        ImageInfo imageInfo = ImageUtils.decodeBase64(imgBase64);
        ImageInfo tfImageInfo = ImageUtils.channelTransfrom(imageInfo);

        Map map = new HashMap();
        map.put("input",ImageUtils.mapTo4(tfImageInfo.pixel));
        map.put("isTraining",false);
        Map input = new HashMap();
        input.put("inputs",map);
        JSONObject inputs = new JSONObject(input);

        String result = HttpUtils.doPost(url,inputs);

        List nums = (List)((HashMap)JSONUtils.parse(result)).get("outputs");
        Object[] finalNums = ((List)nums.get(0)).toArray();
        int subject = NumUtils.max(finalNums);
        return NumUtils.exchange(subject);
    }

    @RequestMapping("/uploadInfo")
    @ResponseBody
    public String uploadInfo(@RequestParam String height,
                             @RequestParam String width,
                             @RequestParam String channel,
                             @RequestParam String url){

        return "";
    }
}
