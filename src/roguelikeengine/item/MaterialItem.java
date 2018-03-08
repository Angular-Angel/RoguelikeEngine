package roguelikeengine.item;

import roguelikeengine.display.RoguelikeInterface;
import roguelikeengine.display.DisplayChar;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import stat.Stat;
import roguelikeengine.largeobjects.Attack;
import roguelikeengine.largeobjects.Creature;
import stat.NoSuchStatException;

/**
 *
 * @author Greg
 */
public class MaterialItem extends Item {
    private ItemDefinition itemDef;
    private MaterialDefinition material;
    
    public MaterialItem(MaterialDefinition m, ItemDefinition itemDef) {
        super();
        this.itemDef = itemDef;
        material = m;
        stats.addAllStats(material.stats.viewStats());
        stats.addAllStats(itemDef.stats.viewStats());
        addAttacks(itemDef.getAttacks());
        refactor();
    }

    /**
     * @return the itemDef
     */
    public ItemDefinition getItemDef() {
        return itemDef;
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        for (ItemMod i : mods) {
            if (!sb.toString().contains(i.getAdjective()))sb.append(i.getAdjective() + " ");
        }
        if (getItemDef().defaultMaterial() != getMaterial()) {
            sb.append(getMaterial().getName() + " " + getItemDef().getName(0));
        } else {sb.append(getItemDef().getName(0));}
        return sb.toString();
    }

    @Override
    public DisplayChar getSymbol() {
        return new DisplayChar(getItemDef().symbol.getSymbol(), 
                getMaterial().getColor());

    }

    /**
     * @return the material
     */
    public MaterialDefinition getMaterial() {
        return material;
    }

    @Override
    public boolean takeAttack(Attack a){
        material.getDamageScript().run(a, this);
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

    @Override
    public boolean containsPart(String s) {
        if (getName().contains(s)) return true;
        return false;
    }

    @Override
    public void use(RoguelikeInterface display, Creature b) {
        itemDef.getUseScript().run(display, this, b);
    }
}
