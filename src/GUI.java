import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;

public class GUI extends JFrame implements ActionListener, KeyListener, ComponentListener, ClipboardOwner {
    MyTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu file, edit, format, color;
    JMenu theme;
    JButton themeButton;
    JMenuItem newOption, openOption, exitOption, saveOption, saveAsOption;
    JMenuItem undoOption, redoOption, cutOption, copyOption, pasteOption, findAndReplace;
    JMenuItem wordWrapOption;
    JMenu font, fontStyle;
    JMenuItem fontSizeOption;
    JMenuItem arial, bookAntiqua, comicSans, timesNewRoman, msGothic, whiteItem, redItem, grayItem, darkGrayItem, customColor;
    JMenuItem highlight, removeHighlight, removeAllHighlights;
    JMenuItem chooseTheme;
    JMenuItem bold, italic, plain;
    String fileName = null, fileAddress = null;
    static boolean caseSensitive = false;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new GUI();
            }
        });
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

        // For all components to be displayed, this must come last
        super.setVisible(true);
    }

    public void createWindow() {
        super.setTitle("MyTextEditor");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(new Dimension(650, 600)); // reset width to 900
        super.setMinimumSize(new Dimension(600, 650)); // reset min-width to 900
        super.setLocation(0, 0);
        super.addComponentListener(this);
        super.setResizable(true);
    }

    public void createTextArea() {
        textArea = new MyTextArea();
        textArea.addKeyListener(this);
    }

    public void createScrollPane() {
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // scrollpane viewport contains the text area
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // subtle difference; remove and observe bottom border
                                                                 // of text area
        super.add(scrollPane);
    }

    public void createMenuBar() {
        menuBar = new JMenuBar();
        super.setJMenuBar(menuBar);

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

        cutOption = new JMenuItem("Cut");
        cutOption.addActionListener(this);
        cutOption.setActionCommand("Cut");
        edit.add(cutOption);

        copyOption = new JMenuItem("Copy");
        copyOption.addActionListener(this);
        copyOption.setActionCommand("Copy");
        edit.add(copyOption);

        pasteOption = new JMenuItem("Paste");
        pasteOption.addActionListener(this);
        pasteOption.setActionCommand("Paste");
        edit.add(pasteOption);

        findAndReplace = new JMenuItem("Find and Replace");
        findAndReplace.addActionListener(this);
        findAndReplace.setActionCommand("Find and Replace");
        edit.add(findAndReplace);
    }

    public void createFormatMenu() {
        wordWrapOption = new JMenuItem("Disable Word Wrap");
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

        highlight = new JMenuItem("Highlight Text");
        highlight.addActionListener(this);
        highlight.setActionCommand("Highlight Text");
        format.add(highlight);

        removeHighlight = new JMenuItem("Remove Highlight");
        removeHighlight.addActionListener(this);
        removeHighlight.setActionCommand("Remove Highlight");
        format.add(removeHighlight);

        removeAllHighlights = new JMenuItem("Remove All Highlights");
        removeAllHighlights.addActionListener(this);
        removeAllHighlights.setActionCommand("Remove All Highlights");
        format.add(removeAllHighlights);
    }

    public void createThemes() {
        chooseTheme = new JMenuItem("Choose Theme");
        chooseTheme.addActionListener(this);
        chooseTheme.setActionCommand("Choose Theme");
        theme.add(chooseTheme);
    }

    public void noTheme() {
        textArea.clearImage();
        if (textArea.isThemeEnabled()) {
            textArea.toggleTheme();
        }
        textArea.setTheme("None");
        textArea.setForeground(MyTextArea.DEFAULT_TEXT);
        // textArea.setText("No theme enabled: " + textArea.isThemeEnabled() +
        // "\ncurrTheme = " + textArea.getTheme() + "\nis Opaque? " +
        // textArea.isOpaque());
    }

    public void showThemeSelection() {
        String[] buttonTxt = {"Toretto", "Iron Man", "Levi Ackermann", "Reshiram", "Zekrom", "Heisenberg", "Remove Theme"};
        JButton[] buttons = new JButton[buttonTxt.length];
        for (int i = 0; i < buttonTxt.length; i++) {
            buttons[i] = new JButton(buttonTxt[i]);
            buttons[i].addActionListener(this);
            buttons[i].setActionCommand(buttonTxt[i]);
        }
        JOptionPane.showConfirmDialog(null, buttons, "Select a Theme", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null);
    }

    public void enableTheme(String theme) {
        if (textArea.getTheme().equals(theme)) {
            // noTheme();
            return;
        }
        textArea.setOpaque(true);
        Dimension d = super.getSize();
        switch (theme) {
            case "ironman":
                textArea.setImage("ironman.png", (int) d.getWidth() - 600, (int) d.getHeight() - 650, 600, 600);
                break;
            case "toretto":
                textArea.setImage("toretto.png", (int) d.getWidth() - 475, (int) d.getHeight() - 650, 425, 600);
                break;
            case "levi":
                textArea.setImage("levi.png", (int) d.getWidth() - 800, (int) d.getHeight() - 650, 800, 600);
                break;
            case "reshiram":
                textArea.setImage("reshiram.png", (int) d.getWidth() - 650, (int) d.getHeight() - 625, 625, 525);
                break;
            case "zekrom":
                textArea.setImage("zekrom.png", (int) d.getWidth() - 635, (int) d.getHeight() - 645, 660, 585);
                break;
            case "heisenberg":
                textArea.setImage("heisenberg.png", (int) d.getWidth() - 500, (int) d.getHeight() - 635, 450, 585);
                break;
            default:
                System.out.println("ruh roh");
        }
        textArea.setTheme(theme);
        if (!textArea.isThemeEnabled()) {
            textArea.toggleTheme();
        }
        // textArea.setText(theme + " theme enabled: " + textArea.isThemeEnabled() +
        // "\ncurrTheme = " + textArea.getTheme() + "\nis Opaque? " +
        // textArea.isOpaque());
        textArea.setForeground(MyTextArea.DEFAULT_TEXT);
    }

    public void findAndReplace() {
        JTextField find = new JTextField();
        JLabel findLabel = new JLabel("Find");

        JLabel replaceLabel = new JLabel("Replace");
        replaceLabel.setVisible(false);

        JTextField replaceText = new JTextField();
        replaceText.setVisible(false);
        JCheckBox caseSensitiveCheck = new JCheckBox("Case sensitive?");
        JCheckBox replace = new JCheckBox("Replace text?");

        JComponent[] inputs = new JComponent[] { findLabel, find, replace, replaceLabel, caseSensitiveCheck, replaceText };

        // work on this
        caseSensitiveCheck.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                int stateChange = e.getStateChange();
                if (stateChange == ItemEvent.SELECTED) {
                    caseSensitive = true;
                }
            } 
        });
        replace.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                int stateChange = e.getStateChange();
                replaceLabel.setVisible(stateChange == ItemEvent.SELECTED);
                replaceText.setVisible(stateChange == ItemEvent.SELECTED);
                if (!replaceText.isVisible()) {
                    replaceText.setText("");
                }
            }
        });

        int response = JOptionPane.showConfirmDialog(null, inputs, "Find (and Replace)", JOptionPane.PLAIN_MESSAGE);
        if (response == JOptionPane.CLOSED_OPTION) {
            return;
        }
        System.out.println(replaceText.getText());

        if (replaceText.getText().equals("")) {
            // simply find

            String search = find.getText(), text = textArea.getText();
            if (!caseSensitive) {
                search = search.toLowerCase();
                text = text.toLowerCase();
            }
            int offset = text.indexOf(search);

            while (offset != -1) {
                try {
                    // add new highlight
                    textArea.getHighlighter().addHighlight(offset, offset + search.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.CYAN));
                    offset = text.indexOf(search, offset + 1);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("concluded finding");
        } else {
            // find AND replace
        }
        caseSensitive = false;
    }

    // private void find(String toFind) {
    //     // textArea.getText().fin
    // }

    public void removeAllHighlights() {
        textArea.getSelectedHighlights().clear();
        textArea.getHighlighter().removeAllHighlights();
    }

    public void removeHighlightFromSelectedText() {
        if (textArea.getSelectedText() == null) {
            System.out.println("Can't remove highlight from unselected text!");
            return;
        }

        if (textArea.getSelectedHighlights().removeIf(pair -> (pair.getStart() == textArea.getSelectionStart() && pair.getEnd() == textArea.getSelectionEnd()))) {
            System.out.println("a highlight for the selected text was removed from the selectedHighlights set");
        } else {
            System.out.println("a highlight for the selected text did not exist in the selectedHighlights set");
            return;
        }

        Highlighter highlighter = textArea.getHighlighter();
        Highlighter.Highlight[] highlights = highlighter.getHighlights();
        for (int index = 0; index < highlights.length; index ++) {
            if (highlights[index].getStartOffset() == textArea.getSelectionStart()) {
                System.out.println("removing highlight found with starting offset = " + highlights[index].getStartOffset());
                highlighter.removeHighlight(highlights[index]);
                return;
            }
        }            
    }

    public boolean highlightSelectedText() {        // returns true if the selected text was highlighted
        if (textArea.getSelectedText() == null) {
            return false;
        }
        for (Pair<Integer> pair: textArea.getSelectedHighlights()) {
            if (pair.getStart() == textArea.getSelectionStart() && pair.getEnd() == textArea.getSelectionEnd()) {
                System.out.println("highlight already contained");
                return false;
            }
        }

        try {
            textArea.getHighlighter().addHighlight(textArea.getSelectionStart(), textArea.getSelectionEnd(), new DefaultHighlighter.DefaultHighlightPainter(MyTextArea.DEFAULT_HIGHLIGHT));
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        Pair<Integer> toAdd = new Pair<>(textArea.getSelectionStart(), textArea.getSelectionEnd());
        System.out.println("Just added " + toAdd.toString());
        textArea.getSelectedHighlights().add(toAdd);
        return true;
    }

    public boolean isSelectedTextHighlighted() {
        for (Pair<Integer> pair: textArea.getSelectedHighlights()) {
            if (textArea.getSelectionStart() == pair.getStart() && textArea.getSelectionEnd() == pair.getEnd()) {
                System.out.println("Selected text is highlighted!");
                return true;
            }
        }
        System.out.println("Selected text is NOT highlighted!");
        return false;
    }


    public void cutText() {
        textArea.replaceSelection("");
    }

    public void copyText() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textArea.getSelectedText()), this);
    }

    public void pasteText() {
        StringBuffer result = new StringBuffer("");
        Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);

        if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
            try {
                result = (StringBuffer) contents.getTransferData(DataFlavor.stringFlavor);
            } catch (UnsupportedFlavorException | IOException e) {
                e.printStackTrace();
            }
        }
        textArea.insert(result.toString(), textArea.getCaretPosition());
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
        int newSize = 12;
        try {
            newSize = Integer.parseInt(JOptionPane.showInputDialog("Enter a font size"));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Unreadable font size!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (newSize < 6 || newSize > 60) {
            JOptionPane.showMessageDialog(textArea, "You entered an invalid font size", "Invalid font size", JOptionPane.ERROR_MESSAGE);
        } else {
            textArea.setFontSize(1.0f * newSize);
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
                break;
            default:
                JOptionPane.showMessageDialog(null, "Something went wrong!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
        // textArea.setText("no theme enabled" + "\ncurrTheme = " + textArea.getTheme()
        // + "\nis Opaque? " + textArea.isOpaque());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
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
            case "Cut":
                cutText();
                break;
            case "Copy":
                copyText();
                break;
            case "Paste":
                pasteText();
                break;
            case "Find and Replace":
                findAndReplace();
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
            case "Highlight Text":
                highlightSelectedText();
                break;
            case "Remove Highlight":
                removeHighlightFromSelectedText();
                break;
            case "Remove All Highlights":
                removeAllHighlights();
                break;
            case "White":
                enableSolidColorTheme("white");
                break;
            case "Red":
                enableSolidColorTheme("red");
                break;
            case "Gray":
                // enableSolidColorTheme("gray");
                isSelectedTextHighlighted();
                break;
            case "Dark Gray":
                enableSolidColorTheme("dark gray");
                break;
            case "custom color":
                enableSolidColorTheme("custom color");
                break;
            case "Choose Theme":
                showThemeSelection();
                break;
            case "Iron Man":
                enableTheme("ironman");
                break;
            case "Toretto":
                enableTheme("toretto");
                break;
            case "Levi Ackermann":
                enableTheme("levi");
                break;
            case "Reshiram":
                enableTheme("reshiram");
                break;
            case "Zekrom":
                enableTheme("zekrom");
                break;
            case "Heisenberg":
                enableTheme("heisenberg");
                break;
            case "Remove Theme":
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
        super.setTitle("New Document");
        fileName = null;
        fileAddress = null;
    }

    /**
     * Functionality for the 'Open' button.
     * <p>
     * Opens the selected file from the {@code FileDialog} this,
     * sets the editor's text to that of the file, and sets the
     * title of the editor this to the name of the opened file.
     */
    public void openFile() {
        FileDialog fd = new FileDialog(this, "Choose a file to open", FileDialog.LOAD);
        fd.setVisible(true);

        if (fd.getFile() != null) {
            fileName = fd.getFile();
            fileAddress = fd.getDirectory();
            super.setTitle(fileName);
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileAddress + fileName)); // creates a character buffer stream given the new file reader

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
            if (textArea.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Your file is empty. No need to save", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            saveFileAs();
        } else {
            try {
                FileWriter fw = new FileWriter(fileAddress + fileName);
                fw.write(textArea.getText());
                super.setTitle(fileName);
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
        FileDialog fd = new FileDialog(this, "Save a file!", FileDialog.SAVE);
        fd.setVisible(true);

        if (fd.getFile() == null) {
            return;
        }

        fileName = fd.getFile();
        fileAddress = fd.getDirectory();
        super.setTitle(fileName);

        try {
            FileWriter fw = new FileWriter(fileAddress + fileName);
            fw.write(textArea.getText());
            fw.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Couldn't save file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Closes the this and exits the application.
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

    public void closethis() {
        switch (JOptionPane.showConfirmDialog(null, "Do you want to save your file?")) {
            case JOptionPane.OK_OPTION:
                saveFile();
                exit();
            case JOptionPane.NO_OPTION:
                exit();
            default:
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
                closethis();
            } else if (keyCode == KeyEvent.VK_J) {
                highlightSelectedText();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        // System.out.println("hello");
        
        // if (!textArea.getTheme().equals("None")) {
        //     Dimension d = super.getSize();
        //     textArea.setImage(textArea.getTheme(), (int) d.getWidth() - 600, (int) d.getHeight() - 650, 600, 600);
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

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        return; // do nothing
    }
}