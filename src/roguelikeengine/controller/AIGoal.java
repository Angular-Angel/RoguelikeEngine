/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

import roguelikeengine.largeobjects.Creature;
import roguelikeengine.area.*;

/**
 *
 * @author greg
 */
public interface AIGoal {
    public boolean takeAction(Creature b);
}
