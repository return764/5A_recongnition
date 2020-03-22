package com.example.demo03.utils;

public class NumUtils {
    /**
     * 返回数组的最大值的下标
     * @param nums
     * @return
     */
    public static int max(Object[] nums){
        int max = 0;
        double maxNum = (double) nums[0];
        for (int i = 0; i < nums.length; i++) {
            double thisNum = (double) nums[i];
            if(maxNum < thisNum){
                maxNum = (double) nums[i];
                max = i;
            }
        }
        return max;
    }

    /**
     * 对应下标就是预测结果
     * @param num
     * @return
     */
    public static String exchange(int num){
        switch (num){
            case 0:
                return "八达岭";
            case 1:
                return "法门寺";
            case 2:
                return "东方明珠";
            case 3:
                return "故宫";
            case 4:
                return "乐山大佛";
            case 5:
                return "兵马俑";
            case 6:
                return "明十三陵";
            case 7:
                return "山海关";
            case 8:
                return "天坛";
            case 9:
                return "颐和园";
            default:
                return "";

        }
    }
}
