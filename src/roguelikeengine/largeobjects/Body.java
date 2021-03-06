package roguelikeengine.largeobjects;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.Location;
import roguelikeengine.controller.Controller;
import roguelikeengine.controller.DijkstraMap;
import roguelikeengine.item.Item;
import stat.NoSuchStatException;
import stat.NumericStat;

/**
 *
 * @author greg
 */
public class Body implements Entity {
    private String name;
    private Controller controller;
    private BodyDefinition def;
    private ArrayList<Item> inventory;
    private DijkstraMap map;
    private int moves;
    private ArrayList<StatusEffect> effects;
    private HashMap<Item, Item> equipment;
    private Item weapon;
    private Item body;
    
    public Body(AreaLocation location, BodyDefinition bodyDef) {
        this("Null Body", location, bodyDef);
    }

    public Body(String name, AreaLocation location, BodyDefinition bodyDef) {
        this.def = bodyDef;
        this.name = name;
        body = def.bodyTemplate.generateItem();
        body.stats.addAllStats(bodyDef.stats.viewStats());
        inventory = new ArrayList<>();
        effects = new ArrayList<>();
        moves = 0;
        weapon = null;
        
        body.stats.addStat("HP", new NumericStat(getBody().stats.getScore("Max HP")));
        
        body.refactor();
        
        setLocation(location);
    }
    
    /**
     * @return the location
     */
    @Override
    public AreaLocation getLocation() {
        return (AreaLocation) getBody().getLocation();
    }
    
    /**
     * 
     * @param l The location to check.
     * @return true if this body occupies that location, false otherwise.
     */
    @Override
    public boolean occupies(Location l) {
        return l.equals(getLocation());
    }
    
    public boolean isAlive() {
        return getDef().getBioScript().isAlive(this);
    }
    
    public void step() {
        getDef().getBioScript().step(this);
    }
    
    @Override
    public void setLocation(Location location) {
        if (location instanceof AreaLocation) {
            if (getLocation() != null && location.getArea() != getLocation().getArea()) {
                getLocation().getArea().removeEntity(this);
                location.getArea().addEntity(this);

            }
            getBody().setLocation((AreaLocation) location);
        }
    }

    /**
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    /**
     * @param controller the controller to set
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * @return the BodyDefinition
     */
    public BodyDefinition getDef() {
        return def;
    }
    
    /**
     * 
     * @param l The location to move to.
     * @return true if the body moved, false otherwise.
     */
    
    public boolean moveTo(AreaLocation l) {
        if (l.isPassable()) {
            setLocation(l);
            addMoves(-100);
            if (getMap() != null) {getMap().fill(getLocation());}
            return true;
        } return false;
    }
    
    public void addItem(Item i) {
        inventory.add(i);
    }
    
    public void removeItem(Item i) {
        inventory.remove(i);
    }

    /**
     * @return the inventory
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * @return the map
     */
    public DijkstraMap getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(DijkstraMap map) {
        this.map = map;
    }

    /**
     * @return the moves
     */
    public int getMoves() {
        return moves;
    }

    /**
     * @param moves the moves to add
     */
    public void addMoves(int moves) {
        this.moves += moves;
    }

    public void addMoves() {
        try {
            if (getBody().stats.getScore("Speed") > 0) {
                this.moves += getBody().stats.getScore("Speed");
            }
        } catch (NoSuchStatException e) {
            this.moves += 100;
        }
    }
    
    
    public void addStatusEffect(StatusEffect effect) {
        effects.add(effect);
    }
    
    public void attack(Body body, Attack attack) {
        addMoves(-100);
        body.beAttacked(attack);
    }
    
    @Override
    public void beAttacked(Attack a) {
        //code for dodging goes here.
        //code for blocking goes here.
        //code for determining hit location goes here.
        
        //take damage
        def.getBioScript().beAttacked(this, a);
        
        if (!isAlive()) die();
    }

    public String getName() {
        return name;
    }
    
    /**
     * @return the weapon
     */
    public Item getWeapon() {
        return weapon;
    }

    /**
     * @param weapon the weapon to set
     */
    public void setWeapon(Item weapon) {
        this.weapon = weapon;
    }
    
    public ArrayList<Attack> getAttacks() {
        ArrayList<Attack> attacks = getBody().getAttacks();
        if (weapon != null) {
            attacks.addAll(weapon.getAttacks());
        }
        return attacks;
    }

    /**
     * @return the body
     */
    public Item getBody() {
        return body;
    }

    private void die() {
        body.symbol.setBackground(Color.RED);
        
        AreaLocation location = getLocation();
        location.getArea().addItem(body, location.getX(), location.getY());
        location.getArea().removeEntity(this);
    }
    
    public void equip(Item Item, Item bodyPart) {
        equipment.put(bodyPart, Item);
    }
    
}
