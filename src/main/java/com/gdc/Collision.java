package com.gdc;

import java.util.ArrayList;

public class Collision {

    Sprite sprite;
    int sprite_x, sprite_y, sprite_width, sprite_height;
    int scr_width = Dodgeball.SCREEN_WIDTH - 14;
    //Subtract for title bar height
    int scr_height = Dodgeball.SCREEN_HEIGHT - 36;

    /**
     * Initialize new collision object
     * @param sprite sprite to initialize collision check on
     */
    public Collision(Sprite sprite) {
        this.sprite = sprite;
        sprite_x = sprite.getX_pos();
        sprite_width = sprite.getWidth();
        sprite_y = sprite.getY_pos();
        sprite_height = sprite.getHeight();
    }

    /**
     * Check if sprite is able to move in specified direction
     * @param direction direction to check if able to move
     * @return boolean
     */
    public boolean canMove(String direction){
        int spriteTopBound = 40;
        int spriteBottomBound = scr_height;
        int spriteLeftBound = 0;
        int spriteRightBound = scr_width;

        if (sprite instanceof Player)
            spriteTopBound = Dodgeball.SCREEN_MIDPOINT;

        if (sprite instanceof Enemy)
            spriteBottomBound = Dodgeball.SCREEN_MIDPOINT;

        return switch (direction) {
            case "UP" -> sprite_y - sprite.change_y > spriteTopBound;
            case "DOWN" -> (sprite_y + sprite_height + sprite.change_y) < spriteBottomBound;
            case "LEFT" -> sprite_x - sprite.change_x > spriteLeftBound;
            case "RIGHT" -> (sprite_x + sprite_width + sprite.change_x) < spriteRightBound;
            default -> true;
        };
    }

    /**
     * Get if sprite is at its max or min possible X
     * @return boolean
     */
    public boolean atXBoundry(){
        return ! (canMove("UP") && canMove("DOWN"));
    }

    /**
     * Get if sprite is at its max or min possible Y
     * @return boolean
     */
    public boolean atYBoundry(){
        return ! (canMove("LEFT") && canMove("RIGHT"));
    }

    /**
     * Check if two sprites are colliding - static
     * @param a First sprite
     * @param b Second sprite
     * @return boolean
     */
    public static boolean isCollidingWithOther(Sprite a, Sprite b){
        return a.getBounds().intersects(b.getBounds());
    }

    /**
     * Check if two sprites are colliding - static
     * @param a Sprite one
     * @param b List of sprites to check
     * @return boolean
     */
    public static boolean isCollidingWithOther(Sprite a, ArrayList<Sprite> b){
        for(Sprite s: b){
            if (a.getBounds().intersects(s.getBounds()) && s != a)
                return true;
        }
        return false;
    }

    /**
     * Get sprite from list that is colliding with other sprite
     * @param a Sprite one
     * @param b List of sprites to check
     * @return Colliding sprite
     */
    public static Sprite getCollidedWith(Sprite a, ArrayList<Sprite> b) {
        for(Sprite s: b){
            if (isCollidingWithOther(a, s))
                return s;
        }
        return null;
    }



}
