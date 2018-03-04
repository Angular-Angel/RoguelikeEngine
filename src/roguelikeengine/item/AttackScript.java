/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Creature;
/**
 *
 * @author greg
 */
public interface AttackScript {
    
    public Attack generateAttack(Creature attacker, Item weapon, Attack attack);
    
}
