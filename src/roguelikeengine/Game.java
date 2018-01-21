/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roguelikeengine;

import java.util.Random;
import roguelikeengine.display.RoguelikeInterface;

/**
 *
 * @author greg
 */
public abstract class Game {
    public Random random;
    public RoguelikeInterface display;
    public Registry registry;
    public Clock clock;
    public static Game game;
    
    public Game() {
        random = new Random();
        registry = new Registry();
        clock = new Clock();
        game = this;
    }
    
    public abstract void start();
    
}
