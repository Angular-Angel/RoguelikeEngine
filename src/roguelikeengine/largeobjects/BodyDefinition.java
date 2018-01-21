package roguelikeengine.largeobjects;

import stat.StatContainer;
import stat.Stat;
import java.util.HashMap;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.item.CompositeItem;

/**
 *
 * @author greg
 */
public class BodyDefinition{
    private final String name;
    private final DisplayChar symbol;
    public CompositeItem bodyTemplate;
    private final BiologyScript bioScript;
    public StatContainer stats;
    
    public BodyDefinition(String name, DisplayChar d, StatContainer stats, BiologyScript script) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        this.name = name;
        this.symbol = d;
        bodyTemplate = new CompositeItem(name, symbol);
        bioScript = script;
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
