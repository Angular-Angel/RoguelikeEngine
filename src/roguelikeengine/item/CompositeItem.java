/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.ArrayList;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.largeobjects.Attack;
import static roguelikeengine.Game.game;

/**
 *
 * @author Greg
 */
public class CompositeItem extends Item {
    
    protected ArrayList<Item> parts;
    
    public CompositeItem(ItemDefinition itemDef) {
        super(itemDef);
        this.symbol = new DisplayChar(itemDef.symbol);
        parts = new ArrayList<>();
        for (ItemDefinition component : itemDef.components)
            addPart(component.generateItem());
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
    
    public Item getRandomPart() {
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
    public void refactor() {
        for (Item i : parts) i.refactor();
        super.refactor();
    }

    @Override
    public boolean takeAttack(Attack a) {
        stats.getStat("HP").modifyBase(-a.stats.getScore("Damage"));
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
    public  ArrayList<Attack> getAttacks() {
        ArrayList<Attack> attackListing = super.getAttacks();
        for (Item i : parts) {
            attackListing.addAll(i.getAttacks());
        }
        return attackListing;
    }
    
}
