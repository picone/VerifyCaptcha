package utils;

import common.Config;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 图像归类(识别)
 * Created by ChienHo on 16/10/21.
 */
public class ImageClassify {
    private static ArrayList<Sample> samples=new ArrayList<>();

    public static char classify(BufferedImage img){
        return classify(transImage(img));
    }

    private static char classify(boolean[] sequence){
        if(samples.size()==0)return 0x00;//未被训练无法识别
        int min_distance=Integer.MAX_VALUE;
        char result=0x00;
        for(Sample sample:samples){
            int distance=EditDistance.distance(sample.sequence,sequence);
            if(distance<min_distance){
                min_distance=distance;
                result=sample.tag;
            }
        }
        return result;
    }

    public static void train(char tag,BufferedImage img){
        train(tag,transImage(img));
    }

    private static void train(char tag, boolean[] sequence){
        samples.add(new Sample(tag,sequence));
    }

    /**
     * @return 样本训练个数
     */
    public static int getSampleCount(){
        return samples.size();
    }

    private static boolean[] transImage(BufferedImage img){
        boolean[] sequence=new boolean[Config.IMAGE_WIDTH*Config.IMAGE_HEIGHT];
        for(int i=0;i<Config.IMAGE_WIDTH;i++){
            for(int j=0;j<Config.IMAGE_HEIGHT;j++){
                sequence[i*Config.IMAGE_WIDTH+j]=img.getRGB(i,j)==-0x1000000;
            }
        }
        return sequence;
    }

    private static class Sample{
        char tag;
        boolean[] sequence;

        Sample(char tag, boolean[] sequence){
            this.tag=tag;
            this.sequence=sequence;
        }
    }
}
