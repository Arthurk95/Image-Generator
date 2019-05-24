package com.imagegenerator.gui;


import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

public class MyConsoleField extends JTextPane {
    public static final Color TEXT_COLOR = new Color(180,180,180);
    private final Color BG_COLOR = new Color(45,45,45);
    private final Font font = new Font("Courier New", Font.PLAIN, 12);
    private final Color FILENAME_COLOR = new Color(70,160,160);

    public MyConsoleField(){
        super();
        this.setFont(font);
        this.setBackground(BG_COLOR);
    }

    public void appendCreateFile(String dir, String file){
        appendToPane("Created file ", TEXT_COLOR);
        appendToPane(file, FILENAME_COLOR);
        appendToPane(" at" + dir + "\n", TEXT_COLOR);
    }

    public void appendFileName(String file){
        appendToPane(file, FILENAME_COLOR);
    }

    public void appendDirectory(String dir){
        appendToPane(dir, TEXT_COLOR);
    }

    public void appendDirWriteSuccess(String dir){
        appendToPane("Directory ", Color.GREEN);
        appendDirectory(dir);
        appendToPane(" successfully created\n", Color.GREEN);
    }

    public void appendDirWriteFail(String dir){
        appendToPane("Directory ", Color.RED);
        appendDirectory(dir);
        appendToPane(" failed to create. It either already exists or invalid name\n", Color.RED);
    }

    private void appendToPane(String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = this.getDocument().getLength();
        this.setCaretPosition(len);
        this.setCharacterAttributes(aset, false);
        this.replaceSelection(msg);
    }

}
