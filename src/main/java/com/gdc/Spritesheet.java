package com.gdc;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Spritesheet {

    /**
     * ArrayList of all sprite frames
     */
    private final ArrayList<BufferedImage> sprites;

    public Spritesheet(ArrayList<BufferedImage> sprites){
        this.sprites = sprites;
    }

    public int size(){
        return sprites.size();
    }

    /**
     * Get graphics for specified sprite frame
     * @param id
     * @return Sprite in ImageIcon format
     */
    public ImageIcon getSprite(int id){
        return new ImageIcon(sprites.get(id));
    }
}
