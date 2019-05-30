package com.imagegenerator.gui.mycomponents;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

/**
 * Generates a StyledDocument using a JTextPane.
 * Provides methods to append passed input to the styled
 * document, allowing for organized output to be displayed
 * on the MainUI.
 *
 * @author  Arthur Kharit
 */
public class MyConsoleField{
    private static final Color TEXT_COLOR = new Color(180,180,180);
    private final Color FILENAME_COLOR = new Color(70,160,160);
    private final Color DIRECTORY_COLOR = new Color(200,200,120);
    private final Color ERROR_COLOR = new Color(180, 60,60);
    private JTextPane pane;

    public MyConsoleField(){
        pane = new JTextPane();
    }

    public StyledDocument getStyledDoc(){
        return pane.getStyledDocument();
    }

    public void appendErrorMessage(String error){
        appendToPane("ERROR: "+error+"\n", ERROR_COLOR);
    }

    /**
     * Appends the following message format:
     *      Created file *file* at *dir*
     *
     * @param dir   the directory that has been written to
     * @param file  the name of the saved file
     */
    public void appendCreateFile(String dir, String file){
        appendToPane("Created file ", TEXT_COLOR);
        appendToPane(file, FILENAME_COLOR);
        appendToPane(" at ", TEXT_COLOR);
        appendDirectory(dir + "\n");
    }

    /**
     * Appends the directory to the output
     *
     * @param dir   directory that has been written to
     */
    private void appendDirectory(String dir){
        appendToPane(dir, DIRECTORY_COLOR);
    }

    /**
     * Outputs the following in Color.GREEN:
     *      Directory *dir* successfully created
     *
     * @param dir   directory that has been written to
     */
    public void appendDirWriteSuccess(String dir){
        appendToPane("Directory ", Color.GREEN);
        appendDirectory(dir);
        appendToPane(" successfully created\n", Color.GREEN);
    }

    /**
     * Outputs the following in Color.RED:
     *      Directory *dir* failed to create. It either already exists or invalid name
     *
     * @param dir   directory that has been written to
     */
    public void appendDirWriteFail(String dir){
        appendToPane("Directory ", ERROR_COLOR);
        appendDirectory(dir);
        appendToPane(" failed to create. It either already exists or invalid name\n", ERROR_COLOR);
    }

    /**
     * Appends the message to the JTextPane with the passed color
     *
     * Solution found at <a href="https://stackoverflow.com/a/9652143">StackOverFlow</a>
     *
     * @param msg   String to append
     * @param c     color of msg
     */
    private void appendToPane(String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset,StyleConstants.FontSize, 10);
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Courier New");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);



        int len = pane.getDocument().getLength();
        pane.setCaretPosition(len);
        pane.setCharacterAttributes(aset, false);
        pane.replaceSelection(msg);
    }

    public void resetConsole(){
        pane.setText("");
    }
}
