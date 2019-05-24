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
    private JCheckBox textCheckBox;
    private JComboBox extensionDropDown;
    private JPanel textPanel;
    private JPanel iteratePanel;
    private ImageGenerator ig;
    private int width, height, textX, textY, fontSize, previewWidth, previewHeight;
    private double topGradient, bottomGradient, ratio;

    private void createUIComponents(){}

    public MainUI(){
        flickTextEnable();
        iterationsTF.setDisabled();

        textCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                flickTextEnable();
            }
        });

        /* Check box checked/unchecked */
        iterativeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                if(iterativeCheckBox.isSelected())
                    iterationsTF.setEnabled();
                else iterationsTF.setDisabled();
            }
        });

        /* Saves the image to a file */
        generateImageFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                getValues();
                String directory = directoryTF.getText();
                if(textCheckBox.isSelected())
                    directory = directory+textContentTF.getText();
                else
                initGenerator();
                BufferedImage image = ig.generateImage();
                File file = new File(directory);
                try {
                    ImageIO.write(image, extensionDropDown.getName(), file);
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

    /* Initializes the generator by passing it all of the values */
    private void initGenerator(){
        ig = new ImageGenerator(width, height, topGradient, bottomGradient);
        ig.setColors(getColor(mainColorTF), getColor(gradientColorTF));
        if(textCheckBox.isSelected()){
            ig.setText(textContentTF.getText(), getColor(textColorTF),
                    Integer.parseInt(textFontSizeTF.getText()), textFontTF.getText());
        }
    }

    /* Converts the content of each MyTextField to its appropriate type */
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

    /* Converts any MyTextField related to the Text generation to its appropriate type */
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

    /* Converts the MyTextField's text to three RGB values, and returns it as a Color */
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

    /* Resets the previewPanel container, removing any previously generated previews */
    private void clearPreview(Graphics g){
        g.clearRect(0,0,imagePreviewPanel.getWidth(), imagePreviewPanel.getHeight());
        g.setColor(mainPanel.getBackground());
        g.fillRect(0,0,imagePreviewPanel.getWidth(), imagePreviewPanel.getHeight());
    }

    private void flickTextEnable(){
        enableComponents(textPanel,textCheckBox.isSelected());
        if(textCheckBox.isSelected()){
            textPanel.setBackground(new Color(61,68,72));
            iteratePanel.setBackground(new Color(61,68,72));
        }
        else {
            textPanel.setBackground(new Color(61,61,61));
            iteratePanel.setBackground(new Color(61,61,61));
        }
    }

    public void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }

    /* Unused for now.
     * generates a slight gradient for the MainPanel background */
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
