package com.example.demo03.entity;

/**
 * 这是一个描述图片的类
 */
public class ImageInfo {
    public int height;
    public int width;
    public int numBand;//通道数
    public int[] pixel;//像素

    public ImageInfo(){}

    public ImageInfo(int height,int width,int numBand,int[] pixel){
        this.height = height;
        this.width = width;
        this.numBand = numBand;
        this.pixel = pixel;
    }

    /**
     * 调试用 打印每个像素的RGB值
     */
    public void printPixels(){
        for (int i=0;i<pixel.length;i++) {
            System.out.print(pixel[i]+" ");
            if ((i+1)%numBand == 0){
                System.out.println();
            }
            if (i >= 100){
                break;
            }
        }
    }
    @Override
    public String toString() {
        return "高"+height+"宽"+width+"大小"+pixel.length;
    }
}
