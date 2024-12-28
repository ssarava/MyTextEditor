import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class GUI implements ActionListener, KeyListener, ComponentListener {

    JFrame window;
    MyTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar; // a "menu of menus" in bar format
    JMenu file, edit, format, color, theme; // Menu bar items
    JMenuItem newOption, openOption, exitOption, saveOption, saveAsOption; // File menu items
    JMenuItem undoOption, redoOption; // Edit menu items
    JMenuItem wordWrapOption; // Format menu items
    JMenu font, fontStyle;
    JMenuItem fontSizeOption;
    JMenuItem arial, bookAntiqua, comicSans, timesNewRoman, msGothic, whiteItem, redItem, grayItem, darkGrayItem, customColor; // Color menu
    JMenuItem toretto, ironman, levi, reshiram, walter, zekrom, noTheme;
    JMenuItem bold, italic, plain;

    String fileName = null, fileAddress = null;

    public static void main(String[] args) {
        new GUI();
    }

    public GUI() {
        createWindow();
        createTextArea();
        createScrollPane();
        createMenuBar();
        createFileMenu();
        createEditMenu();
        createFormatMenu();
        createColorMenu();
        createThemes();

        // Make sure this is last, so that the window and all components are displayed
        window.setVisible(true);
    }

    public void createWindow() {
        window = new JFrame("MyTextEditor");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(new Dimension(900, 600));
        window.setMinimumSize(new Dimension(900, 650));
        window.setLocation(0, 0);
        window.addComponentListener(this);
        window.setResizable(true);
    }

    public void createTextArea() {
        textArea = new MyTextArea();
        textArea.addKeyListener(this);
    }

    public void createScrollPane() {
        // scrollpane viewport contains the text area
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // subtle difference; remove and observe bottom border of text area
        window.add(scrollPane);
    }

    public void createMenuBar() {
        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        file = new JMenu("File");
        edit = new JMenu("Edit");
        format = new JMenu("Format");
        color = new JMenu("Color");
        theme = new JMenu("Theme");

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(format);
        menuBar.add(color);
        menuBar.add(theme);
    }

    public void createFileMenu() {
        newOption = new JMenuItem("New");
        newOption.addActionListener(this);
        newOption.setActionCommand("New");
        file.add(newOption);

        openOption = new JMenuItem("Open");
        openOption.addActionListener(this);
        openOption.setActionCommand("Open");
        file.add(openOption);

        saveOption = new JMenuItem("Save");
        saveOption.addActionListener(this);
        saveOption.setActionCommand("Save");
        file.add(saveOption);

        saveAsOption = new JMenuItem("Save As");
        saveAsOption.addActionListener(this);
        saveAsOption.setActionCommand("Save As");
        file.add(saveAsOption);

        exitOption = new JMenuItem("Exit");
        exitOption.addActionListener(this);
        exitOption.setActionCommand("Exit");
        file.add(exitOption);
    }

    public void createEditMenu() {
        undoOption = new JMenuItem("Undo");
        undoOption.addActionListener(this);
        undoOption.setActionCommand("Undo");
        edit.add(undoOption);

        redoOption = new JMenuItem("Redo");
        redoOption.addActionListener(this);
        redoOption.setActionCommand("Redo");
        edit.add(redoOption);
    }

    public void createFormatMenu() {
        wordWrapOption = new JMenuItem("Enable Word Wrap");
        wordWrapOption.addActionListener(this);
        wordWrapOption.setActionCommand("Toggle Word Wrap");
        format.add(wordWrapOption);

        font = new JMenu("Font");

        arial = new JMenuItem("Arial");
        arial.addActionListener(this);
        arial.setActionCommand("Arial");
        font.add(arial);

        bookAntiqua = new JMenuItem("Book Antiqua");
        bookAntiqua.addActionListener(this);
        bookAntiqua.setActionCommand("BA");
        font.add(bookAntiqua);

        comicSans = new JMenuItem("Comic Sans");
        comicSans.addActionListener(this);
        comicSans.setActionCommand("CS");
        font.add(comicSans);

        timesNewRoman = new JMenuItem("Times New Roman");
        timesNewRoman.addActionListener(this);
        timesNewRoman.setActionCommand("TNR");
        font.add(timesNewRoman);

        msGothic = new JMenuItem("MS Gothic");
        msGothic.addActionListener(this);
        msGothic.setActionCommand("MSG");
        font.add(msGothic);

        format.add(font);

        fontSizeOption = new JMenuItem("Font Size");
        fontSizeOption.addActionListener(this);
        fontSizeOption.setActionCommand("Font Size");
        format.add(fontSizeOption);

        fontStyle = new JMenu("Font Style");

        bold = new JMenuItem("Bold");
        bold.addActionListener(this);
        bold.setActionCommand("Bold");
        fontStyle.add(bold);

        italic = new JMenuItem("Italic");
        italic.addActionListener(this);
        italic.setActionCommand("Italic");
        fontStyle.add(italic);

        plain = new JMenuItem("Normal");
        plain.addActionListener(this);
        plain.setActionCommand("Plain");
        fontStyle.add(plain);
        
        format.add(fontStyle);
    }

    public void createThemes() {
        toretto = new JMenuItem("Dom Toretto");
        toretto.addActionListener(this);
        toretto.setActionCommand("toretto");
        theme.add(toretto);

        ironman = new JMenuItem("Ironman");
        ironman.addActionListener(this);
        ironman.setActionCommand("ironman");
        theme.add(ironman);

        levi = new JMenuItem("Levi");
        levi.addActionListener(this);
        levi.setActionCommand("levi");
        theme.add(levi);

        reshiram = new JMenuItem("Reshiram");
        reshiram.addActionListener(this);
        reshiram.setActionCommand("reshiram");
        theme.add(reshiram);

        walter = new JMenuItem("Heisenberg");
        walter.addActionListener(this);
        walter.setActionCommand("heisenberg");
        theme.add(walter);

        zekrom = new JMenuItem("Zekrom");
        zekrom.addActionListener(this);
        zekrom.setActionCommand("zekrom");
        theme.add(zekrom);

        noTheme = new JMenuItem("None");
        noTheme.addActionListener(this);
        noTheme.setActionCommand("noTheme");
        theme.add(noTheme);
    }

    public void noTheme() {
        textArea.clearImage();
        if (textArea.isThemeEnabled()) {
            textArea.toggleTheme();
        }
        textArea.setTheme("None");
        textArea.setForeground(MyTextArea.DEFAULT_TEXT);
        // textArea.setText("no theme enabled" + "\ncurrTheme = " + textArea.getTheme());
    }

    public void ironman() {
        if (textArea.getTheme().equals("ironman")) {
            noTheme();
        } 
        else {
            textArea.setOpaque(true);
            Dimension d = window.getSize();
            textArea.setImage("ironman.png", (int) d.getWidth() - 600, (int) d.getHeight() - 650, 600, 600);
            if (!textArea.isThemeEnabled()) {
                textArea.toggleTheme();
            }
            textArea.setForeground(MyTextArea.DEFAULT_TEXT);
            textArea.setTheme("ironman");
            // textArea.setText("ironman theme enabled: "  + textArea.isThemeEnabled() + "\ncurrTheme = " + textArea.getTheme());
        }
    }

    public void toretto() {
        if (textArea.getTheme().equals("toretto")) {
            noTheme();
        } else {
            textArea.setOpaque(true);
            Dimension d = window.getSize();
            textArea.setImage("toretto.png", (int) d.getWidth() - 475, (int) d.getHeight() - 650, 425, 600);
            if (!textArea.isThemeEnabled()) {
                textArea.toggleTheme();
            }
            textArea.setForeground(MyTextArea.DEFAULT_TEXT);
            textArea.setTheme("toretto");
            // textArea.setText("dom toretto theme enabled: " + textArea.isThemeEnabled() + "\ncurrTheme = " + textArea.getTheme() + "\nis Opaque? " + textArea.isOpaque());
        }
    }

    public void enableTheme(String theme) {
        switch (theme) {
            case "ironman":
                if (textArea.getTheme().equals("ironman")) {
                    noTheme();
                } 
                else {
                    textArea.setOpaque(true);
                    Dimension d = window.getSize();
                    textArea.setImage("ironman.png", (int) d.getWidth() - 600, (int) d.getHeight() - 650, 600, 600);
                    if (!textArea.isThemeEnabled()) {
                        textArea.toggleTheme();
                    }
                    textArea.setForeground(MyTextArea.DEFAULT_TEXT);
                    textArea.setTheme("ironman");
                    // textArea.setText("ironman theme enabled: "  + textArea.isThemeEnabled() + "\ncurrTheme = " + textArea.getTheme());
                }
                break;
            case "toretto":
                if (textArea.getTheme().equals("toretto")) {
                    noTheme();
                } else {
                    textArea.setOpaque(true);
                    Dimension d = window.getSize();
                    textArea.setImage("toretto.png", (int) d.getWidth() - 475, (int) d.getHeight() - 650, 425, 600);
                    if (!textArea.isThemeEnabled()) {
                        textArea.toggleTheme();
                    }
                    textArea.setForeground(MyTextArea.DEFAULT_TEXT);
                    textArea.setTheme("toretto");
                    // textArea.setText("dom toretto theme enabled: " + textArea.isThemeEnabled() + "\ncurrTheme = " + textArea.getTheme() + "\nis Opaque? " + textArea.isOpaque());
                }
                break;
            default:
            System.out.println("weird functinoality");
        }
    }

    public void levi() {
        if (textArea.getTheme().equals("levi")) {
            noTheme();
        } else {
            textArea.setOpaque(true);
            Dimension d = window.getSize();
            textArea.setImage("levi.png", (int) d.getWidth() - 800, (int) d.getHeight() - 650, 800, 600);
            if (!textArea.isThemeEnabled()) {
                textArea.toggleTheme();
            }
            textArea.setForeground(MyTextArea.DEFAULT_TEXT);
            textArea.setTheme("levi");
            // textArea.setText("levi theme enabled: " + textArea.isThemeEnabled() + "\ncurrTheme = " + textArea.getTheme());
        }
    }

    public void reshiram() {
        if (textArea.getTheme().equals("reshiram")) {
            noTheme();
        } else {
            textArea.setOpaque(true);
            Dimension d = window.getSize();
            textArea.setImage("reshiram.png", (int) d.getWidth() - 650, (int) d.getHeight() - 625, 625, 525);
            if (!textArea.isThemeEnabled()) {
                textArea.toggleTheme();
            }
            textArea.setForeground(MyTextArea.DEFAULT_TEXT);
            textArea.setTheme("reshiram");
            // textArea.setText("reshiram theme enabled: " + textArea.isThemeEnabled() + "\ncurrTheme = " + textArea.getTheme());
        }
    }

    public void zekrom() {
        if (textArea.getTheme().equals("zekrom")) {
            noTheme();
        } else {
            textArea.setOpaque(true);
            Dimension d = window.getSize();
            textArea.setImage("zekrom.png", (int) d.getWidth() - 635, (int) d.getHeight() - 645, 660, 585);
            if (!textArea.isThemeEnabled()) {
                textArea.toggleTheme();
            }
            textArea.setForeground(MyTextArea.DEFAULT_TEXT);
            textArea.setTheme("zekrom");
            // textArea.setText("zekrom theme enabled: " + textArea.isThemeEnabled() + "\ncurrTheme = " + textArea.getTheme());
        }
    }

    public void heisenberg() {
        if (textArea.getTheme().equals("heisenberg")) {
            noTheme();
        } else {
            textArea.setOpaque(true);
            Dimension d = window.getSize();
            textArea.setImage("heisenberg.png", (int) d.getWidth() - 500, (int) d.getHeight() - 635, 450, 585);
            if (!textArea.isThemeEnabled()) {
                textArea.toggleTheme();
            }
            textArea.setForeground(MyTextArea.DEFAULT_TEXT);
            textArea.setTheme("heisenberg");
            // textArea.setText("heisenberg theme enabled: " + textArea.isThemeEnabled() + "\ncurrTheme = " + textArea.getTheme());
        }
    }

    public void toggleWordWrapText() {
        textArea.setWordWrapped();
        wordWrapOption.setText(textArea.isWordWrapped() ? "Disable Word Wrap" : "Enable Word Wrap");
    }

    public void toggleBold() {
        textArea.setFontStyle(textArea.isBolded() ? Font.PLAIN : Font.BOLD);
        textArea.setBolded();
    }

    public void toggleItalic() {
        textArea.setFontStyle(textArea.isItalicized() ? Font.PLAIN : Font.ITALIC);
        textArea.setItalicized();
    }

    public void changeFontSize() {
        int fontSize = 12;
        String input = JOptionPane.showInputDialog("Enter a font size");
        try {
            fontSize = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Unreadable font size!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (fontSize < 6 || fontSize > 60) {
            JOptionPane.showMessageDialog(textArea, "You entered an invalid font size", "Invalid font size", JOptionPane.ERROR_MESSAGE);
        } else {
            float newSize = 1.0f * fontSize;
            textArea.setFontSize(newSize);
        }
    }

    public void createColorMenu() {
        whiteItem = new JMenuItem("White");
        whiteItem.addActionListener(this);
        whiteItem.setActionCommand("White");
        color.add(whiteItem);
        
        redItem = new JMenuItem("Red");
        redItem.addActionListener(this);
        redItem.setActionCommand("Red");
        color.add(redItem);

        grayItem = new JMenuItem("Gray");
        grayItem.addActionListener(this);
        grayItem.setActionCommand("Gray");
        color.add(grayItem);

        darkGrayItem = new JMenuItem("Dark Gray");
        darkGrayItem.addActionListener(this);
        darkGrayItem.setActionCommand("Dark Gray");
        color.add(darkGrayItem);

        customColor = new JMenuItem("Custom");
        customColor.addActionListener(this);
        customColor.setActionCommand("custom color");
        color.add(customColor);
    }

    public void enableSolidColorTheme(String color) {
        if (textArea.isThemeEnabled()) {
            textArea.toggleTheme();
            textArea.clearImage();
            noTheme();
        }
        textArea.setOpaque(true);
        color = color.toLowerCase();
        switch (color) {
            case "white":
                textArea.setBackground(Color.WHITE);
                textArea.setForeground(Color.BLACK);
                textArea.setCaretColor(MyTextArea.DEFAULT_CARET);
                break;
            case "red":
                textArea.setBackground(Color.RED);
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(Color.WHITE);
                break;
            case "gray":
                textArea.setBackground(Color.GRAY);
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(MyTextArea.DEFAULT_CARET);
                break;
            case "dark gray":
                textArea.setBackground(Color.DARK_GRAY);
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(MyTextArea.DEFAULT_CARET);
                break;
            case "custom color":
                Color selectedColor = JColorChooser.showDialog(null, "Choose a Color", Color.MAGENTA);
                textArea.setBackground(selectedColor);
                textArea.setForeground(Color.BLACK);
                textArea.setCaretColor(Color.BLACK);
            default:
                JOptionPane.showMessageDialog(null, "Something went wrong!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
        // textArea.setText("no theme enabled" + "\ncurrTheme = " + textArea.getTheme() + "\nis Opaque? " + textArea.isOpaque());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                newFile();
                break;
            case "Open":
                openFile();
                break;
            case "Save":
                saveFile();
                break;
            case "Save As":
                saveFileAs();
                break;
            case "Exit":
                exit();
            case "Undo":
                undo();
                break;
            case "Redo":
                redo();
                break;
            case "Toggle Word Wrap":
                toggleWordWrapText();
                break;
            case "Arial":
                textArea.setFontFamily("Arial");
                break;
            case "BA":
                textArea.setFontFamily("Book Antiqua");
                break;
            case "CS":
                textArea.setFontFamily("Comic Sans");
                break;
            case "TNR":
                textArea.setFontFamily("Times New Roman");
                break;
            case "MSG":
                textArea.setFontFamily("MS Gothic");
                break;
            case "Font Size":
                changeFontSize();
                break;
            case "Bold":
                toggleBold();
                break;
            case "Italic":
                toggleItalic();
                break;
            case "Plain":
                textArea.setFontStyle(Font.PLAIN);
                break;
            case "White":
                enableSolidColorTheme("white");
                break;
            case "Red":
                enableSolidColorTheme("red");
                break;
            case "Gray":
                enableSolidColorTheme("gray");
                break;
            case "Dark Gray":
                enableSolidColorTheme("dark gray");
                break;
            case "custom color":
                enableSolidColorTheme("custom color");
                break;
            case "ironman":
                // ironman();
                enableTheme("ironman");
                break;
            case "toretto":
                // toretto();
                enableTheme("toretto");
                break;
            case "levi":
                levi();
                break;
            case "reshiram":
                reshiram();
                break;
            case "zekrom":
                zekrom();
                break;
            case "heisenberg":
                heisenberg();
                break;
            case "noTheme":
                noTheme();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Something went wrong!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Functionality for the 'New' button.
     * <p>
     * Erases all text in the text area and sets title to "New Document".
     */
    public void newFile() {
        textArea.setText("");
        window.setTitle("New Document");
        fileName = null;
        fileAddress = null;
    }

    /**
     * Functionality for the 'Open' button.
     * <p>
     * Opens the selected file from the {@code FileDialog} window,
     * sets the editor's text to that of the file, and sets the
     * title of the editor window to the name of the opened file.
     */
    public void openFile() {
        FileDialog fd = new FileDialog(window, "Choose a file to open", FileDialog.LOAD);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            window.setTitle(fileName);
        }

        try {
            FileReader fr = new FileReader(fileAddress + fileName); // reads the chosen file
            BufferedReader br = new BufferedReader(fr); // creates a character buffer stream given 'fr'

            textArea.setText(""); // clear the text area
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                textArea.append(line + "\n");
            }
            br.close(); // close buffered reader
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to open file", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Functionality for the 'Save' button.
     * <p>
     * If no current file exists, calls {@code saveFileAs()}.
     * Otherwise, this function overwrites the existing text
     * in the editor and saves it to the current file specified
     * by {@code fileName}.
     */
    public void saveFile() {
        if (fileName == null) {
            JOptionPane.showMessageDialog(null, "From 'Save As', the file name is NULL", "Error", JOptionPane.ERROR_MESSAGE);
            saveFileAs();
        } else {
            try {
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(textArea.getText());
                window.setTitle(fileName);
                fw.close();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Couldn't save file!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Functionality for the 'Save As' button.
     * <p>
     * Creates a new file with the text in the text area.
     * This function writes this text into a file whose name
     * is chosen in the selected directory.
     */
    public void saveFileAs() {
        FileDialog fd = new FileDialog(window, "Save a file!", FileDialog.SAVE);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            window.setTitle(fileName);
        }

        try {
            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(textArea.getText());
            fw.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Couldn't save file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Closes the window and exits the application.
     */
    public void exit() {
        System.exit(0);
    }

    public void undo() {
        try {
            textArea.getManager().undo();
        } catch (CannotUndoException e) {
            JOptionPane.showMessageDialog(null, "You tried to undo something that can't be undone!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void redo() {
        try {
            textArea.getManager().redo();
        } catch (CannotRedoException e) {
            JOptionPane.showMessageDialog(null, "You tried to redo something that can't be redone!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isMetaDown()) {
            int keyCode = e.getKeyCode();
            if (e.isShiftDown() && keyCode == KeyEvent.VK_S) {
                saveFileAs();
            } else if (keyCode == KeyEvent.VK_S) {
                saveFile();
            } else if (keyCode == KeyEvent.VK_B) {
                toggleBold();
            } else if (keyCode == KeyEvent.VK_I) {
                toggleItalic();
            } else if (keyCode == KeyEvent.VK_Z) {
                undo();
            } else if (keyCode == KeyEvent.VK_Y) {
                redo();
            } else if (keyCode == KeyEvent.VK_W) {
                int response = JOptionPane.showConfirmDialog(textArea, "Do you want to save your file?");
                switch (response) {
                    case JOptionPane.OK_OPTION:
                        saveFile();
                        break;
                    case JOptionPane.NO_OPTION:
                        exit();
                    default:
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // if (textArea.isThemeEnabled()) {
        //     switch (textArea.getTheme()) {
        //         case "ironman":
        //             ironman();
        //             break;
        //         case "toretto":
        //             toretto();
        //             break;
        //         default:
                    
        //     }
        // }
        return;
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        return;
    }

    @Override
    public void componentShown(ComponentEvent e) {
        return;
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        return;
    }
}