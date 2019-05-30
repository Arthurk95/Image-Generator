package com.imagegenerator.gui;

import java.awt.*;

public class MyTextFieldLine extends MyTextField {

    public MyTextFieldLine(){
        super();
    }

    public MyTextFieldLine(String hint){
        super(hint);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g){
        g.setColor(super.getCurrentBorderColor());
        g.fillRect(0, getHeight()-2,getWidth(),getHeight());
    }
}
