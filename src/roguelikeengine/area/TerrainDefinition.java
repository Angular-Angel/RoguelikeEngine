/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.area;

import java.awt.*;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.MaterialDefinition;
import stat.StatContainer;

/**
 * This class represents a single tile of terrain.
 * @author greg
 */
public class TerrainDefinition {
	public final DisplayChar displayChar;
    public final MaterialDefinition material;
    private StatContainer stats;
    public final String name;
    
    /**
     * The default constructor.
     */
    public TerrainDefinition() {
        this("Null Terrain", '%', Color.white, null, new StatContainer());
    }
    
    /**
     * The complicated constructor.
     * @param symbol The character to display on the screen.
     * @param color The color.
     * @param mat the material this Terrain is made out of.
     */
    public TerrainDefinition(String name, char symbol, Color color, MaterialDefinition mat, StatContainer stats) {
        this(name, new DisplayChar(symbol, color), mat, stats);
    }
    
    public TerrainDefinition(String name, DisplayChar symbol, MaterialDefinition mat, StatContainer stats) {
    	this.name = name;
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

	public String getName() {
		return name;
	}
}
