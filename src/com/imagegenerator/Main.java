package com.imagegenerator;

import com.imagegenerator.gui.MainUI;

import javax.swing.*;

public class Main {



    public static void main(String[] args) {
        MainUI main = new MainUI();
        JFrame frame = new JFrame("Image Generator");
        frame.setContentPane(main.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
