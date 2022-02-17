import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Sprite {
    protected Image image;

    protected int width, height;
    protected int x_pos, y_pos;
    protected double change_x, change_y;
    protected double speed;
    protected boolean visible = true;
    protected String facing = "NORTH";
    protected String angled = "NORTH";

    public Sprite(String path, double scale, int x, int y, double speed){
        display(path, scale);
        x_pos = x;
        y_pos = y;
        this.speed = speed;
    }

    public Sprite(String path, double scale, double speed){
        this(path, scale, 0, 0, 1);
    }

    private void display(String path, double scale){
        if (! new File(path).exists()){
            JOptionPane.showMessageDialog(null, "An asset does not exist");
        }
        else{
            ImageIcon icon = new ImageIcon(path);
            this.image = icon.getImage();
            width = (int) (image.getWidth(null) * scale);
            height = (int) (image.getHeight(null) * scale);
        }
    }

    public void move(){
        Collision collisions = new Collision(this);
        if (collisions.canMove("LEFT") && change_x < 0)
            x_pos += change_x * speed;
        if (collisions.canMove("RIGHT") && change_x >= 0)
            x_pos += change_x * speed;
        if (collisions.canMove("TOP") && change_y < 0)
            y_pos += change_y * speed;
        if (collisions.canMove("BOTTOM") && change_y >= 0)
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

    public Image getImage(){
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }

    public boolean isVisible() {
        return visible;
    }
}
