package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;

public class MyCheckBox extends JCheckBox {
    private int size;
    private Color currentBG, currentBorder, fontColor, centerColor = Color.WHITE;

    public MyCheckBox(String text, int s){
        size = s;
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

    public void setEnabled(boolean enabled){
        super.setEnabled(enabled);
        if(enabled){
            currentBG = Utility.TF_BG_COLOR;
            currentBorder = Utility.TF_BORDER_COLOR;
            centerColor = Color.WHITE;
            fontColor = Utility.TF_FONT_COLOR;
        }
        else{
            currentBG = Utility.TF_DISABLED_BG_COLOR;
            currentBorder = Utility.TF_BORDER_COLOR;
            centerColor = Color.GRAY;
            fontColor = Color.GRAY;
        }
    }
}
