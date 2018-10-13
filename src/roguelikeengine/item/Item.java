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
import roguelikeengine.largeobjects.Body;
import stat.Stat;
import stat.StatContainer;

/**
 *
 * @author Greg
 */
public class Item {
    public DisplayChar symbol;
    protected ArrayList<Attack> attacks;
    protected ArrayList<ItemMod> mods;
    public final ArrayList<Connection> connections; //outgoing connections
    public final ArrayList<Connection> connectedTo; //incoming connections
    private Location location;
    public StatContainer stats;
    public final ItemDefinition itemDef;

    public Item(ItemDefinition itemDef) {
        this.itemDef = itemDef;
        this.stats = new StatContainer();
        this.mods =  new ArrayList<>();
        this.attacks = new ArrayList<>();
        this.symbol = new DisplayChar(itemDef.symbol);
        connections = new ArrayList<>();
        connectedTo = new ArrayList<>();
        for (ItemDefinition.ConnectionDefinition connectionDefinition : itemDef.connections)
            addConnection(connectionDefinition.generateConnection(this));
        
        stats.addAllStats(itemDef.stats.viewStats());
        addAttacks(itemDef.getAttacks());
    }
    
    public void addConnection(Connection connection) {
    	Item item = connection.destination;
    	connections.add(connection);
        item.setLocation(new ItemLocation(this));
        item.connectedTo.add(connection);
    }
    
    public void addConnection(Item item, StatContainer statContainer) {
    	Connection connection = new Connection(this, item);
    	connection.stats.addAllStats(statContainer);
    	addConnection(connection);
    }
    
    public void addConnection(Item item, Stat... stats) {
    	addConnection(item, new StatContainer(stats));
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
    
    public  ArrayList<Attack> getAttacks() {
        ArrayList<Attack> attackListing = new ArrayList<>();
        attackListing.addAll(attacks);
        for (Connection connection : connections) {
            attackListing.addAll(connection.destination.getAttacks());
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
    
    public void use(RoguelikeInterface display, Body b) {
        itemDef.useScript.run(display, this, b);
    }
    
    public boolean takeAttack(Attack attack) {
        System.out.println(stats.getScore("HP") + ", " + attack.stats.getScore("Damage"));
        stats.getStat("HP").modify("Damage", -attack.stats.getScore("Damage"));
        return (stats.getScore("HP") <= 0);
    }
    
    public void refactor() {
    	for (Connection connection : connections) connection.destination.refactor();
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
        if (location instanceof AreaLocation) {
            AreaLocation areaLocation = (AreaLocation) location;
            for (Connection connection : connections) {
                areaLocation.getArea().addEntity(new ItemOnGround(connection.destination, areaLocation));
            }
            areaLocation.getArea().removeItem(this);
        } else if (location instanceof ItemLocation) {
            ItemLocation itemLocation = (ItemLocation) location;
            for (Connection connection : connections) {
                itemLocation.getContainer().addConnection(connection.destination);
            }
            for (Connection connection : connectedTo) {
                connection.origin.connections.remove(connection);
            }
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
}