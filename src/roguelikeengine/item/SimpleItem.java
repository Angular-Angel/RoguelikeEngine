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
import roguelikeengine.largeobjects.Creature;

/**
 *
 * @author Greg
 */
public class SimpleItem extends Item {

    ItemDefinition def;

    public SimpleItem(ItemDefinition def) {
        super(def);
        this.def = def;
        addAttacks(def.getAttacks());
    }
    
    @Override
    public boolean containsPart(String s) {
        return false;
    }

    @Override
    public String getName() {
        String name = "";
        for (ItemMod i : mods) {
            if (!name.contains(i.getAdjective())) name += (i.getAdjective() + " ");
        }
        name += def.getName(0);
        return name;
    }

    @Override
    public void use(RoguelikeInterface display, Creature b) {
        def.getUseScript().run(display, this, b);
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
