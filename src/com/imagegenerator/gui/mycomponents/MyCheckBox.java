package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;

public class MyCheckBox extends JCheckBox {
    private int size = 18;
    private Color currentBG, currentBorder, fontColor, centerColor = Color.WHITE;

    public MyCheckBox(){
        this.setOpaque(false);
        this.setMinimumSize(new Dimension(size * 6, size + 10));
    }

    protected void paintComponent(Graphics g){
        Graphics2D graph2D = (Graphics2D)g;
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
        graph2D.drawOval(0, 0, size, size);
        if(this.isSelected()) {
            graph2D.setColor(centerColor);
            graph2D.fillOval(6, 6, size - 11, size - 11);
        }

        graph2D.setColor(fontColor);
        graph2D.setFont(Utility.MAIN_FONT);
        graph2D.drawString(this.getText(), size+10, size-(size/3));
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
