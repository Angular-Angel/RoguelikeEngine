/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.display;

/**
 *
 * @author angle
 */
public abstract class MenuOption {
    public final char key;
    public final String option;
    
    public MenuOption(char key, String option) {
        this.key = key;
        this.option = option;
    }
    
    public abstract void select();
}
