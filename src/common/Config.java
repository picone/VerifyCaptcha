package common;

/**
 * 配置文件
 * Created by ChienHo on 16/10/20.
 */
public class Config {
    public final static int IMAGE_WIDTH=90;//图片宽度
    public final static int IMAGE_HEIGHT=100;//图片高度
    public final static char[] CAPTCHA_CHAR={
            '0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q',
            'R','S','T','U','V','W','X','Y','Z'
    };//图片文字
    public final static String FONT_NAME=null;//字体
    public final static int FONT_SIZE=100;//字体大小
    public final static int FONT_COLOR_MIN_RED=100;//字体颜色[100,255]
    public final static int FONT_COLOR_MIN_GREEN=100;
    public final static int FONT_COLOR_MIN_BLUE=100;
    public final static int CUT_THRESHOLD=1;//剪裁时限制的阈值
    public final static int NOISE_NUMBER=50;//噪点个数
    public final static int NOISE_SIZE=4;//噪点大小
    public final static int CHAR_THRESHOLD=1000;//字符大小阈值
}
