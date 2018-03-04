/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine;

import roguelikeengine.largeobjects.Actor;
import roguelikeengine.controller.PlayerWantsToQuitException;
import java.util.HashSet;

/**
 *
 * @author greg
 */
public class Clock {
    private final HashSet<Actor> actors;
    
    public Clock() {
        actors = new HashSet<Actor>();
    }
    
    public void addActor(Actor actor) {
        actors.add(actor);
    }
    
    public void removeActor(Actor actor) {
        actors.remove(actor);
    }
    
    public void clearActors() {
        actors.clear();
    }
    
    public void play() {
        HashSet<Actor> deadActors = new HashSet<>();
        try {
            while (actors.size() > 0) {
                for (Actor a : actors) {
                    boolean alive = a.act();
                    if (!alive) {
                        System.out.println("!!!!");
                        deadActors.add(a);
                    }
                }
                for(Actor a : deadActors) {
                    actors.remove(a);
                }
                deadActors.clear();
            }
        } catch(PlayerWantsToQuitException ex) {
        }
    }
}
