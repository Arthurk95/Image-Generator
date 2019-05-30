package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class MyTextField extends JTextField{

    private Color currentFGColor = Utility.TF_FONT_COLOR;
    private Color currentColor = Utility.TF_BG_COLOR;
    private Color currentBorderColor = Utility.BORDER_COLOR;
    private boolean showingHint = false;
    private String hint;

    public MyTextField(){
        setCaretColor(Utility.TF_FONT_COLOR);
        setOpaque(false);
        format();
        addFocusListeners();
    }

    public MyTextField(String hint){
        super(hint);
        this.hint = hint;
        showingHint = true;
        setCaretColor(Utility.TF_FONT_COLOR);
        setOpaque(false);
        format();
        addFocusListeners();
    }

    private void format(){
        showingHint();
        this.setForeground(currentFGColor);
        this.setDisabledTextColor(Utility.TF_DISABLED_FONT_COLOR);
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

    protected void paintBorder(Graphics g){ }

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
        showingHint();
    }

    public void setDisabled(){
        currentColor = Utility.TF_DISABLED_BG_COLOR;
        currentFGColor = Utility.TF_DISABLED_FONT_COLOR;
    }

    public void normalBorder(){
        currentBorderColor = Utility.BORDER_COLOR;
    }

    private void addFocusListeners(){
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                currentBorderColor = Utility.TF_SELECTED_BORDER_COLOR;
                if(showingHint) {
                    setText("");
                    showingHint = false;
                    showingHint();
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                currentBorderColor = Utility.BORDER_COLOR;
                if ((getText().equals("") || getText().equals(hint)) && (hint != null)) {
                    showingHint = true;
                    showingHint();
                    setText(hint);
                }
                else showingHint = false;
                repaint();
            }
        });
    }

    private void showingHint(){
        if(showingHint){
            currentFGColor = Utility.TF_HINT_COLOR;
            this.setFont(Utility.TF_HINT_FONT);
        }
        else{
            currentFGColor = Utility.TF_FONT_COLOR;
            this.setFont(Utility.TF_FONT);
        }
    }
}
