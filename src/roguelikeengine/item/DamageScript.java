/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import roguelikeengine.largeobjects.Attack;

/**
 *
 * @author greg
 */
public interface DamageScript {
    
    public void run(Attack a, Item i);
    
}
