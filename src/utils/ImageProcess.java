package utils;

import common.Config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Created by ChienHo on 16/10/20.
 */
public class ImageProcess {

    private static final int GRAY_LEVEL=256;
    private static final float GRAY_WEIGHT_RED=0.3f;
    private static final float GRAY_WEIGHT_GREEN=0.59f;
    private static final float GRAY_WEIGHT_BLUE=0.11f;

    /**
     * 返回二值化后的图像
     * @param source
     * @return 二值化图像
     */
    public static BufferedImage getBinaryImage(BufferedImage source){
        //彩图灰度转换
        int[][] gray=new int[Config.IMAGE_WIDTH][Config.IMAGE_HEIGHT];
        for(int i=0;i<Config.IMAGE_WIDTH;i++){
            for(int j=0;j<Config.IMAGE_HEIGHT;j++){
                Color color=new Color(source.getRGB(i,j));
                gray[i][j]=(int)((color.getRed()*GRAY_WEIGHT_RED+color.getGreen()*GRAY_WEIGHT_GREEN+
                        color.getBlue()*GRAY_WEIGHT_BLUE)/3);
            }
        }
        //计算直方图
        int[] histogram=new int[GRAY_LEVEL];
        for(int i=0;i<Config.IMAGE_WIDTH;i++){
            for(int j=0;j<Config.IMAGE_HEIGHT;j++){
                histogram[gray[i][j]]++;
            }
        }
        //取得直方图中最高的两个波峰之间的最低的波谷作为图像分割阈值
        int threshold=getTrough(histogram);
        //根据阈值二值化图像
        BufferedImage dst=new BufferedImage(Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT,BufferedImage.TYPE_BYTE_BINARY);
        for(int i=0;i<Config.IMAGE_WIDTH;i++){
            for(int j=0;j<Config.IMAGE_HEIGHT;j++){
                dst.setRGB(i,j,gray[i][j]>threshold?0x00FFFFFF:0x00000000);
            }
        }
        return dst;
    }

    /**
     * 剪裁图像空白处
     * @param src
     * @return 剪裁后的图像
     */
    public static BufferedImage getCutImage(BufferedImage src){

    }

    /**
     * 计算数组内两波峰之间波谷的值
     * @param wave
     * @return 波谷的值
     */
    private static int getTrough(int[] wave){
        if(wave.length<=2)return -1;
        LinkedList<Integer> trough=new LinkedList<>(),valley=new LinkedList<>();//分别存放波峰和波谷
        int direction=1;//标记方向是否改变了
        for(int i=1;i<wave.length;i++){
            if((wave[i]-wave[i-1])*direction<0){//方向改变了
                if(direction==1){
                    trough.add(i-1);
                }else{
                    valley.add(i-1);
                }
                direction*=-1;
            }
        }
        if(trough.size()<2||valley.size()<1)return -1;
        trough.sort((o1, o2) -> wave[o2]-wave[o1]);//降序
        int min=trough.get(0);
        for(Integer val:valley){
            if(val<trough.get(0)&&val>trough.get(1)&&wave[val]<wave[min])min=val;
        }
        return min;
    }
}
