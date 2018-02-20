/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.display;

import java.awt.Color;

/**
 *
 * @author angle
 */
public class Window {
    protected RoguelikeInterface roguelikeInterface;
    private int x, y, width, height;

    public Window(RoguelikeInterface roguelikeInterface, int width, int height) {
        this(roguelikeInterface, (roguelikeInterface.getDisplayXDist() - width)/2, (roguelikeInterface.getDisplayYDist() - height)/2, width, height);
    }

    public Window(RoguelikeInterface roguelikeInterface, int x, int y, int width, int height) {
        this(roguelikeInterface, x, y, width, height, true);
    }

    public Window(RoguelikeInterface roguelikeInterface, int x, int y, int width, int height, boolean border) {
        this.roguelikeInterface = roguelikeInterface;
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        DisplayChar blank = new DisplayChar(' ', Color.black);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                setDisplay(blank, i, j);
            }
        }
        DisplayChar horizontalBorder = new DisplayChar('─', Color.WHITE);
        DisplayChar verticalBorder = new DisplayChar('│', Color.WHITE);
        for (int i = 1; i < width; i++) {
            setDisplay(horizontalBorder, i, 0);
            setDisplay(horizontalBorder, i, height - 1);
        }
        for (int i = 1; i < height; i++) {
            setDisplay(verticalBorder, 0, i);
            setDisplay(verticalBorder, width - 1, i);
        }
        setDisplay(new DisplayChar('┌', Color.WHITE), 0, 0);
        setDisplay(new DisplayChar('└', Color.WHITE), 0, height - 1);
        setDisplay(new DisplayChar('┐', Color.WHITE), width - 1, 0);
        setDisplay(new DisplayChar('┘', Color.WHITE), width - 1, height - 1);
        roguelikeInterface.repaint();
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    public void setDisplay(DisplayChar d, int x, int y) {
        roguelikeInterface.setDisplay(d, getX() + x, getY() + y);
    }

    public void drawString(int x, int y, String string, Color c) {
        roguelikeInterface.writeString(x + getX(), y + getY(), string, c);
    }

}
