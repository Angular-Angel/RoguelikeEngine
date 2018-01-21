/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author Greg
 */
public class SimpleItem extends Item {

    ItemDefinition def;

    private SimpleItem(ItemDefinition def) {
        this.def = def;
    }
    
    @Override
    public boolean containsPart(String s) {
        return false;
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        for (ItemMod i : mods) {
            if (!sb.toString().contains(i.getAdjective()))sb.append(i.getAdjective() + " ");
            sb.append(def.getName(0));
        }
        return sb.toString();
    }

    @Override
    public DisplayChar getSymbol() {
        return def.getSymbol();
    }

    @Override
    public void use(RoguelikeInterface display, Body b) {
        def.getUseScript().run(display, this, b);
    }

    @Override
    public String takeAttack(Attack A) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
