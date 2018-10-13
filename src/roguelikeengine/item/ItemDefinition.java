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

    public final String[] name;
    public final DisplayChar symbol;
    public final ArrayList<Attack> attacks;
    public final ArrayList<EquipmentProfile> equipmentSlots;
    public final Material defmat;
    public final ItemUseScript useScript;
    public final StatContainer stats;
    public final ArrayList<ConnectionDefinition> connections;
    
    public ItemDefinition(DisplayChar d, String[] s) {
        this(d, s, null, new StatContainer(), null);
    }
    public ItemDefinition(DisplayChar d, String[] names, Material mat, 
            StatContainer stats, ItemUseScript use) {
        this.stats = new StatContainer();
        this.stats.addAllStats(stats);
        this.symbol = d;
        this.name = names;
        defmat = mat;
        attacks = new ArrayList<>();
        equipmentSlots = new ArrayList<>();
        connections = new ArrayList<>();
        useScript = use;
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
        return new Item(this);
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
    public Material defaultMaterial() {
        return defmat;
    }

    /**
     * @return the useScript
     */
    public ItemUseScript getUseScript() {
        return useScript;
    }
    
    public void addConnection(ItemDefinition itemDef, StatContainer stats) {
    	connections.add(new ConnectionDefinition(itemDef, stats));
    }
    
    public class ConnectionDefinition {

    	public ItemDefinition itemDefinition;
    	
    	public StatContainer stats;
    	
    	public ConnectionDefinition(ItemDefinition itemDefinition) {
    		this.itemDefinition = itemDefinition;
    		this.stats = new StatContainer();
    	}
    	
    	public ConnectionDefinition(ItemDefinition itemDefinition, StatContainer stats) {
    		this(itemDefinition);
    		this.stats.addAllStats(stats);
    	}
    	
    	public Connection generateConnection(Item item) {
    		return new Connection(item, itemDefinition.generateItem(), stats);
    	}
    }
}
