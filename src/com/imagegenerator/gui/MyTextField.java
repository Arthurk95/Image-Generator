package com.imagegenerator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField {
    private final Color BORDER_COLOR = new Color(100,100,100);
    private final Color BG_COLOR = new Color(58,58,58);
    private final Color FG_COLOR = new Color(207,207,207);
    private final Color DISABLED_BG_COLOR = new Color(32,32,32);
    private final Color DISABLED_FG_COLOR = new Color(70,70,70);
    private final Color SELECTED_BORDER_COLOR = new Color(50,80,120);
    private Color currentFGColor = FG_COLOR;
    private Color currentColor = BG_COLOR;
    private Color currentBorderColor = BORDER_COLOR;
    public MyTextField(){
        super();
        setCaretColor(FG_COLOR);
        setOpaque(false);
        format();

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                currentBorderColor = SELECTED_BORDER_COLOR;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                currentBorderColor = BORDER_COLOR;
                repaint();
            }
        });
    }

    public void format(){
        this.setForeground(currentFGColor);
        this.setDisabledTextColor(DISABLED_FG_COLOR);
        normalBorder();
        Font font = new Font("Courier New", Font.PLAIN,11);
        this.setFont(font);
    }

    protected void paintComponent(Graphics g){
        //g.clearRect(0,0,this.getWidth(),this.getHeight());
        this.setForeground(currentFGColor);
        if(this instanceof MyTextFieldLine){}
        else {
            g.setColor(currentColor);
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
        }
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

    public Color getCurrentBorderColor(){return currentBorderColor;}

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
