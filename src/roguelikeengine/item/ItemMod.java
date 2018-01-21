/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.HashMap;
import stat.Stat;
import stat.StatContainer;

/**
 *
 * @author Greg
 */
public class ItemMod {

    private final String adjective, name;
    public StatContainer stats;
    
    public ItemMod(String name, String adj) {
        this(name, adj, new StatContainer());
    }

    public ItemMod(String name, String adj, StatContainer stats) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        adjective = adj;
        this.name = name;
    }

    public String getAdjective() {
        return adjective;
    }
    
    public ItemMod copy() {
        ItemMod ret = new ItemMod(name, adjective);
        for (String s : stats.getStatList()) {
            ret.stats.addStat(s, stats.viewStat(s).copy());
        }
        return ret;
    }
}