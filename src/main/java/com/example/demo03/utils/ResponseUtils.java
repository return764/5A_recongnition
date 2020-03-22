package com.example.demo03.utils;

import com.example.demo03.entity.enums.ResponseEnum;
import com.example.demo03.entity.vo.ResponseMessage;

public class ResponseUtils {

    /**
     * 携带数据的成功
     * @param data
     * @return
     */
    public static ResponseMessage success(Object data){
        ResponseMessage response = new ResponseMessage();
        response.setCode(ResponseEnum.SUCCESS.getCode());
        response.setMsg(ResponseEnum.SUCCESS.getMsg());
        response.setData(data);
        return response;
    }

    /**
     * 不携带数据的成功
     * @return
     */
    public static ResponseMessage success(){
        return success(null);
    }

    /**
     * 自定义错误的返回
     * @param code
     * @param msg
     * @return
     */
    public static ResponseMessage error(Integer code,String msg){
        ResponseMessage response = new ResponseMessage(code,msg,null);
        return response;
    }

    /**
     * 封装好的错误的返回
     * @param responseEnum
     * @return
     */
    public static ResponseMessage error(ResponseEnum responseEnum){
        ResponseMessage response = new ResponseMessage();
        response.setCode(responseEnum.getCode());
        response.setMsg(responseEnum.getMsg());
        return response;
    }
}
