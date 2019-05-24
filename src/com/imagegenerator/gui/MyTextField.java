package com.imagegenerator.gui;

import javax.swing.*;
import java.awt.*;

public class MyTextField extends JTextField {
    public static final Color BORDER_COLOR = new Color(100,100,100);
    public static final Color BG_COLOR = new Color(58,58,58);
    public static final Color FG_COLOR = new Color(207,207,207);
    public static final Color DISABLED_BG_COLOR = new Color(32,32,32);
    public static final Color DISABLED_FG_COLOR = new Color(70,70,70);

    private Color currentFGColor = FG_COLOR;
    private Color currentColor = BG_COLOR;
    private Color currentBorderColor = BORDER_COLOR;
    public MyTextField(){
        super();
        setOpaque(false);
        format();
    }

    public void format(){
        this.setForeground(currentFGColor);
        this.setDisabledTextColor(DISABLED_FG_COLOR);
        normalBorder();
        Font font = new Font("Courier New", Font.PLAIN,12);
        this.setFont(font);
    }

    protected void paintComponent(Graphics g){
        //g.clearRect(0,0,this.getWidth(),this.getHeight());
        this.setForeground(currentFGColor);

        g.setColor(currentColor);
        g.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g){
        g.setColor(currentBorderColor);
        g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
    }

    public void redBorder(){
        if (this.isEnabled())
            currentBorderColor = Color.RED;

    }

    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(enabled)
            setEnabled();
        else setDisabled();
    }

    public void setEnabled(){
        currentColor = BG_COLOR;
        currentFGColor = FG_COLOR;
    }

    public void setDisabled(){
        currentColor = DISABLED_BG_COLOR;
        currentFGColor = DISABLED_FG_COLOR;
    }

    public void normalBorder(){
        currentBorderColor = BORDER_COLOR;
        //this.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
    }
}
