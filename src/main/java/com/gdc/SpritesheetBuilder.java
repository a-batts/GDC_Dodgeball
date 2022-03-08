package com.gdc;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class SpritesheetBuilder {

    private int[][][] coords;

    private BufferedImage spritesheet;

    /**
     * Set file to pick sprite frames from
     * @param file Path of file
     * @return this
     */
    public SpritesheetBuilder setFile(String file) {
        File f = new File(file);
        try {
            spritesheet = ImageIO.read(f);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An asset does not exist");
        }
        return this;
    }

    /**
     * Set coordinates of sprite frame bounds
     * @param coords three dimensional array of bounds
     * @return this
     */
    public SpritesheetBuilder setCoords(int[][][] coords) {
        this.coords = coords;
        return this;
    }

    public Spritesheet build() {
        return build(false);
    }

    /**
     * Build frames from specified coordinates and add sliced frames to ArrayList
     * @param isSingleSprite If the chosen spritesheet only contains a single frame
     * @return Spritesheet holding created arraylist
     */
    public Spritesheet build(boolean isSingleSprite) {
        int[][][] coords = this.coords;
        BufferedImage spritesheet = this.spritesheet;

        ArrayList<BufferedImage> spritesList = new ArrayList<>();
        if (isSingleSprite) {
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
