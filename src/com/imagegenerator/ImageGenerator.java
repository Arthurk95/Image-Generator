package com.imagegenerator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This class generates and stores a BufferedImage file.
 * Contains methods to write the image file to a given
 * directory or path.
 *
 * @author  Arthur Kharit
 * */
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

    public void setText(String t, Color c, Font f){
        text = t; textColor = c;
        textFont = f;
    }

    public int getWidth(){ return imageWidth; }
    public int getHeight(){return imageHeight;}

    /**
     * Generates and writes an image file to disk at directory dir
     *
     * @param dir   the directory or name (if no directory specified) of the file
     * @param ext   the extension of the file
     * @return      the generated file
     */
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

    /**
     * Generates an image by splitting it into three sections:
     *      - The top (of the image) gradient
     *      - The main color (middle)
     *      - The bottom (of the image) gradient
     *
     */
    public void generateImage(){
        bf = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph2D = bf.createGraphics();
        graph2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graph2D.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        /* Top portion of image gradient
        * (0,0) to (0,gradientTop) */
        GradientPaint topGradient = new GradientPaint(
                0, 0, gradientColor, 0, (int)gradientTop, mainColor);
        graph2D.setPaint(topGradient);
        graph2D.fill(new Rectangle2D.Double(0, 0, imageWidth, (int)gradientTop));

        /* Middle of image
        * (0,gradientTop) to (imageWidth,gradientBot)*/
        graph2D.setPaint(mainColor);
        graph2D.fill(new Rectangle2D.Double(0, (int)gradientTop, imageWidth, (int)gradientBot));

        /* Bottom portion of image gradient
        * (0,gradientBot) to (0,imageHeight) */
        GradientPaint bottomGradient = new GradientPaint(
                0, (int)gradientBot, mainColor, 0, imageHeight, gradientColor);
        graph2D.setPaint(bottomGradient);
        graph2D.fill(new Rectangle2D.Double(0, (int)gradientBot, imageWidth, imageHeight));

        // generate text if text valid
        if(text != null && (text.length() > 0)) {
            centerString(graph2D);
        }
    }

    /**
     * Determines the width and height of the text string based on its
     * font and then draws it centered onto the Graphics2D object.
     *
     * Solution found at <a href="https://stackoverflow.com/a/27740330">StackOverFlow</a>
     *
     * @param g a 2D graphics object that contains an image
     */
    private void centerString(Graphics2D g) {
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
