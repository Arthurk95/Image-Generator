package com.imagegenerator;

import com.imagegenerator.gui.MainUI;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StartProgram {
    private MainUI main;

    public StartProgram(){
        JFrame frame = new JFrame("Image Generator");
        MainUI main = new MainUI();
        styleComboBox();
        frame.setContentPane(main.mainPanel);
        frame.setSize(main.mainPanel.getSize());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        main.drawPreview();
        URL iconURL = getClass().getResource("icon.png");
        // iconURL is null when not found
        ImageIcon icon = new ImageIcon(iconURL);
        frame.setIconImage(icon.getImage());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new StartProgram();
    }
    private void styleComboBox(){
        UIManager.put("ComboBox.background", new Color(70,70,70));
        UIManager.put("ComboBox.buttonShadow", new Color(80,80,80));
        UIManager.put("ComboBox.buttonBackground", new Color(80,80,80));
        UIManager.put("ComboBox.buttonDarkShadow", new Color(70,70,70));
        UIManager.put("ComboBox.buttonHighlight", new Color(80,80,80));
        UIManager.put("ComboBox.foreground", new Color(200,200,200));
    }
}
