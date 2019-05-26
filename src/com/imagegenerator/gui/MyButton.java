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
        this.setBackground(BG_COLOR);
        this.setForeground(FG_COLOR);
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

    protected void paintBorder(Graphics g) {
        g.setColor(BORDER_COLOR);
        g.drawRoundRect(0, 0, this.getWidth()-1, this.getHeight()-1, 10, 10);
    }


}
