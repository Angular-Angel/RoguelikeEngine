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
public class MeleeAttack extends Attack {

    public MeleeAttack(String name) {
        this(name, new StatContainer());
    }
    
    public MeleeAttack(String name, StatContainer stats) {
        super(name, stats);
    }

    @Override
    public boolean attack(Item i){
        return i.takeAttack(this);
        
    }
}
