package roguelikeengine.largeobjects;

import stat.StatContainer;
import roguelikeengine.display.DisplayChar;

/**
 *
 * @author greg
 */
public class BodyDefinition{
    private final String name;
    private final DisplayChar symbol;
    private final BiologyScript bioScript;
    public StatContainer stats;
    
    public BodyDefinition(String name, DisplayChar d, StatContainer stats, BiologyScript script) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        this.name = name;
        this.symbol = d;
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
