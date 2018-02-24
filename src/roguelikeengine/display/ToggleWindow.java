/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.display;

import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author angle
 */
public class ToggleWindow extends MenuWindow {
    
    private HashMap<Character, Boolean> selected;
    
    public ToggleWindow(RoguelikeInterface roguelikeInterface, String title, int width, int height) {
        super(roguelikeInterface, title, width, height);
        selected = new HashMap<>();
    }
    
    @Override
    public void addMenuOption(MenuOption menuOption) {
        super.addMenuOption(menuOption);
        int y = options.size();
        selected.put(menuOption.key, false);
        setDisplay(new DisplayChar('-', Color.white), 3, 1 + y);
        drawString(5, 1 + y, menuOption.option, Color.white);
    }
    
    @Override
    public void removeMenuOption(MenuOption menuOption) {
        selected.remove(menuOption.key);
        super.removeMenuOption(menuOption);
    }
    
    @Override
    public void drawOptions() {
        int i = 0;
        for (MenuOption option : options.values()) {
            i++;
            setDisplay(new DisplayChar(option.key, Color.white), 1, 1 + i);
            if (selected.get(option.key))
                setDisplay(new DisplayChar('+', Color.white), 3, 1 + i);
            else
                setDisplay(new DisplayChar('-', Color.white), 3, 1 + i);
            drawString(5, 1 + i, option.option, Color.white);
        }
    }
    
    @Override
    public void loop() {
        done = false;
        while (!done) {
            drawWindow();
            drawOptions();
            char c = roguelikeInterface.getKeyChar();
            MenuOption option = options.get(c);
            if (option != null) {
                selected.replace(c, !selected.get(c));
            } else done = true;
        }
        for (MenuOption option : options.values()) {
            if (selected.get(option.key)) option.select();
        }
    }
}
