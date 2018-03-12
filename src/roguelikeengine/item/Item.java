/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.*;
import roguelikeengine.area.AreaLocation;
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
public class Item {
    protected ArrayList<ItemMod> mods;
    protected ArrayList<Attack> attacks;
    private Location location;
    public DisplayChar symbol;
    public StatContainer stats;
    public final ItemDefinition itemDef;

    public Item(ItemDefinition itemDef) {
        this.itemDef = itemDef;
        this.stats = new StatContainer();
        this.mods =  new ArrayList<>();
        this.attacks = new ArrayList<>();
        this.symbol = new DisplayChar(itemDef.symbol);
        
        stats.addAllStats(itemDef.stats.viewStats());
        addAttacks(itemDef.getAttacks());
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
    
    public ArrayList<Attack> getAttacks() {
        ArrayList<Attack> attackListing = new ArrayList<>();
        for (Attack a : attacks) {
            attackListing.add(a);
        }
        return attackListing;
    }
    
    public String getName() {
        String name = "";
        for (ItemMod i : mods) {
            if (!name.contains(i.getAdjective())) name += (i.getAdjective() + " ");
        }
        name += itemDef.getName(0);
        return name;
    }
    
    public void use(RoguelikeInterface display, Creature b) {
        itemDef.useScript.run(display, this, b);
    }
    
    public boolean takeAttack(Attack attack) {
        stats.getStat("HP").modify("Damage", -attack.stats.getScore("Damage"));
        return (stats.getScore("HP") <= 0);
    }
    
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
    
    public void destroy() {
        Location location = getLocation();
        if (location instanceof AreaLocation) {
            AreaLocation areaLocation = (AreaLocation) location;
            areaLocation.getArea().removeItem(this);
        } else if (location instanceof ItemLocation) {
            ItemLocation itemLocation = (ItemLocation) location;
            itemLocation.getContainer().removePart(this);
        }
    }

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
    
    public boolean containsPart(String s) {
        return false;
    }
}