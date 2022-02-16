import javax.swing.*;
import java.awt.Image;
import java.io.File;

public class Sprite {
    private Image image;
    private int width, height;
    private int x_pos, y_pos;
    protected double change_x, change_y;
    private double speed;

    public Sprite(String path, double scale, int x, int y, double speed){
        display(path, scale);
        x_pos = x;
        y_pos = y;
        this.speed = speed;
    }

    public Sprite(String path, double scale){
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
        x_pos += change_x * speed;
        y_pos += change_y * speed;
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
}
