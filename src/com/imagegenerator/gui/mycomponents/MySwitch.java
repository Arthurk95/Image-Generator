package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;

public class MySwitch extends JRadioButton {
    private int width = 40;
    private int height = 20;
    private Color green = new Color(50,150,70);
    private Color darkerGreen = new Color(40,140,60);

    public MySwitch(){
        this.setForeground(Utility.RB_MAIN_COLOR);
        this.setOpaque(false);
        this.setFont(Utility.MAIN_FONT);
    }

    public void paint(Graphics g){
        Graphics2D g2 = (Graphics2D)g;

        GradientPaint gradient, circleGradient;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        circleGradient = new GradientPaint(0, 0, Color.LIGHT_GRAY, 0, height, Color.GRAY);
        if (this.isSelected())
            gradient = new GradientPaint(0, 0, darkerGreen, 0, height, green);
        else
            gradient = new GradientPaint(0, 0, Color.GRAY, 0, height, Color.GRAY);


        g2.setPaint(gradient);
        g2.fillRoundRect(1,1, width, height, height,height);
        g2.setColor(Utility.TF_BG_COLOR);
        g2.drawRoundRect(0,0, width, height, height,height);
        g2.setPaint(circleGradient);
        if(this.isSelected()){
            g2.fillOval(width-height, 0, height,height);
            g2.setColor(Utility.TF_BG_COLOR);
            g2.drawOval(width-height, 0, height,height);
        }
        else {
            g2.fillOval(0, 0, height,height);
            g2.setColor(Utility.BORDER_COLOR);
            g2.drawOval(0, 0, height,height);
        }

        g2.setColor(Utility.TF_FONT_COLOR);
        g2.setFont(Utility.MAIN_FONT);
        g2.drawString(this.getText(), width + 10,height-(height/4));
    }
}
