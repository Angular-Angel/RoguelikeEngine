/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.stat;

import experimental.Stat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.item.CompositeItem;
import roguelikeengine.item.Item;
import roguelikeengine.largeobjects.Creature;
import stat.NoSuchStatException;

/**
 *
 * @author Greg
 */
public class AggregateStat implements Stat {
    private CompositeItem i;
    private String s1;
    private float score;
    private int type;
    public static final int SUM = 1, LOWEST = 2, HIGHEST = 3, AVERAGE = 4;
    
    public AggregateStat(String s1, int type) {
        this.s1 = s1;
        this.type = type;
    }
    
    public AggregateStat(CompositeItem i, String s1, int type) {
        this.i = i;
        this.s1 = s1;
        this.type = type;
    }

    public void refactor() {
        score = 0;
        int num = 0;
        try {
            ArrayList<Item> parts = i.getParts();
            if (type == LOWEST) score = parts.get(0).stats.getScore(s1);
            for (Item i : parts) {
                switch(type) {
                    case AVERAGE: 
                        num++;
                    case SUM: score += i.stats.getScore(s1);
                        break;
                    case HIGHEST: 
                        if (score < i.stats.getScore(s1)) {
                            score = i.stats.getScore(s1);
                        }
                        break;
                    case LOWEST:
                        if (score > i.stats.getScore(s1)) {
                            score = i.stats.getScore(s1);
                        }
                        break;
                }
            }
            if (type == AVERAGE) score /= num;
        } catch(NoSuchStatException | NullPointerException ex) {
            Logger.getLogger(Creature.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<String> getDependencies() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
