package roguelikeengine.largeobjects;

import roguelikeengine.area.AreaLocation;
import stat.StatContainer;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.ItemDefinition;

/**
 *
 * @author greg
 */
public class CreatureDefinition{
    public final String name;
    public final DisplayChar symbol;
    public final BiologyScript bioScript;
    public final ItemDefinition bodyTemplate;
    public StatContainer stats;
    
    public CreatureDefinition(String name, DisplayChar d, StatContainer stats, BiologyScript script, ItemDefinition bodyTemplate) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        this.name = name;
        this.symbol = d;
        this.bodyTemplate = bodyTemplate;
        bioScript = script;
        
    }
    
    public Creature getCreature(AreaLocation location) {
        return new Creature(location, this);
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
