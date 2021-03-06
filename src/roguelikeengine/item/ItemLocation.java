package roguelikeengine.item;

import roguelikeengine.area.LocalArea;
import roguelikeengine.area.Location;
import roguelikeengine.area.NonexistentLocationException;
import roguelikeengine.area.TerrainDefinition;
import roguelikeengine.display.DisplayChar;
import roguelikeengine.largeobjects.Body;

/**
 *
 * @author greg
 */
public class ItemLocation extends Location {
    private Item container;
    
    public ItemLocation(Item container) {
        this.container = container;
    }

    @Override
    public boolean equals(Location l) {
        if (l instanceof ItemLocation)
            return true;
        return false;
    }

    @Override
    protected boolean refactor() {return true;}
    
    public Item getContainer() {
        return container;
    }

    @Override
    public int getX() {
        return container.getLocation().getX();
    }

    @Override
    public int getY() {
        return container.getLocation().getY();
    }

    @Override
    public LocalArea getArea() {
        return container.getLocation().getArea();
    }

    @Override
    public TerrainDefinition getTerrain() {
        return container.getLocation().getTerrain();
    }

    @Override
    public boolean isPassable() {
        return container.getLocation().isPassable();
    }

    @Override
    public boolean isTransparent() {
        return container.getLocation().isTransparent();
    }

    @Override
    public DisplayChar getSymbol() throws NonexistentLocationException {
        return container.symbol;}

    @Override
    public Body bodyAt() {
        return container.getLocation().bodyAt();
    }

    @Override
    public String getString() {
        return container.getName();
    }

}
