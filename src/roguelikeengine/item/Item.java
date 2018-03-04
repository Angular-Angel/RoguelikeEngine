/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.*;
import roguelikeengine.area.Location;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Creature;
import stat.StatContainer;

/**
 *
 * @author Greg
 */
public abstract class Item {
    protected ArrayList<ItemMod> mods;
    protected ArrayList<Attack> attacks;
    private Location location;
    public StatContainer stats;

    public Item() {
        this(new StatContainer());
    }
    
    public Item(StatContainer stats) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        mods = new ArrayList<>();
        attacks = new ArrayList<>();
    }

    
    
    public void addAttack(Attack attack) {
        attacks.add(attack);
    }
    
    public void addAttacks(ArrayList<Attack> attacks) {
        this.attacks.addAll(attacks);
    }
    
    public Attack getAttack(String s) {
        for (Attack t : attacks){
            if (t.name.equals(s)) return t;
        }
        return null;
    }
    
    public abstract boolean containsPart(String s);
    
    public ArrayList<Attack> getAttacks() {
        return (ArrayList<Attack>) attacks.clone();
    }
    
    public abstract String getName();
    
    public abstract DisplayChar getSymbol();
    
    public abstract void use(RoguelikeInterface display, Creature b);
    
    public abstract boolean takeAttack(Attack A);
    
    public void refactor() {
        stats.refactor();
        /*for (ItemMod i : mods) {
            for (String s : i.viewStats().keySet()) { 
                if (hasStat(s)) {
                    getStat(s).modify(i.viewStat(s).getScore());
                    System.out.println(s +": " + i.getScore(s));
                } else addStat(s, i.viewStat(s));
            }
        }*/
    }
    
    public Item addMod(ItemMod i) {
        mods.add(i);
        refactor();
        return this;
    }
    
    public abstract void destroy();

    /**
     * @return the location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    
}