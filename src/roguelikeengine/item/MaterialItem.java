package roguelikeengine.item;

import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.largeobjects.Attack;

/**
 *
 * @author Greg
 */
public class MaterialItem extends Item {
    private MaterialDefinition material;
    
    public MaterialItem(MaterialDefinition m, ItemDefinition itemDef) {
        super(itemDef);
        material = m;
        stats.addAllStats(material.stats.viewStats());
        refactor();
    }

    @Override
    public String getName() {
        StringBuilder sb = new StringBuilder();
        for (ItemMod i : mods) {
            if (!sb.toString().contains(i.getAdjective()))sb.append(i.getAdjective() + " ");
        }
        if (itemDef.defaultMaterial() != getMaterial()) {
            sb.append(getMaterial().getName() + " " + itemDef.getName(0));
        } else {sb.append(itemDef.getName(0));}
        return sb.toString();
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
}
