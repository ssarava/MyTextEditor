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
    JMenuItem arial, bookAntiqua, comicSans, timesNewRoman, msGothic, whiteItem, redItem, grayItem, darkGrayItem; // Color menu
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
        window.setMinimumSize(new Dimension(600, 650));
        window.setLocation(0, 0);
        window.addComponentListener(this);
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
        toretto.setActionCommand("Toretto");
        theme.add(toretto);

        ironman = new JMenuItem("Ironman");
        ironman.addActionListener(this);
        ironman.setActionCommand("Ironman");
        theme.add(ironman);

        levi = new JMenuItem("Levi");
        levi.addActionListener(this);
        levi.setActionCommand("Levi");
        theme.add(levi);

        reshiram = new JMenuItem("Reshiram");
        reshiram.addActionListener(this);
        reshiram.setActionCommand("Reshiram");
        theme.add(reshiram);

        walter = new JMenuItem("Heisenberg");
        walter.addActionListener(this);
        walter.setActionCommand("Heisenberg");
        theme.add(walter);

        zekrom = new JMenuItem("Zekrom");
        zekrom.addActionListener(this);
        zekrom.setActionCommand("Zekrom");
        theme.add(zekrom);

        noTheme = new JMenuItem("None");
        noTheme.addActionListener(this);
        noTheme.setActionCommand("No Theme");
        theme.add(noTheme);


    }
    
    public static double getWidth(JFrame frame) {
        return frame.getSize().getWidth();
    }

    public static double getHeight(JFrame frame) {
        return frame.getSize().getHeight();
    }

    public void ironman() {
        Dimension d = window.getSize();
        int w = (int) d.getWidth();
        int h = (int) d.getHeight();
        textArea.setImage("ironman.png", w - 600, h - 650, 600, 600);
        // textArea.setText("width = " + w + "\nheight = " + h);

        textArea.setText("iron man theme enabled");
    }

    public void toggleWordWrapText() {
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
        String input = JOptionPane.showInputDialog("Enter a font size");
        int fontSize = Integer.parseInt(input);
        if (fontSize < 6 || fontSize > 60) {
            JOptionPane.showMessageDialog(textArea, "You entered an invalid font size", "Invalid font size", JOptionPane.ERROR_MESSAGE);
        } else {
            float newSize = 1.0f * fontSize;
            textArea.setFontSize(newSize);
            System.out.println("Changed font size to " + newSize);
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
    }

    public JFrame getWindow() {
        return window;
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
                textArea.setWordWrapped();
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
                if (textArea.isThemeEnabled()) {
                    textArea.toggleTheme();
                    textArea.clearImage();
                }
                textArea.setBackground(Color.WHITE);
                textArea.setForeground(Color.BLACK);
                textArea.setCaretColor(MyTextArea.DEFAULT_CARET);
                break;
            case "Red":
                if (textArea.isThemeEnabled()) {
                    textArea.toggleTheme();
                    textArea.clearImage();
                }
                textArea.setBackground(Color.RED);
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(Color.WHITE);
                break;
            case "Gray":
                if (textArea.isThemeEnabled()) {
                    textArea.toggleTheme();
                    textArea.clearImage();
                }
                textArea.setBackground(Color.GRAY);
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(MyTextArea.DEFAULT_CARET);
                break;
            case "Dark Gray":
                if (textArea.isThemeEnabled()) {
                    textArea.toggleTheme();
                    textArea.clearImage();
                }
                textArea.setBackground(Color.DARK_GRAY);
                textArea.setForeground(Color.WHITE);
                textArea.setCaretColor(MyTextArea.DEFAULT_CARET);
                break;
            case "Ironman":
                
                textArea.toggleTheme();
                ironman();
                break;
                
            case "No Theme":
                if (textArea.isThemeEnabled()) {
                    textArea.toggleTheme();
                }
                break;
            default:
                System.out.println("Default");
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
            System.out.println("File address and file name: " + fileAddress + fileName);
            BufferedReader br = new BufferedReader(fr); // creates a character buffer stream given 'fr'

            textArea.setText(""); // clear the text area
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                textArea.append(line + "\n");
            }
            br.close(); // close buffered reader
        } catch (Exception e) {
            System.out.println("failed to open file");
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
            System.out.println("from 'Save As', the file name is NULL");
            saveFileAs();
        } else {
            try {
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(textArea.getText());
                window.setTitle(fileName);
                fw.close();

            } catch (Exception e) {
                System.out.println("couldn't save file");
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
            System.out.println("couldn't save file as");
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
            System.out.println("You tried to undo something that can't be undone!");
        }
    }

    public void redo() {
        try {
            textArea.getManager().redo();
        } catch (CannotRedoException e) {
            System.out.println("You tried to redo something that can't be redone!");
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
        if (textArea.isThemeEnabled()) {
            ironman();
        }
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