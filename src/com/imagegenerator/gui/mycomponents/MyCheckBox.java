package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;

public class MyCheckBox extends JCheckBox {
    private int size;
    private Color currentBG, currentBorder, fontColor, centerColor = Color.WHITE;
    private int textLocation;
    public static final int TEXT_LEFT = 0;
    public static final int TEXT_RIGHT = 1;

    public MyCheckBox(String text, int s, int l){
        size = s;
        textLocation = l;
        this.setText(text);
        this.setOpaque(false);
        this.setMinimumSize(new Dimension(size * 6, size + 10));
    }

    protected void paintComponent(Graphics g){
        Graphics2D graph2D = (Graphics2D)g;
        int y = this.getHeight()/2 - size/2;
        if (this.isEnabled()) {
            if (this.isSelected()) {
                currentBG = Utility.TF_SELECTED_BORDER_COLOR;
            } else {
                currentBG = Utility.TF_BG_COLOR;
                currentBorder = Utility.TF_BORDER_COLOR;
            }
        }
        graph2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graph2D.setColor(currentBG);
        //graph2D.fillOval(0, 0, size, size);
        graph2D.setColor(currentBorder);
        if(textLocation == TEXT_RIGHT){
            textOnRight(graph2D, y);
        }
        else textOnLeft(graph2D, y);


    }

    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(enabled){
            currentBG = Utility.TF_BG_COLOR;
            currentBorder = Utility.TF_BORDER_COLOR;
            centerColor = Color.WHITE;
            fontColor = Utility.MAIN_LABEL_COLOR;
        }
        else{
            currentBG = Utility.TF_DISABLED_BG_COLOR;
            currentBorder = Utility.TF_BORDER_COLOR;
            centerColor = Color.GRAY;
            fontColor = Color.GRAY;
        }
    }

    /* Draw the circles on the right, text on the left */
    private void textOnLeft(Graphics2D graph2D, int y){
        int x = this.getWidth() - size - 2;
        graph2D.drawOval(x, y, size, size);
        if(this.isSelected()) {
            int x2 = x + size/4;
            int y2 = y + size/4;
            int radius = size-((size/4)*2) + 1;
            graph2D.setColor(centerColor);
            graph2D.fillOval(x2, y2, radius, radius);
        }

        graph2D.setColor(fontColor);
        FontMetrics m = graph2D.getFontMetrics(Utility.MAIN_FONT);
        graph2D.setFont(Utility.MAIN_FONT);
        graph2D.drawString(this.getText(), x-10-m.stringWidth(this.getText()),
                y + (size - m.getHeight())/2 + m.getAscent() + 1);
    }

    /* Draw the circles on the left, text on the right */
    private void textOnRight(Graphics2D graph2D, int y){
        graph2D.drawOval(0, y, size, size);
        if(this.isSelected()) {
            int x = size/4;
            int y2 = y + size/4;
            int radius = size-((size/4)*2) + 1;
            graph2D.setColor(centerColor);
            graph2D.fillOval(x, y2, radius, radius);
        }

        FontMetrics m = graph2D.getFontMetrics(Utility.MAIN_FONT);

        graph2D.setColor(fontColor);
        graph2D.setFont(Utility.MAIN_FONT);
        graph2D.drawString(this.getText(), size+10, y + (size - m.getHeight())/2 + m.getAscent() + 1);
    }
}
