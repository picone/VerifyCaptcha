package utils;

import common.Config;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 图像处理
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
                dst.setRGB(i,j,gray[i][j]>threshold?0xFFFFFF:0x00000000);
            }
        }
        return dst;
    }

    /**
     * 使用广度优先遍历找到所属字符路径
     * @param src
     * @return 剪裁后的字符
     */
    public static BufferedImage getCutNoiseImage(BufferedImage src){
        HashMap<String,Boolean> visited=new HashMap<>();
        LinkedList<Point> queue=new LinkedList<>();
        BufferedImage dst=new BufferedImage(Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT,BufferedImage.TYPE_BYTE_BINARY);
        boolean has_found=false;
        Graphics g=dst.getGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0,0,Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT);
        for(int i=0;i<Config.IMAGE_WIDTH&&!has_found;i++){
            for(int j=0;j<Config.IMAGE_HEIGHT&&!has_found;j++){
                if(src.getRGB(i,j)==-0x1000000){
                    queue.clear();
                    visited.clear();
                    queue.add(new Point(i,j));
                    while(!queue.isEmpty()){
                        Point p=queue.poll();
                        if(visited.containsKey(p.x+","+p.y))continue;
                        visited.put(p.x+","+p.y,true);
                        //检查8个方向的点是否有像素
                        if(p.x-1>=0&&src.getRGB(p.x-1,p.y)==-0x1000000&&!visited.containsKey((p.x-1)+","+p.y))queue.add(new Point(p.x-1,p.y));
                        if(p.y-1>=0&&src.getRGB(p.x,p.y-1)==-0x1000000&&!visited.containsKey(p.x+","+(p.y-1)))queue.add(new Point(p.x,p.y-1));
                        if(p.x+1<Config.IMAGE_WIDTH&&src.getRGB(p.x+1,p.y)==-0x1000000&&!visited.containsKey((p.x+1)+","+p.y))queue.add(new Point(p.x+1,p.y));
                        if(p.y+1<Config.IMAGE_HEIGHT&&src.getRGB(p.x,p.y+1)==-0x1000000&&!visited.containsKey(p.x+","+(p.y+1)))queue.add(new Point(p.x,p.y+1));
                        if(p.x-1>=0&&p.y-1>=0&&src.getRGB(p.x-1,p.y-1)==-0x1000000&&!visited.containsKey((p.x-1)+","+(p.y-1)))queue.add(new Point(p.x-1,p.y-1));
                        if(p.x-1>=0&&p.y+1<Config.IMAGE_HEIGHT&&src.getRGB(p.x-1,p.y+1)==-0x1000000&&!visited.containsKey((p.x-1)+","+(p.y+1)))queue.add(new Point(p.x-1,p.y+1));
                        if(p.x+1<Config.IMAGE_WIDTH&&p.y-1>=0&&src.getRGB(p.x+1,p.y-1)==-0x1000000&&!visited.containsKey((p.x+1)+","+(p.y-1)))queue.add(new Point(p.x+1,p.y-1));
                        if(p.x+1<Config.IMAGE_WIDTH&&p.y+1<Config.IMAGE_HEIGHT&&src.getRGB(p.x+1,p.y+1)==-0x1000000&&!visited.containsKey((p.x+1)+","+(p.y+1)))queue.add(new Point(p.x+1,p.y+1));
                    }
                    if(visited.size()>Config.CHAR_THRESHOLD){
                        for(Map.Entry<String,Boolean> val:visited.entrySet()){
                            String[] point=val.getKey().split(",");
                            dst.setRGB(Integer.parseInt(point[0]),Integer.parseInt(point[1]),0x00000000);
                        }
                        has_found=true;
                    }
                }
            }
        }
        return dst;
    }

    /**
     * 剪裁图像空白处并缩放至合适的大小
     * @param src
     * @return 剪裁后的图像
     */
    public static BufferedImage getCutImage(BufferedImage src){
        int top,bottom,left,right;
        int blank;
        //获取上方空白像素数量
        for(blank=top=0;blank<Config.CUT_THRESHOLD&&top<Config.IMAGE_HEIGHT;top++){
            for(int i=0;i<Config.IMAGE_WIDTH;i++){
                if(src.getRGB(i,top)==-0x1000000){
                    blank++;
                    break;
                }
            }
        }
        //获取左方空白像素数量
        for(blank=left=0;blank<Config.CUT_THRESHOLD&&left<Config.IMAGE_WIDTH;left++){
            for(int i=top;i<Config.IMAGE_HEIGHT;i++){
                if(src.getRGB(left,i)==-0x1000000){
                    blank++;
                    break;
                }
            }
        }
        //获取右方空白位置
        for(blank=0,right=Config.IMAGE_WIDTH-1;blank<Config.CUT_THRESHOLD&&right>=0;right--){
            for(int i=top;i<Config.IMAGE_HEIGHT;i++){
                if(src.getRGB(right,i)==-0x1000000){
                    blank++;
                    break;
                }
            }
        }
        //获取底部空白位置
        for(blank=0,bottom=Config.IMAGE_HEIGHT-1;blank<Config.CUT_THRESHOLD&&bottom>=0;bottom--){
            for(int i=left;i<right;i++){
                if(src.getRGB(i,bottom)==-0x1000000){
                    blank++;
                    break;
                }
            }
        }
        //切割并缩放图片
        BufferedImage dst=new BufferedImage(Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT,BufferedImage.TYPE_BYTE_BINARY);
        if(right>left&&bottom>top)
            dst.getGraphics().drawImage(src.getSubimage(left,top,right-left,bottom-top),0,0,Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT,null);
        return dst;
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

    private static class Point{
        int x;
        int y;

        Point(int x,int y){
            this.x=x;
            this.y=y;
        }
    }
}
