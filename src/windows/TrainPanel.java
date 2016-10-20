package windows;

import common.Config;
import utils.ImageClassify;
import utils.ImageCreate;
import utils.ImageProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

/**
 * 训练Panel
 * Created by ChienHo on 16/10/20.
 */
public class TrainPanel extends JPanel {

    private JLabel captcha_before,captcha_after;
    private JProgressBar progress_bar;
    private JButton start;

    public TrainPanel(){
        setLayout(new BorderLayout());
        Box captcha_box=Box.createHorizontalBox();//验证码区

        captcha_before=new JLabel();
        captcha_before.setPreferredSize(new Dimension(Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT));
        captcha_box.add(captcha_before);
        captcha_box.add(Box.createHorizontalStrut(2));
        captcha_after=new JLabel();
        captcha_after.setPreferredSize(new Dimension(Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT));
        captcha_box.add(captcha_after);
        add(captcha_box,BorderLayout.CENTER);

        start=new JButton("开始训练");
        add(start,BorderLayout.SOUTH);

        start.addActionListener(e -> {
            remove(start);
            progress_bar=new JProgressBar();
            progress_bar.setMaximum(Config.CAPTCHA_CHAR.length);
            add(progress_bar,BorderLayout.SOUTH);
            train();//开始训练
        });
    }

    private void train(){
        for(char c:Config.CAPTCHA_CHAR){
            ImageCreate create=new ImageCreate(c);
            BufferedImage image=create.getImage();//生成图像
            captcha_before.setIcon(new ImageIcon(image));
            image=ImageProcess.getBinaryImage(image);//二值化图像
            image=ImageProcess.getCutImage(image);//剪裁图像
            captcha_after.setIcon(new ImageIcon(image));//显示图像
            progress_bar.setValue(progress_bar.getValue()+1);
            ImageClassify.train(create.getResult(),image);
        }
    }
}
