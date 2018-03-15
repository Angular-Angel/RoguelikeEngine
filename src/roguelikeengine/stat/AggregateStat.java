/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.stat;

import stat.Stat;
import java.util.ArrayList;
import roguelikeengine.item.Item;
import stat.StatContainer;
import stat.StatDescriptor;

/**
 *
 * @author Greg
 */
public class AggregateStat extends Stat {
    private Item item;
    private String statName; //What stat this is an aggregate of.
    private Type type;

    public AggregateStat(Item i, StatDescriptor statDescriptor) {
        super(statDescriptor);
        this.item = i;
        statName = statDescriptor.identifier;
        type = Type.SUM;
    }

    @Override
    protected float refactorBase() {
        float newScore = 0;
        int num = 0;
        ArrayList<Item> parts = item.getParts();
        if (type == Type.LOWEST) newScore = parts.get(0).stats.getScore(statName);
        for (Item i : parts) {
            switch(type) {
                case AVERAGE: 
                    num++;
                case SUM: newScore += i.stats.getScore(statName);
                    break;
                case HIGHEST: 
                    if (newScore < i.stats.getScore(statName)) {
                        newScore = i.stats.getScore(statName);
                    }
                    break;
                case LOWEST:
                    if (newScore > i.stats.getScore(statName)) {
                        newScore = i.stats.getScore(statName);
                    }
                    break;
            }
        }
        if (type == Type.AVERAGE) newScore /= num;
        return newScore;
    }
    
    @Override
    public void setContainer(StatContainer i) {
    }

    @Override
    public void set(Object obj) {
        if (obj instanceof String)
            statName = (String) obj;
        else if (obj instanceof Item)
            item = (Item) obj;
    }

    @Override
    public void modifyBase(float change) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stat copy() {
        return new AggregateStat(item, getStatDescriptor());
    }
    private enum Type {
        SUM, LOWEST, HIGHEST, AVERAGE
    }
}
