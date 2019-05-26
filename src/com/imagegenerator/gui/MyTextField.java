package com.imagegenerator.gui;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField {

    private Color currentFGColor = Utility.TF_FG_COLOR;
    private Color currentColor = Utility.TF_BG_COLOR;
    private Color currentBorderColor = Utility.BORDER_COLOR;
    public MyTextField(){
        super();
        setCaretColor(Utility.TF_FG_COLOR);
        setOpaque(false);
        format();

        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                currentBorderColor = Utility.TF_SELECTED_BORDER_COLOR;
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                currentBorderColor = Utility.BORDER_COLOR;
                repaint();
            }
        });
    }

    public void format(){
        this.setForeground(currentFGColor);
        this.setDisabledTextColor(Utility.TF_DISABLED_FG_COLOR);
        normalBorder();

        this.setFont(Utility.TF_FONT);
    }

    protected void paintComponent(Graphics g){
        this.setForeground(currentFGColor);
        Graphics2D graph2D = (Graphics2D)g;
        graph2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        graph2D.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if(this instanceof MyTextFieldLine){}
        else {
            graph2D.setColor(currentColor);
            graph2D.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
            g.setColor(currentBorderColor);
            g.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        }
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g){
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
        currentColor = Utility.TF_BG_COLOR;
        currentFGColor = Utility.TF_FG_COLOR;
    }

    public void setDisabled(){
        currentColor = Utility.TF_DISABLED_BG_COLOR;
        currentFGColor = Utility.TF_DISABLED_FG_COLOR;
    }

    public void normalBorder(){
        currentBorderColor = Utility.BORDER_COLOR;
    }
}
