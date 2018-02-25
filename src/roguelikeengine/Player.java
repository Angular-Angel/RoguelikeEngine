package roguelikeengine;

import roguelikeengine.display.Rotation;
import roguelikeengine.display.Window;
import roguelikeengine.display.DisplayChar;
import stat.NoSuchStatException;
import roguelikeengine.controller.Controller;
import roguelikeengine.item.Item;
import roguelikeengine.item.ItemOnGround;
import roguelikeengine.area.LocalArea;
import roguelikeengine.area.Location;
import roguelikeengine.area.LocationLine;
import roguelikeengine.area.AreaLocation;
import roguelikeengine.area.NonexistentLocationException;
import roguelikeengine.area.Direction;
import roguelikeengine.controller.PlayerWantsToQuitException;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import roguelikeengine.display.MenuOption;
import roguelikeengine.display.MenuWindow;
import roguelikeengine.display.ToggleWindow;
import roguelikeengine.largeobjects.*;

/**
 * This class is for the object that manages the interface between the player 
 * and the character that they control.
 * @author greg
 */
public class Player extends Controller {
    
    private final Game game;
    private Rotation rot;
    
    public Player(Body body, Game game) {
        super(body);
        
        rot = Rotation.degree0;
        this.game = game;
    }
    
    /**
     * writes what the character can see to display.
     */
    public void view() {
        try {
            //this variable is used  lot, so let's copy it to a local.
            int sightRange = (int) getBody().getDef().stats.getScore("Sight Range");
            //these too.
            int x = getBody().getLocation().getX();
            int y = getBody().getLocation().getY();
            //clear the display so that we don't have any left over bits.
            game.display.setAll(new DisplayChar(' ', Color.black));
            //send a line in each direction to see what we can see, then write to the
            //display.
            for (int i = 0; i <= sightRange*2; i++) {
                visionLine(new LocationLine(getBody().getLocation(), 
                        x + sightRange, y - sightRange + i, true, false));
                visionLine(new LocationLine(getBody().getLocation(), 
                        x - sightRange, y - sightRange + i, true, false));
                visionLine(new LocationLine(getBody().getLocation(), 
                        x - sightRange + i, y + sightRange, true, false));
                visionLine(new LocationLine(getBody().getLocation(), 
                        x - sightRange + i, y - sightRange, true, false));
            }
            //rotate and mirror according to the player, so as to not cause sudden 
            //reorientation when the player crosses a border.
            game.display.rotate(getRotation());
            game.display.repaint();
        } catch (NoSuchStatException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This function goes through the line and write the symbol for each 
     * location to the display. Will stop if t comes to an page 
     * @param line The line to go through.
     */
    public void visionLine(LocationLine line) {
        //save the coordinates for the center of the display, 
        //cause we'll use them alot
        int x = game.display.getDisplayXDist()/2;
        int y = game.display.getDisplayYDist()/2;
        //now check over the line and add the symbol 
        //for each point to the display.
        for (int i = 0; i < line.getLength(); i++) {
            try {
                //set the symbol at the location to the display. if the location 
                //doesn't actually exist, an exception is thrown.
                game.display.setDisplay(line.getLocation(i).getSymbol(), 
                                   x + line.getX(i), y + line.getY(i));
            } catch (NonexistentLocationException ex) {
                System.out.println(ex.getMessage());
                return;
            }
        }
    }
    
    /**
     * handles the input from the display.
     * @throws roguelikeengine.controller.PlayerWantsToQuitException
     */
    public void handleInput() throws PlayerWantsToQuitException {
        //get the character the player entered.
        char c = game.display.getKeyChar();
        //set the default direction, so we can easily tell whether or not it's 
        //been changed.
        Direction dir = null;
        //depending on what the key entered was, set dir accordingly.
        switch (c) {
            case '7': dir = Direction.NORTHWEST;
                break;
            case '8': dir = Direction.NORTH;
                break;
            case '9': dir = Direction.NORTHEAST;
                break;
            case '6': dir = Direction.EAST;
                break;
            case '3': dir = Direction.SOUTHEAST;
                break;
            case '2': dir = Direction.SOUTH;
                break;
            case '1': dir = Direction.SOUTHWEST;
                break;
            case '4': dir = Direction.WEST;
                break;
            case 'q': throw new PlayerWantsToQuitException();
            case 'g': pickUpItems();
                break;
            case 'i': viewInventory();
                break;
            case 's': //saySpell();
                break;
            case '@': 
            default: break;
        }
        //adjust dir for mirroring and rotation.
        if (dir != null) {
            
            dir = dir.rotate(getRotation());
            //find the location to move the player to.
            AreaLocation l = new AreaLocation(getBody().getLocation());
            l.move(dir.dx, dir.dy);
            //move the player, and adjust things if they cross a border.
            if (l.bodyAt() != null) {
                bodyInteraction(l.bodyAt());
            } else if (getBody().moveTo(l) && l.getTerrain() == null) {
                LocalArea.BorderArea b = l.getArea().getBorderArea(l.getX(), l.getY());
                addRotation(l.getArea().getBorderArea(l.getX(), l.getY()).getRotation());
            }
        }
        
    }
    
    public void bodyInteraction(Body body) {
        MenuWindow menu = new MenuWindow(game.display, 40, 20);
        menu.addMenuOption(new MenuOption('a', "Attack") {
            @Override
            public void select() {
                attackMenu(body);
                menu.end();
            }
        });
        menu.loop();
    }
    
    public void attackMenu(Body body) {
        MenuWindow menu = new MenuWindow(game.display, "Attack!", 40, 20);
        ArrayList<Attack> attacks = getBody().getAttacks();
        
        for (int i = 0; i < attacks.size(); i++) {
            Attack attack = attacks.get(i);
            menu.addMenuOption(new MenuOption((char)(97 + i), attack.name) {
                @Override
                public void select() {
                    getBody().attack(body, attack);
                    menu.end();
                }
            });
        }
        
        menu.loop();
        
    }
    
    public void pickUpItems() {
        Location l = getBody().getLocation();
        ArrayList<ItemOnGround> items = l.getArea().itemsAt(l.getX(), l.getY());
        ToggleWindow menu = new ToggleWindow(game.display, "Items", 20, 10);
        for (int i = 0; i < items.size(); i++) {
            ItemOnGround itemOnGround = items.get(i);
            Item item = itemOnGround.getItem();
            menu.addMenuOption(new MenuOption((char)(97 + i), item.getName()) {
                @Override
                public void select() {
                    getBody().addItem(item);
                    l.getArea().removeEntity(itemOnGround);
                }
            });
        }
        
        menu.loop();
    }
    
    public void viewInventory() {
        MenuWindow menu = new MenuWindow(game.display, "Inventory", 40, 20);
        
        ArrayList<Item> inventory = getBody().getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            menu.addMenuOption(new MenuOption((char)(97 + i), item.getName()) {
                @Override
                public void select() {
                    viewItem(item);
                    if (!inventory.contains(item))
                        menu.removeMenuOption(this);
                }
            });
        }
        
        menu.loop();
    }
    
    public void wield(Item i) {
        getBody().setWeapon(i);
    }
    
    public void viewItem(Item i) {
        MenuWindow menu = new MenuWindow(game.display, i.getName(), 20, 10);
        Body body = getBody();
        menu.addMenuOption(new MenuOption('d', "Drop") {
            @Override
            public void select() {
                body.removeItem(i);
                body.getLocation().getArea().addEntity(new ItemOnGround(i, new AreaLocation(body.getLocation())));
                menu.end();
            }
        });
        menu.addMenuOption(new MenuOption('w', "Wield") {
            @Override
            public void select() {
                wield(i);
                menu.end();
            }
        });
        menu.loop();
    }
    
    public void viewStatus() {
        Window win = new Window(game.display, 20, 8);
    }
    
    /*public void saySpell() {
        String string[] = game.display.getSentence("Say something!").split(" ", 2);
        if (game.registry.materials.containsKey(string[0]) && 
            game.registry.items.containsKey(string[1]))
        getBody().addItem(new SimpleItem(game.registry.materials.get(string[0]), 
                game.registry.items.get(string[1])));
    }*/

    /**
     * @return the rotation
     */
    public Rotation getRotation() {
        return rot;
    }

    /**
     * @param rot the rotation to set
     */
    public void setRotation(Rotation rot) {
        this.rot = rot;
    }
    
    /**
     * rotate the player view by r
     * @param r 
     */
    public void addRotation(Rotation r) {
        setRotation(Rotation.add(getRotation(), r));
    
    }
     
    /**
     * inherited from controller. used to determine whether this controller 
     * is a player or not.
     * @return true;
     */
    public boolean isPlayer() {
        return true;
    }
    
    @Override
    public boolean act() throws PlayerWantsToQuitException{
        if (!getBody().isAlive()) 
            return false;
        getBody().step();
        addMoves();
        for (int i = 0; getBody().getMoves() > 0 && i < 100; i++) {
            view();
                //handle the input, and if the player wants to quit, quit.
            handleInput();
        }
        return true;
    }

    @Override
    public void addMoves() {
        getBody().addMoves();
    }
}
