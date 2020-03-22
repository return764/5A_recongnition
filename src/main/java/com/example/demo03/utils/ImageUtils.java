package com.example.demo03.utils;

import com.example.demo03.entity.ImageInfo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 图片处理的相关类
 */
public class ImageUtils {
    /**
     * 将Base64转化成ImageInfo对象
     * @param s Base64码
     * @return
     */
    public static ImageInfo decodeBase64(String s) {
        Base64.Decoder decoder = Base64.getDecoder();
        String imgStr = s.substring(s.indexOf(",")+1);
        FileOutputStream fout = null;
        ImageInfo imageInfo = null;
        byte[] b = decoder.decode(imgStr);
        File file = null;
        // 处理数据
        for (int i = 0; i < b.length; ++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        try {
            file = File.createTempFile("temp",null);
            fout = new FileOutputStream(file);
            fout.write(b);
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fout != null){
                try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            BufferedImage biBefore = ImageIO.read(file);
            BufferedImage bi = zoomImage(biBefore,224);
            System.out.println("该图片是"+bi.getData().getNumBands()+"通道");
            //获取像素值
            int[] piexels = bi.getData().getPixels(0,0,bi.getHeight(),bi.getWidth(),new int[bi.getHeight()*bi.getWidth()*bi.getData().getNumBands()]);
            imageInfo = new ImageInfo(bi.getHeight(),bi.getWidth(),bi.getData().getNumBands(),piexels);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return imageInfo;
    }

    /**
     * RGBA转RGB
     */
    public static ImageInfo channelTransfrom(ImageInfo imageInfo){
        ImageInfo tfImageInfo = null;
        int[] pixels = null;
        AtomicInteger index = new AtomicInteger(0);
        if (imageInfo.numBand == 4){
                pixels = Arrays.stream(imageInfo.pixel).filter(x -> {
                if ((index.getAndIncrement()+1)%4==0){
                    return false;
                } else{
                    return true;
                }}).toArray();
            tfImageInfo = new ImageInfo(imageInfo.height,imageInfo.width,3,pixels);
            return tfImageInfo;
        }else {
            return imageInfo;
        }
    }

    /**
     * 将1*n的像素转化成1*224*224*3
     * @param pixels 图片的像素
     * @return
     */
    public static int[][][][] mapTo4(int[] pixels){
        int[][][][] martix = new int[1][224][224][3];
        int num = 0;
        for (int i = 0; i < 224; i++) {
            for (int j = 0;j < 224;j++){
                for (int k = 0;k < 3;k++){
                    martix[0][i][j][k] = pixels[num];
                    num++;
                }
            }
        }
        return martix;
    }

    /**
     * 图片缩放的方法
     * @param bi 图片对象
     * @param wh 缩放后的长宽值
     * @return
     */
    public static BufferedImage zoomImage(BufferedImage bi,int wh){
        Image imageTemp = bi.getScaledInstance(wh,wh,BufferedImage.SCALE_DEFAULT);
        BufferedImage newBufferImage = new BufferedImage(wh, wh, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newBufferImage.createGraphics();
        graphics.drawImage(imageTemp,0,0,null);
        graphics.dispose();
        return newBufferImage;
    }

}
