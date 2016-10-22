package windows;

import common.Config;
import org.jb2011.lnf.beautyeye.ch3_button.BEButtonUI;
import utils.ImageClassify;
import utils.ImageCreate;
import utils.ImageProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 识别窗口
 * Created by ChienHo on 16/10/21.
 */
public class DistinguishPanel extends JPanel{

    private JLabel captcha,answer,percentage;
    private JButton start,right,wrong;
    private int right_counter=0;
    private int total_counter=0;

    DistinguishPanel(){
        setLayout(new BorderLayout());

        captcha=new JLabel();
        captcha.setPreferredSize(new Dimension(Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT));
        add(captcha,BorderLayout.NORTH);

        start=new JButton("开始");
        right=new JButton("正确");
        right.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.green));
        wrong=new JButton("错误");
        wrong.setUI(new BEButtonUI().setNormalColor(BEButtonUI.NormalColor.red));
        add(start,BorderLayout.SOUTH);
        start.addActionListener(e->{
            if(ImageClassify.getSampleCount()==0){

            }else{
                Box info_box=Box.createVerticalBox();
                Box result_box=Box.createHorizontalBox();
                Box answer_box=Box.createHorizontalBox();
                answer=new JLabel("识别结果:");
                percentage=new JLabel("正确率:0%");
                result_box.add(answer);
                result_box.add(percentage);
                answer_box.add(right);
                answer_box.add(wrong);
                info_box.add(result_box);
                info_box.add(answer_box);
                remove(start);
                add(info_box,BorderLayout.SOUTH);
                distinguish();
            }
        });
        right.addActionListener(e->{
            right_counter++;
            distinguish();
        });
        wrong.addActionListener(e->distinguish());
    }

    private void distinguish(){
        percentage.setText("正确率:"+(total_counter>0?right_counter*100.0/total_counter:0)+"%");
        total_counter++;
        new ImageThread().start();
        right.setEnabled(false);
        wrong.setEnabled(false);
    }

    private class ImageThread extends Thread{
        @Override
        public void run() {
            super.run();
            ImageCreate create=new ImageCreate();
            char c;
            BufferedImage image=create.getImage();//生成图像
            captcha.setIcon(new ImageIcon(image));//显示图像
            image=ImageProcess.getBinaryImage(image);//二值化图像
            image=ImageProcess.getCutNoiseImage(image);//降噪
            image=ImageProcess.getCutImage(image);//剪裁图像
            c=ImageClassify.classify(image);
            answer.setText("识别结果:"+c);
            right.setEnabled(true);
            wrong.setEnabled(true);
        }
    }
}
