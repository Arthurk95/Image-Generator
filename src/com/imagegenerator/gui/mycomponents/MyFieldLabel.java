package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;

public class MyFieldLabel extends JLabel {
    public MyFieldLabel(){
        this.setFont(Utility.MAIN_FONT);
        this.setOpaque(false);
        this.setForeground(new Color(212,212,212));
    }
}
