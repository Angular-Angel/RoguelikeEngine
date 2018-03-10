/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.largeobjects.Attack;

/**
 *
 * @author Greg
 */
public class SimpleItem extends Item {

    public SimpleItem(ItemDefinition def) {
        super(def);
    }
    
    @Override
    public boolean containsPart(String s) {
        return false;
    }

    @Override
    public boolean takeAttack(Attack a) {
        stats.getStat("HP").modify("Damage", -a.stats.getScore("Damage"));
        return (stats.getScore("HP") <= 0);
    }

    @Override
    public void destroy() {
        System.out.println(getName() + " Destroyed!");
        Location location = getLocation();
        if (location instanceof AreaLocation) {
            AreaLocation areaLocation = (AreaLocation) location;
            areaLocation.getArea().removeItem(this);
        } else if (location instanceof ItemLocation) {
            ItemLocation itemLocation = (ItemLocation) location;
            itemLocation.getContainer().removePart(this);
        }
    }
    
}
