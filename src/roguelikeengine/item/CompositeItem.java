/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Body;
import stat.NoSuchStatException;
import stat.Stat;
import stat.StatContainer;

/**
 *
 * @author Greg
 */
public class CompositeItem extends Item {
    
    protected ArrayList<Item> parts;
    private String name;
    private DisplayChar symbol;
    private ItemScript useScript;
    
    public CompositeItem(String name, DisplayChar symbol) {
        this(name, symbol, new StatContainer(), null);
    }
    
    public CompositeItem(String name, DisplayChar symbol, StatContainer stats, ItemScript use) {
        super(stats);
        this.name = name;
        this.symbol = symbol;
        useScript = use;
        parts = new ArrayList<>();
    }
    
    public void addPart(Item part) {
        parts.add(part);
        part.setLocation(new ItemLocation(this));
    }
    
    public void removePart(Item part) {
        parts.remove(part);
    }
    
    @Override
    public boolean containsPart(String s) {
        if (getName().equals(s)) return true;
        for (Item i : getParts()) {
            if (i.containsPart(s))
                return true;
        }
        return false;
    }
    
    public final ArrayList<Item> getParts() {
        return parts;
    }
    
    public Item getRandomPart() throws NoSuchStatException {
        Random random = new Random();
        float size = stats.getScore("Size");
        int target = random.nextInt((int) size);
        for (Item part : getParts()) {
            if (target < part.stats.getScore("Size"))
                return part;
            else target -= part.stats.getScore("Size");
        }
        return null;
    }
    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public DisplayChar getSymbol() {
        return symbol;
    }
    
    @Override
    public void refactor() {
        for (Item i : parts) i.refactor();
        super.refactor();
    }

    @Override
    public String takeAttack(Attack a) {
        int hitLoc = 0;
        try {
            hitLoc = (int) (Math.random() * stats.getScore("Size"));
        } catch (NoSuchStatException ex) {
            Logger.getLogger(CompositeItem.class.getName()).log(Level.SEVERE, null, ex);
        }
        Item i = null;
        
        for (Item e : parts) {
            try {
                if (hitLoc > e.stats.getScore("Size")) {hitLoc -= e.stats.getScore("Size");}
                else {i = e; break;}
            } catch (NoSuchStatException ex) {
                Logger.getLogger(Body.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (i != null) {
            String result = i.takeAttack(a);
            if (result.contains("Deleted")) {
                removePart(i);
            }
            return result;
        }
        else return "Nothig interesting happened.";
    }

    @Override
    public void destroy() {
        Location location = getLocation();
        if (location instanceof AreaLocation) {
            AreaLocation areaLocation = (AreaLocation) location;
            for (Item part : parts) {
                areaLocation.getArea().addEntity(new ItemOnGround(areaLocation, part));
            }
            areaLocation.getArea().removeItem(this);
        } else if (location instanceof ItemLocation) {
            ItemLocation itemLocation = (ItemLocation) location;
            for (Item part : parts) {
                itemLocation.getContainer().addPart(part);
            }
            itemLocation.getContainer().removePart(this);
        }
    }

    @Override
    public void use(RoguelikeInterface display, Body b) {
        useScript.run(display, this, b);
    }
    
}
