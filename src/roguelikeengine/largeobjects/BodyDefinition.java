package roguelikeengine.largeobjects;

import roguelikeengine.area.AreaLocation;
import stat.StatContainer;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.ItemDefinition;

/**
 *
 * @author greg
 */
public class BodyDefinition{
    public final String name;
    public final DisplayChar symbol;
    public final BiologyScript bioScript;
    public final ItemDefinition bodyTemplate;
    public StatContainer stats;
    
    public BodyDefinition(String name, DisplayChar d, StatContainer stats, BiologyScript script, ItemDefinition bodyTemplate) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        this.name = name;
        this.symbol = d;
        this.bodyTemplate = bodyTemplate;
        bioScript = script;
        
    }
    
    public Body getCreature(AreaLocation location) {
        return new Body(location, this);
    }
    
    /**
     * @return the symbol
     */
    public DisplayChar getSymbol() {
        return symbol;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the bioScript
     */
    public BiologyScript getBioScript() {
        return bioScript;
    }
}
