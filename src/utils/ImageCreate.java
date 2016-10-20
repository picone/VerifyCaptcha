package utils;

import common.Config;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by ChienHo on 16/10/20.
 */
public class ImageCreate {

    private char result;//答案
    private Random random=new Random();

    public ImageCreate() {
        result=Config.CAPTCHA_CHAR[random.nextInt(Config.CAPTCHA_CHAR.length)];
    }

    public ImageCreate(char c) {
        result=c;
    }

    /**
     * 生成图片
     * @return BufferedImage
     */
    public BufferedImage getImage() {
        BufferedImage buffer=new BufferedImage(Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB);
        Font font=new Font(Config.FONT_NAME,Font.PLAIN,Config.FONT_SIZE);
        Graphics2D g=buffer.createGraphics();
        //设置背景颜色
        g.setColor(Color.WHITE);
        g.fillRect(0,0,Config.IMAGE_WIDTH, Config.IMAGE_HEIGHT);
        //绘制文字
        g.setColor(new Color(randomInt(Config.FONT_COLOR_MIN_RED,255),randomInt(Config.FONT_COLOR_MIN_GREEN,255),
                randomInt(Config.FONT_COLOR_MIN_BLUE,255)));
        g.setFont(font);
        g.drawString(String.valueOf(result),0,Config.IMAGE_HEIGHT);
        return buffer;
    }

    public char getResult(){
        return result;
    }

    private int randomInt(int min,int max){
        return min+random.nextInt(max-min);
    }
}
