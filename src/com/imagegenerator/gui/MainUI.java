package com.imagegenerator.gui;

import com.imagegenerator.ImageGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainUI {

    private final int MAX_DIMENSION_SIZE = 2000;
    public JPanel mainPanel;
    private MyTextField imageWidthTF;
    private MyTextField imageHeightTF;
    private MyTextField mainColorTF;
    private MyTextField gradientColorTF;
    private MyTextField topGradientTF;
    private JPanel imagePreviewPanel;
    private JButton generatePreviewButton;
    private JButton generateImageFileButton;
    private MyTextField textColorTF;
    private MyTextField botGradientTF;
    private MyTextField textxyTF;
    private MyTextField textContentTF;
    private MyTextField textFontSizeTF;
    private JPanel colorsPanel;
    private JTextField directoryTF;
    private JPanel gradientPanel;
    private MyTextField textFontTF;
    private JCheckBox iterativeCheckBox;
    private MyTextField iterationsTF;
    private ImageGenerator ig;
    private int width, height, textX, textY, fontSize, previewWidth, previewHeight;
    private double topGradient, bottomGradient, ratio;

    private void createUIComponents(){}

    public MainUI(){
        iterationsTF.setEnabled(false);
        iterativeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                if(iterativeCheckBox.isSelected()){
                    iterationsTF.setEnabled(true);
                    iterationsTF.setBackground(MyTextField.BG_COLOR);
                    iterationsTF.setForeground(MyTextField.FG_COLOR);
                }
                else {
                    iterationsTF.setBackground(MyTextField.DISABLED_BG_COLOR);
                    iterationsTF.setForeground(MyTextField.DISABLED_FG_COLOR);
                    iterationsTF.setEnabled(false);
                }
            }
        });

        generateImageFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                getValues();

                initGenerator();
                BufferedImage image = ig.generateImage();
                File file = new File("testimage.png");
                try {
                    ImageIO.write(image, "png", file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        generatePreviewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getValues();
                initGenerator();
                int centerWidthPreview = imagePreviewPanel.getWidth()/2;
                int centerHeightPreview = imagePreviewPanel.getHeight()/2;
                fitToPreview();
                BufferedImage image = ig.generateImage();
                Graphics g = imagePreviewPanel.getGraphics();
                clearPreview(g);
                g.drawImage(image,
                        centerWidthPreview-(previewWidth/2), centerHeightPreview-(previewHeight/2),
                        previewWidth, previewHeight, null);
            }
        });
    }

    private void initGenerator(){
        ig = new ImageGenerator(width, height, topGradient, bottomGradient);
        ig.setColors(getColor(mainColorTF), getColor(gradientColorTF));
        if(textContentTF.getText().length() > 0 && !(textContentTF.getText().equals("Enter Text Here"))){
            ig.setText(textContentTF.getText(), getColor(textColorTF),
                    Integer.parseInt(textFontSizeTF.getText()), textFontTF.getText());
        }
    }

    private void getValues(){
        try {
            width = Integer.parseInt(imageWidthTF.getText());
            if(width > MAX_DIMENSION_SIZE)
                width = MAX_DIMENSION_SIZE;
            imageWidthTF.setText(String.valueOf(width));
            previewWidth = width;
            imageWidthTF.normalBorder();
        } catch(Exception e){
            imageWidthTF.redBorder();}

        try {
            height = Integer.parseInt(imageHeightTF.getText());
            if (height > MAX_DIMENSION_SIZE)
                height = MAX_DIMENSION_SIZE;
            imageHeightTF.setText(String.valueOf(height));
            imageHeightTF.normalBorder();
            previewHeight = height;
        } catch(Exception e){
            imageHeightTF.redBorder(); }

        try {
            topGradient = Double.parseDouble(topGradientTF.getText());
            topGradient = (double) height * topGradient;
            topGradientTF.normalBorder();
        } catch(Exception e){
            topGradientTF.redBorder();}

        try {
            bottomGradient = Double.parseDouble(botGradientTF.getText());
            bottomGradient = (double) height * (1 - bottomGradient);
            botGradientTF.normalBorder();
        } catch(Exception e){
            botGradientTF.redBorder();}

        getTextValues();
    }

    private void getTextValues(){
        String text = textxyTF.getText();
        String[] textSplit = text.split(",");
        try {
            textX = Integer.parseInt(textSplit[0]);
            textY = Integer.parseInt(textSplit[1]);
            textxyTF.normalBorder();
        } catch(Exception e){ textxyTF.redBorder();}

        text = textFontSizeTF.getText();
        try{
            fontSize = Integer.parseInt(text);
            textFontSizeTF.normalBorder();
        } catch(Exception e){
            textFontSizeTF.redBorder();
            fontSize = 18;
        }
    }

    private Color getColor(MyTextField tf){
        String colorString = tf.getText();
        String[] colors = colorString.split(",");
        try {
            tf.normalBorder();
            int r = Integer.parseInt(colors[0]);
            int g = Integer.parseInt(colors[1]);
            int b = Integer.parseInt(colors[2]);
            return new Color(r, g, b);
        } catch(Exception e){
            tf.redBorder();
            return null;
        }
    }

    private void fitToPreview(){
        if (previewWidth > imagePreviewPanel.getWidth()){
            ratio = (double)previewWidth/imagePreviewPanel.getWidth();
            previewWidth = imagePreviewPanel.getWidth();
            previewHeight = (int)(previewHeight/ratio);
            fitToPreview();
        }
        else if(previewHeight > imagePreviewPanel.getHeight()){
            ratio = (double)previewHeight/imagePreviewPanel.getHeight();
            previewHeight = imagePreviewPanel.getHeight();
            previewWidth = (int)(previewWidth/ratio);
            fitToPreview();
        }
    }

    private void clearPreview(Graphics g){
        g.clearRect(0,0,imagePreviewPanel.getWidth(), imagePreviewPanel.getHeight());
        g.setColor(mainPanel.getBackground());
        g.fillRect(0,0,imagePreviewPanel.getWidth(), imagePreviewPanel.getHeight());
    }

    private void drawBG(Graphics g){
        Color mainColor = new Color(69,77,81);
        Color gradientColor = new Color(75,83,87);

        Graphics2D graph2D = (Graphics2D) g;

        /* Top portion of image gradient */
        GradientPaint topGradient = new GradientPaint(
                0, 0, gradientColor, mainPanel.getWidth()/6, mainPanel.getHeight()/3, mainColor);
        graph2D.setPaint(topGradient);
        graph2D.fill(new Rectangle2D.Double(0, 0, mainPanel.getWidth(), mainPanel.getHeight()-(mainPanel.getHeight()/3)));

        /* Middle of image */
        graph2D.setPaint(mainColor);
        graph2D.fill(new Rectangle2D.Double(0, mainPanel.getHeight()/3,
                mainPanel.getWidth()-(mainPanel.getWidth()/6), mainPanel.getHeight()-(mainPanel.getHeight()/3)));

        /* Bottom of image */
        GradientPaint bottomGradient = new GradientPaint(
                0, mainPanel.getHeight()-(mainPanel.getHeight()/3), mainColor,
                mainPanel.getWidth()-(mainPanel.getWidth()/6), mainPanel.getHeight(), gradientColor);
        graph2D.setPaint(bottomGradient);
        graph2D.fill(new Rectangle2D.Double(0, mainPanel.getHeight()-(mainPanel.getHeight()/3),
                mainPanel.getWidth(), mainPanel.getWidth()));
    }
}
