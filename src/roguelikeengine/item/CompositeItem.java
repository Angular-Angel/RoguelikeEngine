/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Body;
import stat.NoSuchStatException;
import stat.StatContainer;
import static roguelikeengine.Game.game;

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
        float size = stats.getScore("Size");
        int target = game.random.nextInt((int) size);
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
    public boolean takeAttack(Attack a) {
        stats.getStat("HP").modify("Damage", -a.stats.getScore("Damage"));
        return (stats.getScore("HP") <= 0);
    }

    @Override
    public void destroy() {
        Location location = getLocation();
        if (location instanceof AreaLocation) {
            AreaLocation areaLocation = (AreaLocation) location;
            for (Item part : parts) {
                areaLocation.getArea().addEntity(new ItemOnGround(part, areaLocation));
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
