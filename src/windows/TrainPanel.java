package windows;

import common.Config;
import utils.ImageCreate;
import utils.ImageProcess;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by ChienHo on 16/10/20.
 */
public class TrainPanel extends JPanel {

    private JLabel captcha,answer;
    private JTextField command;
    private JLabel right_text,wrong_text,percentage_text;

    public TrainPanel(){
        setLayout(new BorderLayout());
        Box train_box=Box.createVerticalBox();//左侧训练区
        Box info_box=Box.createVerticalBox();//右侧识别区

        captcha=new JLabel();
        captcha.setSize(Config.IMAGE_WIDTH,Config.IMAGE_HEIGHT);
        train_box.add(captcha);
        answer=new JLabel("正确答案:");
        train_box.add(answer);
        command=new JTextField();
        train_box.add(command);
        add(train_box,BorderLayout.CENTER);

        right_text=new JLabel("答对题数:0");
        info_box.add(right_text);
        wrong_text=new JLabel("答错题数:0");
        info_box.add(wrong_text);
        percentage_text=new JLabel("正确率:0%");
        info_box.add(percentage_text);
        add(info_box,BorderLayout.EAST);

        train();//开始训练
    }

    private void train(){
        ImageCreate create=new ImageCreate();
        BufferedImage image=create.getImage();
        image=ImageProcess.getBinaryImage(image);

        captcha.setIcon(new ImageIcon(image));
        answer.setText("正确答案:"+create.getResult());

        //ImageProcess.getBinaryImage(image);
    }
}
