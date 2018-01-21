/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.largeobjects;

import java.util.HashMap;
import roguelikeengine.item.Item;
import stat.Stat;
import stat.StatContainer;


/**
 *
 * @author Greg
 */
public class MeleeAttack extends Attack {
    private Item weapon;
    private Body body;

    public MeleeAttack(String name, Item weapon, Body body) {
        this(name, weapon, body, new StatContainer());
    }
    
    public MeleeAttack(String name, Item weapon, Body body, StatContainer stats) {
        super(name, stats);
        this.body = body;
        this.weapon = weapon;
    }

    @Override
    public String attack(Item i){
        return i.takeAttack(this);
        
    }
}
