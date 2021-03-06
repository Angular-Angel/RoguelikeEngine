package roguelikeengine.display;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * This class is for a window that displays 
 * characters in neat rows and takes input.
 * @author greg
 */
public class RoguelikeInterface extends JPanel {
    
    private DisplayChar[][] display;
    private int displayXDist;
    private int displayYDist;
    private Color backgroundColor;
    private Font displayFont;
    private JFrame window;
    private ArrayList<KeyEvent> keys;

    /**
     * Creates a window of the default size and color.
     * @param title The title of the window.
     */
    public RoguelikeInterface(String title) {
        this(title, 20, 20);
    }
    
    /**
     * Creates a window of the default color.
     * @param title The title of the window.
     * @param xDist The distance across the window should be.
     * @param yDist The distance from top to bottom the window should be.
     */
    public RoguelikeInterface(String title, int xDist, int yDist) {
        this(title, xDist, yDist, Color.BLACK, Color.WHITE, new Font("Monospaced", Font.PLAIN, 12));
    }
    
    /**
     * The full constructor.
     * @param title The title of the window.
     * @param xDist The distance across the window should be.
     * @param yDist The distance from top to bottom the window should be.
     * @param bgColor The background color.
     * @param textColor The textcolor.
     * @param font The font.
     */
    public RoguelikeInterface(String title, int xDist, int yDist, Color bgColor,
                              Color textColor, Font font) {
        super();
        JFrame frame = new JFrame(title);
        this.displayXDist = xDist;
        this.displayYDist = yDist;
        backgroundColor = bgColor;
        displayFont = font;
        keys = new ArrayList<>();
        frame.setBackground(backgroundColor);
        display = new DisplayChar[xDist][yDist];
        setAll(new DisplayChar(' ', Color.WHITE));
        frame.add(this);
        this.setWindow(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(206, 275);
        frame.setResizable(false);
        frame.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {
                keys.add(ke);
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                
            }
        });
        frame.setVisible(true);
    }
    /**
     * The method that draws the interface to the screen.
     * @param g The graphics to draw to.
     */
    @Override
    public void paintComponent(Graphics g) {
        
        g.setFont(getDisplayFont());
        FontMetrics metrics = g.getFontMetrics();
        int winXDist, winYDist;
        winXDist = 6 + getDisplayXDist() * metrics.getMaxAdvance();
        winYDist = (getDisplayYDist() + 2) * metrics.getHeight();
        getWindow().setSize(winXDist, winYDist);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, winXDist, winYDist);
        for (int y = 0; y < getDisplayYDist(); y++) {
            for (int x = 0; x < getDisplayXDist(); x++) {
                getDisplay(x, y).draw(g, metrics, x, y);
            }
        }
    }

    /**
     * 
     * @param x
     * @param y
     * @return 
     */
    public DisplayChar getDisplay(int x, int y) {
        return display[x][y];
    }

    /**
     * 
     * @param display
     * @param x
     * @param y 
     */
    public void setDisplay(DisplayChar display, int x, int y) {
        if (display != null && x >= 0 && y >= 0 && x < getDisplayXDist() && y < getDisplayYDist())
        this.display[x][y] = display;
    }

    /**
     * @return the backgroundColor
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * @param backgroundColor the backgroundColor to set
     */
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * @return the displayXDist
     */
    public int getDisplayXDist() {
        return displayXDist;
    }

    /**
     * @return the displayYDist
     */
    public int getDisplayYDist() {
        return displayYDist;
    }

    /**
     * @return the displayFont
     */
    public Font getDisplayFont() {
        return displayFont;
    }

    /**
     * @param displayFont the displayFont to set
     */
    public void setDisplayFont(Font displayFont) {
        this.displayFont = displayFont;
    }

    /**
     * @return the window
     */
    public JFrame getWindow() {
        return window;
    }

    /**
     * @param window the window to set
     */
    public void setWindow(JFrame window) {
        this.window = window;
    }
    
    /**
     * Gets a key from the user.
     * @return 
     */
    public synchronized KeyEvent getKey() {
        while (keys.isEmpty())
            try {
                wait(10);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        KeyEvent key = keys.get(0);
        keys.remove(0);
        return key;
    }
    
    public synchronized char getKeyChar() {
        return getKey().getKeyChar();
    }
    
    /**
     * Sets all spaces to c.
     * @param c The character to set all spaces to.
     */
    public void setAll(DisplayChar c) {
        for (int i = 0; i < displayXDist; i++) {
            for (int j = 0; j < displayYDist; j++) {
                setDisplay(c, i, j);
            }
        }
    }
    
    /**
     * Writes a string to the Roguelike Interface.
     * @param x The x coordinate to start the string at.
     * @param y The y coordinate to start the string at.
     * @param string The string to write.
     */
    public void writeString(int x, int y, String string) {
        writeString(x, y, string, Color.white);
    }
    
    public void writeString(int x, int y, String string, Color c) {
        for (int i = 0; i < string.length(); i++) {
            setDisplay(new DisplayChar(string.charAt(i), c), x + i, y);
        }
    }
    
    /**
     * Rotates the display.
     * @param r 
     */
    public void rotate(Rotation r)
    {
        switch (r)
        {
            case degree90:
            {
                DisplayChar[][] newImage = 
                        new DisplayChar[getDisplayYDist()][getDisplayXDist()];
                for (int i = 0; i < getDisplayXDist(); i++)
                {
                    for (int j = 0; j < getDisplayYDist(); j++)
                    {
                        newImage[i][j] = getDisplay(j, (getDisplayYDist() -1) - i);
                    }

                }
                display = newImage;
                break;
            }
            case degree180:
            {
                DisplayChar[][] newImage = 
                        new DisplayChar[getDisplayYDist()][getDisplayXDist()];
                for (int i = 0; i < getDisplayXDist(); i++)
                {
                    for (int j = 0; j < getDisplayYDist(); j++)
                    {
                        newImage[i][j] = getDisplay(getDisplayXDist() -1 - i, getDisplayYDist() -1 - j);
                    }

                }
                display = newImage;
                break;
            }
            case degree270:
            {
                DisplayChar[][] newImage = 
                        new DisplayChar[getDisplayYDist()][getDisplayXDist()];
                for ( int i = 0; i < getDisplayXDist(); i++)
                {
                    for ( int j = 0; j < getDisplayYDist(); j++)
                    {
                        newImage[i][j] = getDisplay(getDisplayYDist() - 1 - j, i);
                        
                    }

                }
                display = newImage;
                break;
            }

        }
    }
    
    /**
     * flips the display vertically.
     */
    public void flipVertical() {
        DisplayChar[][] newImage = 
                    new DisplayChar[getDisplayYDist()][getDisplayXDist()];
            for (int i = 0; i < getDisplayXDist(); i++)
            {
                for (int j = 0; j < getDisplayYDist(); j++)
                {
                    newImage[i][j] = getDisplay(i, getDisplayYDist() -1 -j);
                }

            }
            display = newImage;
    }
    
    /**
     * flips the display horizontally.
     */
    public void flipHorizontal() {
        DisplayChar[][] newImage = 
                    new DisplayChar[getDisplayYDist()][getDisplayXDist()];
            for (int i = 0; i < getDisplayXDist(); i++)
            {
                for (int j = 0; j < getDisplayYDist(); j++)
                {
                    newImage[i][j] = getDisplay(getDisplayXDist() - 1 - i, j);
                }

            }
            display = newImage;
    }
    
    public char prompt(String s) {
        Window win = new Window(this, s.length() + 2, 3);
        win.drawString(1, 1, s, Color.white);
        repaint();
        return getKeyChar();
    }
    
    public String getSentence(String s) {
        Window win = new Window(this, getDisplayXDist(), 4);
        win.drawString(1, 1, s, Color.white);
        repaint();
        StringBuilder sb = new StringBuilder();
        char c;
        while (true) {
            c = getKeyChar();
            if (c == '\n')
                return sb.toString();
            else {
                sb.append(c);
                win.drawString(1, 2, sb.toString(), Color.white);
                repaint();
            }
        }
        
    }
        
}
