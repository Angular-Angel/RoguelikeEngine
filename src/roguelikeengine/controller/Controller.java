package roguelikeengine.controller;

import roguelikeengine.Game;
import roguelikeengine.largeobjects.Actor;
import roguelikeengine.largeobjects.Creature;

/**
 *
 * @author greg
 */
public abstract class Controller implements Actor {
    private Creature body;
    private Game game;
    
    public Controller() {
    }
    /**
     * @return the body
     */
    
    /**
     * Constructor
     * @param body The body that this controls.
     */
    public Controller(Creature body) {
        setBody(body);
    }
    /**
     * @return the body
     */
    public Creature getCreature() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(Creature body) {
        this.body = body;
        body.setController(this);
    }
    
    public abstract boolean isPlayer();
    
    @Override
    public abstract boolean act() throws PlayerWantsToQuitException;

    @Override
    public void addMoves(){
        getCreature().addMoves();
    }

    /**
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the game to set
     */
    public void setGame(Game game) {
        this.game = game;
    }
}
