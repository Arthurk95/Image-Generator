package com.imagegenerator.gui;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;

public class MyRadioButton extends JRadioButton {
    private int radius = 14;

    public MyRadioButton(){
        this.setForeground(Utility.RB_MAIN_COLOR);
        this.setOpaque(false);
        this.setFont(Utility.MAIN_FONT);
        this.setAlignmentX(Component.RIGHT_ALIGNMENT);
    }

    public void paint(Graphics g){
        this.setAlignmentX(Component.RIGHT_ALIGNMENT);
        g.setColor(Utility.RB_MAIN_COLOR);
        g.drawOval(0,0,radius, radius);
        if(this.isSelected()){
            g.setColor(Utility.RB_MAIN_COLOR);
            g.fillOval(2,2, radius-4, radius-4);
        }
    }
}
