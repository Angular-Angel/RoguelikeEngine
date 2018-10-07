/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.controller;

import roguelikeengine.largeobjects.Body;

/**
 *
 * @author angle
 */
public class BasicAI extends Controller {

    public BasicAI(Body body) {
        super(body);
    }
    
    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public boolean act() throws PlayerWantsToQuitException {
        return getCreature().isAlive();
    }
    
}
