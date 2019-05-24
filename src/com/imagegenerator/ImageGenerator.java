package com.imagegenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageGenerator {
    private int imageWidth; // columns in array | x-axis
    private int imageHeight; // rows in array | y-axis
    private Color mainColor, gradientColor, textColor;
    private String text;
    private Font textFont;

    /* Gradient amount for top and bottom portions of image
    * value between 0.0 and 0.5 */
    private double gradientTop, gradientBot;
    private BufferedImage bf;

    public ImageGenerator(int w, int h, double gT, double gB){
        imageWidth = w; imageHeight = h; gradientTop = gT; gradientBot = gB;
    }

    public void setColors(Color m, Color g){
        mainColor = m; gradientColor = g;
    }

    public void setText(String t, Color c, int size, String font){
        text = t; textColor = c;
        textFont = new Font(font, Font.PLAIN, size);
    }

    public File writeFile(String dir, String ext){

        String ext2 = ext.substring(1,4);
        File file = new File(dir+ext);
        try {
            ImageIO.write(bf, ext2, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public BufferedImage getImage(){
        return bf;
    }

    public void generateImage(){
        bf = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph2D = bf.createGraphics();

        /* Top portion of image gradient */
        GradientPaint topGradient = new GradientPaint(
                0, 0, gradientColor, 0, (int)gradientTop, mainColor);
        graph2D.setPaint(topGradient);
        graph2D.fill(new Rectangle2D.Double(0, 0, imageWidth, (int)gradientTop));

        /* Middle of image */
        graph2D.setPaint(mainColor);
        graph2D.fill(new Rectangle2D.Double(0, (int)gradientTop, imageWidth, (int)gradientBot));

        /* Bottom portion of image gradient */
        GradientPaint bottomGradient = new GradientPaint(
                0, (int)gradientBot, mainColor, 0, imageHeight, gradientColor);
        graph2D.setPaint(bottomGradient);
        graph2D.fill(new Rectangle2D.Double(0, (int)gradientBot, imageWidth, imageHeight));
        if(text != null && (text.length() > 0)) {
            centerString(graph2D);
        }
    }

    public void centerString(Graphics2D g) {
        FontMetrics metrics = g.getFontMetrics(textFont);
        // Determine the X coordinate for the text
        int x = (imageWidth - metrics.stringWidth(text))/2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = ((imageHeight - metrics.getHeight())/2) + metrics.getAscent();
        g.setFont(textFont);
        g.setColor(textColor);
        g.drawString(text, x, y);
    }
}
