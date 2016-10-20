package windows;

import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;

/**
 * Created by ChienHo on 16/10/20.
 */
public class Main {

    public static void main(String[] args){
        try {
            BeautyEyeLNFHelper.frameBorderStyle=BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
            BeautyEyeLNFHelper.debug=true;
            BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
            new MainWindow().setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
