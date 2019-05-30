package com.imagegenerator.gui.mycomponents;

import com.imagegenerator.ImageGenerator;
import com.imagegenerator.Utility;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.awt.*;

public class PreviewPanel extends JPanel{
    private Font previewFont = new Font("Calibri", Font.PLAIN, 22);
    private int previewWidth, previewHeight, previewStartX, previewStartY;
    private ImageGenerator ig;
    private boolean canGenerate;

    public PreviewPanel(){
        super();
    }

    public void drawPreviewBG(Graphics g){
        clearPreview(g);
        g.setColor(Utility.BORDER_COLOR);
        // top left to bottom right
        g.drawLine(previewStartX, previewStartY, previewWidth + previewStartX, previewHeight + previewStartY);
        // bottom left to top right
        g.drawLine(previewStartX, previewHeight + previewStartY, previewWidth + previewStartX, previewStartY);
        g.setColor(Utility.MAIN_BG_COLOR);
        drawString(g);
        paintBorder(g);
    }

    public void paintComponent(Graphics g){
        super.repaint();
        if(canGenerate)
            drawPreview(g);
        else {
            drawPreviewBG(g);
        }
    }


    protected void paintBorder(Graphics g){
        if(!canGenerate) {
            g.setColor(Utility.BORDER_COLOR);
            g.drawRect(previewStartX, previewStartY, previewWidth - 1, previewHeight);
        }
    }

    private void drawPreview(Graphics g){
        g.drawImage(ig.getImage(), previewStartX, previewStartY, previewWidth, previewHeight, null);
    }
    /* Draws the image in the previewPanel container.
     * The image is scaled to fit within the container. */
    public void drawPreview(ImageGenerator i, boolean c) {
        ig = i;
        canGenerate = c;
        previewWidth = ig.getWidth();
        previewHeight = ig.getHeight();

        fitToPreview();
        paintComponent(getGraphics());
    }

    /* Recursive function to scale the dimensions of the image to fit within the
     * previewPanel container. That is, if the width of the generated image is greater
     * than that of the previewPanel's width, find how much greater (ratio),
     * set the previewWidth to previewPanel's width, and divide the previewHeight
     * by the ratio.
     * Repeat for the previewHeight.
     *
     * previewWidth and previewHeight start as the width/height for the generated image.
     *
     * EX:      previewPanel.getWidth() = 100; width = 110;
     *          ratio = 110/110 = 1.1
     *          previewPanel.getHeight() = 100; height = 50
     *
     * result:  previewWidth = previewPanel.getWidth() = 100
     *          previewHeight = previewHeight/ratio = 50/1.1 = 45*/
    private void fitToPreview(){
        int maxWidth = this.getWidth()-(this.getWidth()/20);
        int maxHeight = this.getHeight()-(this.getHeight()/20);
        double ratio;
        if (previewWidth > maxWidth){
            ratio = (double)previewWidth/maxWidth;
            previewWidth = maxWidth;
            previewHeight = (int)(previewHeight/ratio);
            fitToPreview();
        }
        else if(previewHeight > maxHeight){
            ratio = (double)previewHeight/maxHeight;
            previewHeight = maxHeight;
            previewWidth = (int)(previewWidth/ratio);
            fitToPreview();
        }
        int centerWidthPreview = this.getWidth() / 2;
        int centerHeightPreview = this.getHeight() / 2;
        previewStartX = centerWidthPreview - (previewWidth / 2);
        previewStartY = centerHeightPreview - (previewHeight / 2);
    }

    /* Resets the previewPanel container, removing any previously generated previews */
    private void clearPreview(Graphics g){

    }

    private void drawString(Graphics g) {
        FontMetrics metrics = g.getFontMetrics(previewFont);
        int stringWidth = metrics.stringWidth("Preview");
        int stringHeight = metrics.getHeight();
        // Determine the X coordinate for the text
        int x = ((this.getWidth() - stringWidth)/2);
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((this.getHeight() - stringHeight)/2);
        g.fillRect(x-(stringWidth/2), y-stringHeight, stringWidth*2, stringHeight*3);
        y = y + metrics.getAscent();
        g.setFont(previewFont);
        g.setColor(Utility.BORDER_COLOR);
        g.drawString("Preview", x, y);
    }
}
