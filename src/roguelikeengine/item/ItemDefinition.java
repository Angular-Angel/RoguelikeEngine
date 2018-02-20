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
public class ItemDefinition{
    
    private DisplayChar symbol;
    private String[] name;
    private ArrayList<Attack> attacks;
    private MaterialDefinition defmat;
    private ItemScript useScript;
    public StatContainer stats;
    
    public ItemDefinition(DisplayChar d, String[] s) {
        this(d, s, null, new StatContainer(), null);
    }
    
    public ItemDefinition(DisplayChar d, String[] names, MaterialDefinition mat, 
            StatContainer stats, ItemScript use) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        this.symbol = d;
        this.name = names;
        defmat = mat;
        attacks = new ArrayList<>();
        useScript = use;
    }

    /**
     * @return the symbol
     */
    public DisplayChar getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(DisplayChar symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the Name
     */
    public String getName(int i) {
        if (i < 3)
        return name[i];
        else throw new IllegalArgumentException ();
    }

    /**
     * @param Name the Name to set
     */
    public void setName(String[] name) {
        this.name = name;
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
