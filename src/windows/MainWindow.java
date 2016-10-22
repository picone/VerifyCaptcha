package windows;

import javax.swing.*;
import java.awt.*;

/**
 * 主窗体
 * Created by ChienHo on 16/10/20.
 */
class MainWindow extends JFrame {

    MainWindow(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("图像识别");
        setLayout(new BorderLayout());
        JTabbedPane tp=new JTabbedPane();
        tp.add("训练",new TrainPanel());
        tp.add("识别",new DistinguishPanel());
        add(tp,BorderLayout.CENTER);
        pack();
        setSize(200,230);
        setLocationRelativeTo(null);
    }
}
