package roguelikeengine.item;

import roguelikeengine.largeobjects.Attack;

/**
 *
 * @author Greg
 */
public class MaterialItem extends Item {
    private Material material;
    
    public MaterialItem(Material m, ItemDefinition itemDef) {
        super(itemDef);
        material = m;
        stats.addAllStats(material.stats.viewStats());
        refactor();
    }

    @Override
    public String getName() {
        String ret = "";
        for (ItemMod i : mods) {
            if (!ret.contains(i.getAdjective())) ret += i.getAdjective() + " ";
        }
        if (itemDef.defaultMaterial() != getMaterial()) {
            ret += getMaterial().getName() + " " + itemDef.getName(0);
        } else {
            ret += itemDef.getName(0);
        }
        return ret;
    }

    /**
     * @return the material
     */
    public Material getMaterial() {
        return material;
    }

    @Override
    public boolean takeAttack(Attack a){
        material.getDamageScript().run(a, this);
        return (stats.getScore("HP") <= 0);
    }
}
