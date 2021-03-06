/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.largeobjects;

import roguelikeengine.item.Item;
import stat.StatContainer;

/**
 *
 * @author Greg
 */
public abstract class Attack {
    
    public final String name;
    public StatContainer stats;
    
    public Attack(String name, StatContainer stats) {
        this.name = name;
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
    }
    
    public Attack(Attack attack) {
        this(attack.name, attack.stats.viewStats());
    }
    
    public abstract boolean attack(Item i);
}
