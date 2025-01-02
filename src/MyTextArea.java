import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.util.HashSet;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class MyTextArea extends JTextArea {

    private Image bgImage;
    private int imageX, imageY, imageWidth, imageHeight, fontSize, fontStyle;
    private String fontFamily, currTheme;
    private boolean bolded, italicized, wordWrap, themeEnabled;
    private UndoManager manager;

    final static Color DEFAULT_CARET = Color.RED, DEFAULT_TEXT = Color.BLACK, DEFAULT_BG = Color.WHITE, DEFAULT_HIGHLIGHT = Color.CYAN;
    final static int DEFAULT_TEXT_SIZE = 20;
    private HashSet<Pair<Integer>> selectedHighlights;

    public MyTextArea() {
        super();
        initializeFont();
        initializeStyle();
        manager = new UndoManager();
        super.getDocument().addUndoableEditListener(new UndoableEditListener() {

            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                manager.addEdit(e.getEdit());                
            }
        });
    }

    public void initializeFont() {
        fontFamily = "Times New Roman";
        fontStyle = Font.PLAIN;
        fontSize = DEFAULT_TEXT_SIZE;
        bolded = false;
        italicized = false;
        wordWrap = true;
        super.setFont(new Font(fontFamily, fontStyle, DEFAULT_TEXT_SIZE));
    }

    public void initializeStyle() {
        super.setSelectionColor(Color.LIGHT_GRAY);
        themeEnabled = false;
        currTheme = "None";
        super.setCaretColor(DEFAULT_CARET);
        super.setForeground(DEFAULT_TEXT);
        super.setMargin(new Insets(5, 5, 5, 5));
        super.setLineWrap(wordWrap); // don't need this if a horizontal scrollbar is present
        super.setText("Star Wars is an American epic space opera media franchise created by George Lucas, which began with the eponymous 1977 film[a] and quickly became a worldwide pop culture phenomenon. The franchise has been expanded into various films and other media, including television series, video games, novels, comic books, theme park attractions, and themed areas, comprising an all-encompassing fictional universe.[b] Star Wars is one of the highest-grossing media franchises of all time. The the\n" + //
                        "\n" + //
                        "");
        selectedHighlights = new HashSet<>();
    }

    public Set<Pair<Integer>> getSelectedHighlights() {
        return selectedHighlights;
    }

    public String getTheme() {
        return currTheme;
    }

    public void setTheme(String theme) {
        currTheme = theme;
    }

    public boolean isThemeEnabled() {
        return themeEnabled;
    }

    public void toggleTheme() {
        themeEnabled = !themeEnabled;
    }

    public boolean isBolded() {
        return bolded;
    }

    public void setBolded() {
        bolded = !bolded;
    }

    public boolean isItalicized() {
        return italicized;
    }

    public void setItalicized() {
        italicized = !italicized;
    }

    public boolean isWordWrapped() {
        return wordWrap;
    }

    public void setWordWrapped() {
        wordWrap = !wordWrap;
        super.setLineWrap(wordWrap);
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setFontFamily(String familyIn) {
        fontFamily = familyIn;
        super.setFont(new Font(familyIn, fontStyle, fontSize));
    }

    public int getFontStyle() {
        return fontStyle;
    }

    public void setFontStyle(int styleIn) {
        fontStyle = styleIn;
        super.setFont(new Font(fontFamily, fontStyle, fontSize));
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(float newSize) {
        fontSize = (int) newSize;
        super.setFont(getFont().deriveFont(newSize));
    }

    public UndoManager getManager() {
        return manager;
    }

    public void setImage(String theme, int xPos, int yPos, int widthIn, int heightIn) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("images/" + theme));
        // setText("default image width: " + icon.getIconWidth() + "\ndefault image height: " + icon.getIconHeight());  // debugging purposes
        bgImage = icon.getImage();
        imageX = xPos;
        imageY = yPos;
        imageWidth = widthIn;
        imageHeight = heightIn;
        setOpaque(false);
        repaint();
    }

    public void clearImage() {
        bgImage = null;
        imageX = 0;
        imageY = 0;
        imageWidth = 0;
        imageHeight = 0;
        repaint();
    }

    public void paint(Graphics g) {
        if (themeEnabled) {
            g.drawImage(bgImage, imageX, imageY, imageWidth, imageHeight, null);
        } else {
            clearImage();
        }
        super.paint(g);
    }

}
