/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.area;

import java.util.ArrayList;

/**
 *
 * @author Greg
 */
public class Area {
    public final ArrayList<LocalArea> localAreas;
    
    public Area() {
        localAreas = new ArrayList<>();    
    }
    
    public void add(LocalArea localArea) {
        localAreas.add(localArea);
    }
}
