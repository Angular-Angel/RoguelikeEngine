/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.area;

import java.util.ArrayList;
import roguelikeengine.controller.Controller;

/**
 *
 * @author Greg
 */
public class Area {
    public final ArrayList<LocalArea> localAreas;
    public final ArrayList<Controller> controllers;
    
    public LocalArea start;
    
    public Area() {
        localAreas = new ArrayList<>();    
        controllers = new ArrayList<>();
    }
    
    public void add(LocalArea localArea) {
        localAreas.add(localArea);
    }
    
    public void add(Controller controller) {
        controllers.add(controller);
    }
}
