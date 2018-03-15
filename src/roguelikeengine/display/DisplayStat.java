/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.display;

import java.util.Observable;
import java.util.Observer;
import stat.Stat;

/**
 *
 * @author angle
 */
public class DisplayStat extends DisplayString implements Observer {
    
    public final Stat stat;

    public DisplayStat(Stat stat) {
        this(0, 0, stat);
    }
    
    public DisplayStat(int x, int y, Stat stat) {
        super(x, y, stat.getStatDescriptor().name + ": " + stat.getScore());
        this.stat = stat;
        stat.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        displayChars.clear();
        append(stat.getStatDescriptor().name + ": " + stat.getScore());
        if (window != null) draw(window);
    }
    
    
    
}
