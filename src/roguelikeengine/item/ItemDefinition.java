/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.item;

import java.util.ArrayList;
import stat.StatContainer;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.largeobjects.Attack;

/**
 *
 * @author Greg
 */
public class ItemDefinition {
    
    public final DisplayChar symbol;
    public final String[] name;
    public final ArrayList<Attack> attacks;
    public final MaterialDefinition defmat;
    public final ItemScript useScript;
    public final StatContainer stats;
    public final ItemDefinition[] components;
    
    public ItemDefinition(DisplayChar d, String[] s) {
        this(d, s, null, new StatContainer(), null);
    }
    
    public ItemDefinition(DisplayChar d, String[] names, MaterialDefinition mat, 
            StatContainer stats, ItemScript use) {
        this(d, names, mat, stats, use, null);
    }
    
    public ItemDefinition(DisplayChar d, String[] names, MaterialDefinition mat, 
            StatContainer stats, ItemScript use, ItemDefinition[] components) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        this.symbol = d;
        this.name = names;
        defmat = mat;
        attacks = new ArrayList<>();
        useScript = use;
        this.components = components;
    }


    /**
     * @return the Name
     */
    public String getName(int i) {
        if (i < 3)
        return name[i];
        else throw new IllegalArgumentException ();
    }
    
    public Item generateItem() {
        if (components != null) {
            return new CompositeItem(this);
        } else if(defmat != null)
            return new MaterialItem(defmat, this);
        else return new SimpleItem(this);
    }
    
    public void addAttack(Attack attack) {
        attacks.add(attack);
    }
    
    public void addAttacks(ArrayList<Attack> attacks) {
        this.attacks.addAll(attacks);
    }
    
    public Attack getAttack(String s) {
        for (Attack t : attacks){
            if (t.name.equals(s)) return t;
        }
        return null;
    }
    
    public ArrayList<Attack> getAttacks() {
        return (ArrayList<Attack>) attacks.clone();
    }

    /**
     * @return the nameMaterial
     */
    public MaterialDefinition defaultMaterial() {
        return defmat;
    }

    /**
     * @return the useScript
     */
    public ItemScript getUseScript() {
        return useScript;
    }
}
