package com.imagegenerator.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class MyButton extends JButton {
    private final Color BG_COLOR = new Color(80,80,80);
    private final Color FG_COLOR = new Color(200,200,200);
    private final Color BORDER_COLOR = new Color(100,100,100);
    private final Color SEL_COLOR = new Color(50,80,120);
    private final Font font = new Font("Calibri", Font.PLAIN, 11);

    public MyButton(){
        this.setForeground(FG_COLOR);
        this.setOpaque(false);
        this.setFont(font);
        this.setMargin(new Insets(4,5,4,5));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setBackground(SEL_COLOR);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(BG_COLOR);
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
        graph2D.setColor(BG_COLOR);
        graph2D.fillRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 10, 10);
        graph2D.setColor(BORDER_COLOR);
        graph2D.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 10, 10);
        centerString(graph2D);
    }

    protected void paintBorder(Graphics g) {

    }

    private void centerString(Graphics2D g){
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = (this.getWidth() - metrics.stringWidth(this.getText()))/2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((this.getHeight() - metrics.getHeight())/2) + metrics.getAscent();
        g.setFont(font);
        g.setColor(FG_COLOR);
        g.drawString(this.getText(), x, y);
    }

}
