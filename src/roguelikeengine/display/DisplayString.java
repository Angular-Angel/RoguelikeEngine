/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.display;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author angle
 */
public class DisplayString extends WindowElement {
    public final ArrayList<DisplayChar> displayChars;
    public int x, y;
    
    public DisplayString(int x, int y) {
        this.x = x;
        this.y = y;
        displayChars = new ArrayList<>();
    }
    
    public DisplayString(int x, int y, DisplayChar... displayChars) {
        this(x, y);
        this.displayChars.addAll(Arrays.asList(displayChars));
    }
    
    public DisplayString(int x, int y, String string) {
        this(x, y, string, Color.white);
    }
    
    public DisplayString(int x, int y, String string, Color color) {
        this(x, y);
        append(string, color);
    }
    
    public void setColor(Color color) {
        displayChars.forEach(((DisplayChar displayChar) -> displayChar.setColor(color)));
    }
    
    public void append(String string) {
        append(string, Color.white);
    }
    
    public void append(String string, Color color) {
        for (char c : string.toCharArray()) {
            this.displayChars.add(new DisplayChar(c, color));
        }
    }

    @Override
    public void draw(Window window) {
        for (int i = 0; i < displayChars.size(); i++) {
            window.setDisplay(displayChars.get(i), x + i, y);
        }
    }
}