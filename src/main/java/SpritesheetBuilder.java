import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SpritesheetBuilder {

    private int[][][] coords;

    private BufferedImage spritesheet;

    public SpritesheetBuilder setFile(String file){
        File f = new File(file);
        try {
            spritesheet = ImageIO.read(f);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An asset does not exist");
        }
        return this;
    }

    public SpritesheetBuilder setCoords(int[][][] coords){
        this.coords = coords;
        return this;
    }

    public Spritesheet build(){
        return build(false);
    }

    public Spritesheet build(boolean isSingleSprite){
        int[][][] coords = this.coords;
        BufferedImage spritesheet = this.spritesheet;

        ArrayList<BufferedImage> spritesList = new ArrayList<>();
        if(isSingleSprite){
            spritesList.add(spritesheet);
            return new Spritesheet(spritesList);
        }

        for (int[][] spriteCoords : coords) {
            int x = spriteCoords[0][0];
            int y = spriteCoords[0][1];
            int width = spriteCoords[1][0] - x + 2;
            int height = spriteCoords[1][1] - y + 2;

            spritesList.add(spritesheet.getSubimage(x, y, width, height));
        }

        return new Spritesheet(spritesList);
    }


}
