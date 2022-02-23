package com.gdc;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {
    protected Image currentSprite;
    protected Spritesheet sprites;

    protected int width, height;
    protected int x_pos, y_pos;
    protected double change_x, change_y;
    protected double speed;
    protected double scale;
    protected boolean visible = true;
    protected String facing = "NORTH";
    protected String angled = "NORTH";

    public Sprite(double scale, int x, int y, double speed){
        this.scale = scale;
        x_pos = x;
        y_pos = y;
        this.speed = speed;
    }

    public Sprite(double scale, double speed){
        this(scale, 0, 0, speed);
    }

    public void buildSprites(SpritesheetBuilder builder){
        sprites = builder.build();
        setCurrentSprite(0);
    }

    public void buildSprites(SpritesheetBuilder builder, boolean isSingleSprite){
        sprites = builder.build(isSingleSprite);
        setCurrentSprite(0);
    }

    public void setCurrentSprite(int id){
        currentSprite = sprites.getSprite(id).getImage();
        width = (int) (currentSprite.getWidth(null) * scale);
        height = (int) (currentSprite.getHeight(null) * scale);
    }

    public void move(){
        Collision collisions = new Collision(this);
        if (collisions.canMove("LEFT") && change_x < 0)
            x_pos += change_x * speed;
        if (collisions.canMove("RIGHT") && change_x >= 0)
            x_pos += change_x * speed;
        if (collisions.canMove("UP") && change_y < 0)
            y_pos += change_y * speed;
        if (collisions.canMove("DOWN") && change_y >= 0)
            y_pos += change_y * speed;
    }

    protected void setPosition(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public int getX_pos(){
        return x_pos;
    }

    public int getY_pos(){
        return y_pos;
    }
    
    public int getWidth(){
        return width;
    }
    
    public int getHeight(){
        return height;
    }

    public Image currentSprite(){
        return currentSprite;
    }

    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }

    public boolean isVisible() {
        return visible;
    }

    public int calculateDistance(Sprite b){
        return (int) Math.sqrt((b.getX_pos() - getX_pos())^2 + (b.getY_pos() - getY_pos())^2);
    }
}
