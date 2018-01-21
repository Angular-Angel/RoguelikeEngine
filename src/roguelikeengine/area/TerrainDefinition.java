/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.area;

import java.awt.*;
import java.util.HashMap;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.MaterialDefinition;
import stat.Stat;
import stat.StatContainer;

/**
 * This class represents a single tile of terrain.
 * @author greg
 */
public class TerrainDefinition {
    private DisplayChar displayChar;
    private MaterialDefinition material;
    private StatContainer stats;
    
    /**
     * The default constructor.
     */
    public TerrainDefinition() {
        this('%', Color.white, null, new StatContainer());
    }
    
    /**
     * The complicated constructor.
     * @param symbol The character to display on the screen.
     * @param color The color.
     * @param mat the material this Terrain is made out of.
     */
    public TerrainDefinition(char symbol, Color color, MaterialDefinition mat, StatContainer stats) {
        this(new DisplayChar(symbol, color), mat, stats);
    }
    
    public TerrainDefinition(DisplayChar symbol, MaterialDefinition mat, StatContainer stats) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        displayChar = symbol;
        material = mat;
    }

    /**
     * @return the symbol
     */
    public char getChar() {
        return getDisplayChar().getSymbol();
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return getDisplayChar().getColor();
    }

    /**
     * @return the displayChar
     */
    public DisplayChar getDisplayChar() {
        return displayChar;
    }
    
    /**
     * @return the transparent
     */
    public boolean isTransparent() {
        return stats.hasStat("Transparent");
    }

    /**
     * @return the passable
     */
    public boolean isPassable() {
        return stats.hasStat("Passable");
    }

    /**
     * @return the material
     */
    public MaterialDefinition getMaterial() {
        return material;
    }
}
