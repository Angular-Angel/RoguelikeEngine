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
public class MenuWindow extends Window {
    
    private HashMap<Character, MenuOption> options;
    
    public MenuWindow(RoguelikeInterface roguelikeInterface, int width, int height) {
        super(roguelikeInterface, width, height);
        options = new HashMap <>();
    }
    
    public void addMenuOption(MenuOption menuOption) {
        options.put(menuOption.key, menuOption);
        int y = options.size();
        setDisplay(new DisplayChar(menuOption.key, Color.white), 1, 1 + y);
        drawString(3, 1 + y, menuOption.option, 
                            Color.white);
    }
    
    public void loop() {
        boolean done = false;
        while (!done) {
            roguelikeInterface.repaint();
            char c = roguelikeInterface.getKey();
            if (c == 'q') done = true;
            MenuOption option = options.get(c);
            if (option != null) {
                option.select();
            }
        }
    }
    
}
