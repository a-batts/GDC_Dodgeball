package com.gdc;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Spritesheet {

    private final ArrayList<BufferedImage> sprites;

    public Spritesheet(ArrayList<BufferedImage> sprites){
        this.sprites = sprites;
    }

    public int size(){
        return sprites.size();
    }

    public ImageIcon getSprite(int id){
        return new ImageIcon(sprites.get(id));
    }
}
