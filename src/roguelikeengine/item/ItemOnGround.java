/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import roguelikeengine.area.Location;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Entity;

/**
 *
 * @author Greg
 */
public class ItemOnGround implements Entity{
    private Location location;
    private Item item;
    
    
    public ItemOnGround(Item i, Location l) {
        setItem(i);
        setLocation(l);
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

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }
    
    @Override
    public boolean occupies(Location l) {
        if (l.getArea() == getLocation().getArea() && 
            l.getX() == getLocation().getX() && l.getY() == getLocation().getY())
            return true;
        return false;
    }

    @Override
    public void beAttacked(Attack a) {
        if (a.attack(item)) {
            location.getArea().removeEntity(this);
        }
    }

}
