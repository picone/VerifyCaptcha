package windows;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ChienHo on 16/10/20.
 */
public class MainWindow extends JFrame {

    public MainWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("验证码识别");
        setLayout(new BorderLayout());
        JTabbedPane tp=new JTabbedPane();
        tp.add("训练",new TrainPanel());
        tp.add("设置",new SettingPanel());
        add(tp,BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }
}
