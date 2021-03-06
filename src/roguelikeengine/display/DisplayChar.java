package roguelikeengine.display;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 *
 * @author greg
 */
public class DisplayChar {
    private char symbol;
    private Color color;
    private Color bgColor;
    
    public DisplayChar(char symbol) {
        this(symbol, Color.WHITE);
    }
    
    public DisplayChar(char symbol, Color color) {
        this(symbol, color, Color.BLACK);
    }
    
    /**
     * Constructor
     * @param symbol The character 
     * @param color The Color
     */
    public DisplayChar(char symbol, Color color, Color bgColor){
        setSymbol(symbol);
        setColor(color);
        setBackground(bgColor);
    }

    public DisplayChar(DisplayChar symbol) {
        this(symbol.getSymbol(), symbol.getColor(), symbol.getBackground());
    }

    /**
     * @return the symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void draw(Graphics g, FontMetrics metrics, int x, int y) {
        if(getBackground() != Color.black) {
            g.setColor(getBackground());
            g.fillRect(x * metrics.getMaxAdvance() + 2, y * metrics.getHeight() + 2,
                       metrics.getMaxAdvance(), metrics.getHeight());
        }
        g.setColor(getColor());
        g.drawString("" + getSymbol(), x * metrics.getMaxAdvance() + 2, (y +1) * metrics.getHeight());
    }

    /**
     * @return the bgColor
     */
    public Color getBackground() {
        return bgColor;
    }

    /**
     * @param bgColor the bgColor to set
     */
    public void setBackground(Color bgColor) {
        this.bgColor = bgColor;
    }
}
