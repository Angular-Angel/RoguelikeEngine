package roguelikeengine.controller;

import roguelikeengine.Game;
import roguelikeengine.largeobjects.Actor;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public abstract class Controller implements Actor {
    private Body body;
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
    public Controller(Body body) {
        setBody(body);
    }
    /**
     * @return the body
     */
    public Body getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(Body body) {
        this.body = body;
        body.setController(this);
    }
    
    public abstract boolean isPlayer();
    
    @Override
    public abstract boolean act() throws PlayerWantsToQuitException;

    @Override
    public void addMoves(){
        getBody().addMoves();
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
