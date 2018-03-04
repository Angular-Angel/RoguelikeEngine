/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

import roguelikeengine.largeobjects.Creature;

/**
 *
 * @author greg
 */
public class Target implements AIGoal{
    private Creature target;
    private int distance;
    
    public Target(Creature b, int d) {
        target = b;
        distance = d;
    }

    /**
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    @Override
    public boolean takeAction(Creature b) {
        
        return b.moveTo(target.getMap().lowestAdjacent(b.getLocation()));
    }
}
