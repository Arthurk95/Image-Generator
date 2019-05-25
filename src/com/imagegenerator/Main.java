package com.imagegenerator;

import com.imagegenerator.gui.MainUI;

import javax.swing.*;

public class Main {



    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Generator");
        MainUI main = new MainUI();
        frame.setContentPane(main.mainPanel);
        frame.setSize(main.mainPanel.getSize());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
