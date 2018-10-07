/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import stat.StatContainer;
import stat.Stat;
import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author Greg
 */
public class MaterialDefinition {
	public final String name;
	public final Color color;
	public final DamageScript damageScript;
    public StatContainer stats;
    

    public MaterialDefinition(String name, Color c, DamageScript script) {
        this(name, c, new StatContainer(), script);
    }
    
    public MaterialDefinition(String name, Color c, 
            StatContainer stats, DamageScript script) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        this.name = name;
        this.color = c;
        damageScript = script;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the damageScript
     */
    public DamageScript getDamageScript() {
        return damageScript;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }
}
