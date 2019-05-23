package com.imagegenerator.gui;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {
    public static final Color BORDER_COLOR = new Color(100,100,100);
    public static final Color BG_COLOR = new Color(58,58,58);
    public static final Color FG_COLOR = new Color(207,207,207);
    public static final Color DISABLED_BG_COLOR = new Color(32,32,32);
    public static final Color DISABLED_FG_COLOR = new Color(50,50,50);

    public MyTextField(){
        format();
    }

    public void format(){
        this.setBackground(BG_COLOR);
        this.setForeground(FG_COLOR);
        normalBorder();
        Font font = new Font("Courier New", Font.PLAIN,12);
        this.setFont(font);
    }

    public void redBorder(){
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
    }

    public void normalBorder(){
        this.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    }
}
