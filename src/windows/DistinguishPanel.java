package windows;

import common.Config;
import utils.EditDistance;
import utils.ImageClassify;
import utils.ImageCreate;
import utils.ImageProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        add(captcha,BorderLayout.WEST);

        start=new JButton("开始");
        add(start,BorderLayout.EAST);
        start.addActionListener(e->{
            Box info_box=Box.createVerticalBox();
            Box answer_box=Box.createHorizontalBox();
            answer=new JLabel("识别结果:");
            info_box.add(answer);
            right=new JButton("正确");
            wrong=new JButton("错误");
            answer_box.add(right);
            answer_box.add(wrong);
            info_box.add(answer_box);
            percentage=new JLabel("正确率0%");
            info_box.add(percentage);
            remove(start);
            add(info_box,BorderLayout.EAST);
            distinguish();
        });
    }

    private void distinguish(){
        ImageCreate create=new ImageCreate();
        char answer;
        BufferedImage image=create.getImage();//生成图像
        captcha.setIcon(new ImageIcon(image));//显示图像
        image= ImageProcess.getBinaryImage(image);//二值化图像
        image=ImageProcess.getCutImage(image);//剪裁图像
        answer=ImageClassify.classify(image);
        this.answer.setText("识别结果:"+answer);
    }
}
