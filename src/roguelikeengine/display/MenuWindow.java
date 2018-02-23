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
    
    protected HashMap<Character, MenuOption> options;
    private boolean done;
    
    public MenuWindow(RoguelikeInterface roguelikeInterface, int width, int height) {
        super(roguelikeInterface, width, height);
        options = new HashMap <>();
    }
    
    public MenuWindow(RoguelikeInterface roguelikeInterface, String title,  int width, int height) {
        super(roguelikeInterface, title, width, height);
        options = new HashMap <>();
    }
    
    public void addMenuOption(MenuOption menuOption) {
        options.put(menuOption.key, menuOption);
        int y = options.size();
        setDisplay(new DisplayChar(menuOption.key, Color.white), 1, 1 + y);
        drawString(3, 1 + y, menuOption.option, Color.white);
    }
    
    public void drawOptions() {
        int i = 0;
        for (MenuOption option : options.values()) {
            i++;
            setDisplay(new DisplayChar(option.key, Color.white), 1, 1 + i);
            drawString(3, 1 + i, option.option, Color.white);
        }
    }
    
    public void loop() {
        done = false;
        while (!done) {
            drawWindow();
            drawOptions();
            char c = roguelikeInterface.getKeyChar();
            MenuOption option = options.get(c);
            if (option != null) {
                option.select();
            } else done = true;
        }
    }
    
    public void end() {
        done = true;
    }
    
}
