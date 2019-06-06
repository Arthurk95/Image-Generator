package com.imagegenerator.gui;

import com.imagegenerator.ImageGenerator;
import com.imagegenerator.Utility;
import com.imagegenerator.gui.mycomponents.MyCheckBox;
import com.imagegenerator.gui.mycomponents.MyConsoleField;
import com.imagegenerator.gui.mycomponents.MyTextField;
import com.imagegenerator.gui.mycomponents.PreviewPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainUI extends JFrame{


    private Color mainColor, gradientColor, textColor;
    public JPanel mainPanel;
    private MyTextField imageWidthTF;
    private MyTextField imageHeightTF;
    private MyTextField mainColorTF;
    private MyTextField gradientColorTF;
    private MyTextField topGradientTF;
    private JButton generateImageFileButton;
    private MyTextField textColorTF;
    private MyTextField botGradientTF;
    private MyTextField textContentTF;
    private MyTextField textFontSizeTF;
    private JTextField directoryTF;
    private MyTextField textFontTF;
    private MyCheckBox iterativeCheckBox;
    private MyTextField iterationsTF;
    private JRadioButton textCheckBox;
    private JComboBox extensionDropDown;
    private JPanel textPanel;
    private JTextField fileNameTF;
    private JTextPane consolePane;
    private JPanel dimensionsPanel;
    private PreviewPanel imagePreviewPanel;
    private JTextField linkField;
    private JScrollPane consoleScrollPane;
    private MyCheckBox boldCheckBox;
    private MyCheckBox italicsCheckBox;
    private MyConsoleField consoleOutput = new MyConsoleField();
    private ImageGenerator ig;
    private int width, height, fontSize, iterations,gradientTopEnd, gradientBotStart, textX;
    private double topGradient, bottomGradient, ratio;
    private String directory, textContent, extension, fileName;
    private boolean canGenerate = true;

    private void createUIComponents(){
        textContentTF = new MyTextField("Enter Text Here");
        linkField = new JTextField();
        linkField.setText("https://github.com/Arthurk95 "); // showing off
        linkField.setFont(new Font("Calibri", Font.PLAIN, 10));
        linkField.setForeground(new Color(165,165,165));
        linkField.setEditable(false); // as before
        linkField.setBackground(null); // this is the same as a JLabel
        linkField.setBorder(null); // remove the border

        iterativeCheckBox = new MyCheckBox("iterative", 20);
        boldCheckBox = new MyCheckBox("bold", 12);
        italicsCheckBox = new MyCheckBox("italics", 12);
    }

    public MainUI(){
        textCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                flickTextEnable();
                drawPreview();
            }
        });

        /* Automatically scrolls the JTextPane down when it gets too long */
        final int[] verticalScrollBarMaximumValue = {consoleScrollPane.getVerticalScrollBar().getMaximum()};
        consoleScrollPane.getVerticalScrollBar().addAdjustmentListener(
                e -> {
                    if ((verticalScrollBarMaximumValue[0] - e.getAdjustable().getMaximum()) == 0)
                        return;
                    e.getAdjustable().setValue(e.getAdjustable().getMaximum());
                    verticalScrollBarMaximumValue[0] = consoleScrollPane.getVerticalScrollBar().getMaximum();
                });

        /* Check box checked/unchecked */
        iterativeCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                if(iterativeCheckBox.isSelected()) {
                    iterationsTF.setEnabled(true);
                    drawPreview();
                }
                else {
                    iterationsTF.setEnabled(false);
                    drawPreview();
                }
            }
        });

        /* Added these two actionlisteners so that preview is automatically updated
         * when checkbox is selected/de-selected */
        boldCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPreview();
            }
        });
        italicsCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                drawPreview();
            }
        });

        /* Saves the image to a file */
        generateImageFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                getValues();
                repaintPanels();
                // creates a new directory
                if(directory.equals("")){}
                else createDirectory();
                if(!canGenerate){
                    canGenerate = true;
                }
                else {
                    /* draw and write multiple images */
                    if (textCheckBox.isSelected() && iterativeCheckBox.isSelected()) {
                        String tempText = textContent;
                        String tempFileName = fileName;
                        for (int i = 1; i <= iterations; i++) {
                            if(textContent.length() == 0)
                                textContent = Integer.toString(i);
                            else textContent = textContent + " " + i;
                            fileName = fileName + i;
                            createImageFile();
                            textContent = tempText;
                            fileName = tempFileName;
                        }
                    } else {
                        generateImage();
                        createImageFile();
                    }
                }

            }
        });
        flickTextEnable();
        styleConsole();
        iterationsTF.setDisabled();
        setListeners();
    }

    private void repaintPanels(){
        dimensionsPanel.repaint();
        textPanel.repaint();
    }

    /* Calls the necessary methods in ImageGenerator to create and write an image */
    private void createImageFile(){
        File f;
        generateImage();
        f = ig.writeFile(directory+fileName, extension);
        consoleOutput.appendCreateFile(f.getAbsolutePath().replace(
                fileName+extension, ""),fileName+extension);
        consolePane.setStyledDocument(consoleOutput.getStyledDoc());
    }

    /* Initializes the generator by passing it all of the values.
     * Generates the image */
    private void generateImage(){
        ig = new ImageGenerator(width, height, gradientTopEnd, gradientBotStart);
        ig.setColors(mainColor, gradientColor);
        if(textCheckBox.isSelected()){
            int bold = boldCheckBox.isSelected() ? Font.BOLD : 0;
            int italics = italicsCheckBox.isSelected() ? Font.ITALIC : 0;
            int fontStyle = bold + italics;
            Font f = new Font(textFontTF.getText(), fontStyle, fontSize);
            ig.setText(textContent, textColor, f, textX);
        }
        ig.generateImage();
    }



    /* Converts the content of each MyTextField to its appropriate type */
    private void getValues(){
        try {
            width = Integer.parseInt(imageWidthTF.getText());
            if(width > Utility.MAX_DIMENSION_SIZE)
                width = Utility.MAX_DIMENSION_SIZE;
            if(width < Utility.MIN_DIMENSION_SIZE)
                width = Utility.MIN_DIMENSION_SIZE;
            imageWidthTF.setText(String.valueOf(width));
            imageWidthTF.normalBorder();
        } catch(Exception e) {
            invalidField(imageWidthTF);
        }

        try {
            height = Integer.parseInt(imageHeightTF.getText());
            if (height > Utility.MAX_DIMENSION_SIZE)
                height = Utility.MAX_DIMENSION_SIZE;
            if(height < Utility.MIN_DIMENSION_SIZE)
                height = Utility.MIN_DIMENSION_SIZE;
            imageHeightTF.setText(String.valueOf(height));
            imageHeightTF.normalBorder();
        } catch(Exception e){
            invalidField(imageHeightTF);
        }

        /* Get end Y of the top gradient */
        try {
            topGradient = Double.parseDouble(topGradientTF.getText());
            gradientTopEnd = (int) (height * topGradient);
            topGradientTF.normalBorder();
        } catch(Exception e){
            invalidField(topGradientTF);
        }

        /* Get starting Y of bottom gradient */
        try {
            bottomGradient = Double.parseDouble(botGradientTF.getText());
            gradientBotStart = (int) (height * (1 - bottomGradient));
            botGradientTF.normalBorder();
        } catch(Exception e){
            invalidField(botGradientTF);
        }

        try{
            if((Double.parseDouble(topGradientTF.getText()) + Double.parseDouble(botGradientTF.getText())) > 0.9){
                consoleOutput.appendErrorMessage("The gradients must be less than 0.9 when added together.");
                canGenerate = false;
                topGradientTF.redBorder();
                botGradientTF.redBorder();
                consolePane.setStyledDocument(consoleOutput.getStyledDoc());
            }
        } catch(Exception e){}
        mainColor = getColor(mainColorTF);
        gradientColor = getColor(gradientColorTF);
        getDirectoryValues();
        if(textCheckBox.isSelected())
            getTextValues();
    }

    private void invalidField(MyTextField field){
        consoleOutput.appendErrorMessage("Field " + field.getName() + " invalid");
        canGenerate = false;
        field.redBorder();
        consolePane.setStyledDocument(consoleOutput.getStyledDoc());
        consolePane.setCaretPosition(consolePane.getDocument().getLength());
    }

    /* Converts any MyTextField related to the Text generation to its appropriate type */
    private void getTextValues(){
        String text = textFontSizeTF.getText();
        try{
            fontSize = Integer.parseInt(text);
            textFontSizeTF.normalBorder();
        } catch(Exception e){
            invalidField(textFontSizeTF);
            fontSize = 18;
        }

        text = iterationsTF.getText();
        try{
            iterations = Integer.parseInt(text);
            if(iterations > 100)
                iterations = Utility.MAX_ITERATIONS;
            iterationsTF.normalBorder();
        } catch(Exception e){
            invalidField(iterationsTF);
        }
        textColor = getColor(textColorTF);
        textContent = textContentTF.getText();
    }

    /* Converts the directory and fileName JTextFields to Strings.
     * Makes sure the directory ends with a backslash */
    private void getDirectoryValues() {
        fileName = fileNameTF.getText();
        extension = (String) extensionDropDown.getSelectedItem();
        directory = directoryTF.getText();
        if (directory.equals("")) {}
        else {
            char dirEnd = directory.charAt(directory.length() - 1);
            if (dirEnd != '\\') {
                directory = directory + "\\";
            }
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
            invalidField(tf);
            return null;
        }
    }

    public void drawPreview(){
        getValues();
        repaintPanels();
        if (iterativeCheckBox.isSelected()){
            if(textContent.length() == 0)
                textContent = "1";
            else textContent = textContent + " 1";
        }
        if(!canGenerate){
            imagePreviewPanel.drawPreview(ig, canGenerate);
            canGenerate = true;
        }
        else {
            generateImage();
            imagePreviewPanel.drawPreview(ig, canGenerate);
        }
    }

    /* Adds focus listeners to all components to draw
     * the PreviewPanel whenever something loses focus. */
    private void setListeners(){
        addMouseListener(mainPanel);
        Component[] comps = mainPanel.getComponents();
        for (Component comp : comps){
            if (comp instanceof JPanel){
                addMouseListener((JPanel)comp);
                Component[] subComp = ((JPanel) comp).getComponents();
                for(Component c : subComp) {
                    addFocusListener(c);
                }
            }
            else {
                addFocusListener(comp);
            }
        }
    }

    /* Takes focus away from any other currently selected component */
    private void addMouseListener(JPanel c){
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseReleased(e);
                c.grabFocus();
            }
        });
    }

    private void addFocusListener(Component c){
        c.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) { }
            @Override
            public void focusLost(FocusEvent e) {
                drawPreview();
            }
        });
    }

    /* Enables/disables the entire textPanel based on the textCheckBox state.
     * Changes the color of the background as well, and makes sure the
     * iterativeTextField maintains its state.*/
    private void flickTextEnable(){
        enableComponents(textPanel,textCheckBox.isSelected());
        if(textCheckBox.isSelected()){
            textPanel.setBackground(new Color(61,68,72));
        }
        else {
            textPanel.setBackground(new Color(61,61,61));
        }
        if(!iterativeCheckBox.isSelected()){
            iterationsTF.setEnabled(false);
        }
    }

    /**
     * Enables/disables all components within a container.
     *
     * @param container     container whose components to enable/disable
     * @param enable        state to switch to
     */
    private void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
        textCheckBox.setEnabled(true);
    }

    /* Attempts to create a directory.
     * Outputs success or failure */
    private void createDirectory(){
        File dir = new File(directory);
        boolean directoryCreated = dir.mkdir();
        if(directoryCreated){
            consoleOutput.appendDirWriteSuccess(directory);
        }
        else consoleOutput.appendDirWriteFail(directory);
    }

    private void styleConsole(){
        consolePane.setBackground(new Color(45,45,45));
        consolePane.setFont(new Font("Courier New", Font.PLAIN, 12));
    }
}
