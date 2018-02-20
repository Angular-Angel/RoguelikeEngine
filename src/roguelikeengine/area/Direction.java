/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package roguelikeengine.area;

import roguelikeengine.display.Rotation;

/**
 *
 * @author angle
 */

public enum Direction {

    // use magic numbers to set the ordinal (used for rotation),
    // and the dx and dy of each direction.
    NORTHWEST(0, -1, -1),
    NORTH(1, 0, -1),
    NORTHEAST(2, 1, -1),
    EAST(3, 1, 0),
    SOUTHEAST(4, 1, 1),
    SOUTH(5, 0, 1),
    SOUTHWEST(6, -1, 1),
    WEST(7, -1, 0);

    private final int r90index, r180index, r270index;
    private final boolean horizontal, vertical;
    public final int ordinal, dx, dy;

    private Direction(int ordinal, int dx, int dy) {
        // from the ordinal, dx, and dy, we can calculate all the other constants.
        this.ordinal = ordinal;
        this.dx = dx;
        this.dy = dy;
        this.horizontal = dx != 0;
        this.vertical = !horizontal;
        this.r90index  = (ordinal + 2) % 8; 
        this.r180index = (ordinal + 4) % 8; 
        this.r270index = (ordinal + 6) % 8; 
    }


    // Rotate 90 degrees clockwise
    public Direction rotate90() {
        return values()[r90index];
    }

    // Rotate 180 degrees
    public Direction rotate180() {
        return values()[r180index];
    }

    // Rotate 270 degrees clockwise (90 counterclockwise)
    public Direction rotate270() {
        return values()[r270index];
    }
    
    public Direction rotate(Rotation rotation) {
        return values()[(ordinal + rotation.ordinal) % 8]; 
    }

    public Boolean isHorizontal() {
        return horizontal;
    }

    public Boolean isVertical() {
        return vertical;
    }
    
    public int dx(int steps) {
        return dx * steps;
    }

    public int dy(int steps) {
        return dy * steps;
    }

    public int forwards_x(int n) {
        return n + dx;
    }

    public int forwards_y(int n) {
        return n + dy;
    }

    public int backwards_x(int n) {
        return n - dx;
    }

    public int backwards_y(int n) {
        return n - dy;
    }

    public int left_x(int n) {
        // if we are E/W facing, our left/right 'x' co-ords are still 'x'
        // if we are N/S facing, our left/right 'x' is `-dy`
        return n - dy;
    }

    public int left_y(int n) {
        // see left_x comments for the idea
        return n + dx;
    }

    public int right_x(int n) {
        // see left_x comments for the idea
        return n + dy;
    }

    public int right_y(int n) {
        // see left_x comments for the idea
        return n - dx;
    }
}