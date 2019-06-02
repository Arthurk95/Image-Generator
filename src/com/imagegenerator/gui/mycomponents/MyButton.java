package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class MyButton extends JButton {
    private Color currentBG = Utility.BUTTON_BG_COLOR;
    private Color currentBorder = Utility.BUTTON_BORDER_COLOR;

    public MyButton(){
        this.setOpaque(false);
        this.setMargin(new Insets(4,5,4,5));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                currentBG = currentBorder = Utility.BUTTON_BG_HOVER_COLOR;
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                currentBG = Utility.BUTTON_BG_COLOR;
                currentBorder = Utility.BUTTON_BORDER_COLOR;
            }
        });
    }

    protected void paintComponent(Graphics g){
        Graphics2D graph2D = (Graphics2D)g;
        graph2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graph2D.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graph2D.setColor(currentBG);
        graph2D.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 10, 10);
        graph2D.setColor(currentBorder);
        graph2D.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 10, 10);
        centerString(graph2D);
    }

    protected void paintBorder(Graphics g) {

    }

    private void centerString(Graphics2D g){
        FontMetrics metrics = g.getFontMetrics(Utility.BUTTON_FONT);
        // Determine the X coordinate for the text
        int x = (this.getWidth() - metrics.stringWidth(this.getText()))/2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((this.getHeight() - metrics.getHeight())/2) + metrics.getAscent();
        g.setFont(Utility.BUTTON_FONT);
        g.setColor(Utility.BUTTON_FONT_COLOR);
        g.drawString(this.getText(), x, y);
    }

}
