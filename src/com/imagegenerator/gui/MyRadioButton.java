package com.imagegenerator.gui;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;

public class MyRadioButton extends JRadioButton {
    private int width = 40;
    private int height = 20;

    public MyRadioButton(){
        this.setForeground(Utility.RB_MAIN_COLOR);
        this.setOpaque(false);
        this.setFont(Utility.MAIN_FONT);
        this.setMinimumSize(new Dimension(width+20, height+10));
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        Color red = new Color(150,80,80);
        Color darkerRed = new Color(140,70,70);
        Color green = new Color(50,150,70);
        Color darkerGreen = new Color(40,140,60);

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gradientRed = new GradientPaint(
                0, 0, darkerRed, 0, height, red);
        GradientPaint gradientBlue = new GradientPaint(
                0, 0, darkerGreen, 0, height, green);
        GradientPaint circleGradient = new GradientPaint(
                0, 0, Color.LIGHT_GRAY, 0, height, Color.GRAY);

        if(this.isSelected()){
            g2.setPaint(gradientBlue);
            g2.fillRoundRect(1,1, width, height, height,height);
            g2.setColor(Color.darkGray);
            g2.drawRoundRect(0,0, width+1, height+1, height,height);
            g2.setPaint(circleGradient);
            g2.fillOval(width-(height)+1, 1, height,height);
            g2.setColor(Color.DARK_GRAY);
            g2.drawOval(width-(height)+1, 1, height,height);
        }
        else {
            g2.setPaint(gradientRed);
            g2.fillRoundRect(1,1, width, height, height,height);
            g2.setColor(Color.darkGray);
            g2.drawRoundRect(0,0, width+1, height+1, height,height);
            g2.setPaint(circleGradient);
            g2.fillOval(1, 1, height,height);
            g2.setColor(Color.DARK_GRAY);
            g2.drawOval(1, 1, height,height);
        }

    }
}
