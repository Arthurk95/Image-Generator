package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;

public class MyFieldLabel extends JLabel {
    public MyFieldLabel(){
        this.setFont(Utility.MAIN_FONT);
        this.setOpaque(false);
        this.setForeground(Utility.MAIN_LABEL_COLOR);
    }
}
