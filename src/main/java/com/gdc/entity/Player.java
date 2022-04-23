package com.gdc.entity;

import com.gdc.Game;
import com.gdc.spritesheet.SpritesheetBuilder;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Random;

public class Player extends Sprite {

    private final int step;
    private int lives;
    private final Inventory<Ball> inventory = new Inventory<>();

    public static int INIT_LIVES = 3;

    public Player() {
        this(3);
    }

    public Player(int movementStep) {
        super(2.5, Game.SCREEN_WIDTH / 2, Game.SCREEN_HEIGHT - 100, 1.5);

        String dir = "src/main/resources/sprite/player/";
        File[] files = new File(dir).listFiles();

        Random rand = new Random();
        assert files != null;
        File file = files[rand.nextInt(files.length)];
        String path = dir + file.getName();

        int[][][] coords = {
                { { 4, 5 }, { 29, 23 } }, { { 35, 6 }, { 61, 23 } }, { { 67, 6 }, { 93, 23 } },
                { { 98, 7 }, { 125, 23 } },
                { { 2, 39 }, { 29, 55 } }, { { 34, 40 }, { 61, 55 } }, { { 65, 41 }, { 93, 55 } },
                { { 96, 42 }, { 125, 55 } },
                { { 0, 75 }, { 29, 87 } },
                { { 4, 101 }, { 29, 119 } }, { { 36, 101 }, { 61, 118 } }, { { 68, 101 }, { 92, 119 } },
                { { 100, 101 }, { 123, 119 } },
                { { 4, 133 }, { 26, 151 } }, { { 36, 134 }, { 59, 151 } }, { { 68, 134 }, { 93, 151 } },
                { { 100, 133 }, { 125, 151 } },
                { { 4, 165 }, { 29, 183 } }, { { 36, 165 }, { 61, 183 } },
                { { 4, 198 }, { 26, 215 } }, { { 35, 198 }, { 57, 215 } }, { { 68, 198 }, { 90, 215 } },
                { { 100, 197 }, { 121, 215 } },
                { { 10, 225 }, { 20, 249 } }, { { 42, 226 }, { 51, 250 } }, { { 74, 225 }, { 84, 249 } },
                { { 106, 226 }, { 116, 250 } },
                { { 6, 261 }, { 27, 279 } }, { { 37, 262 }, { 59, 279 } }, { { 70, 261 }, { 91, 279 } },
                { { 101, 262 }, { 121, 279 } },
                { { 7, 293 }, { 27, 311 } }, { { 39, 294 }, { 59, 311 } }, { { 71, 293 }, { 91, 311 } },
                { { 103, 295 }, { 123, 311 } },
                { { 6, 325 }, { 27, 343 } }, { { 37, 326 }, { 59, 343 } }, { { 70, 325 }, { 91, 343 } },
                { { 101, 326 }, { 123, 343 } },
                { { 10, 357 }, { 20, 377 } }, { { 42, 358 }, { 52, 378 } }, { { 74, 357 }, { 84, 377 } },
                { { 106, 358 }, { 116, 378 } },
                { { 2, 390 }, { 24, 407 } }, { { 34, 389 }, { 55, 407 } }, { { 66, 390 }, { 88, 407 } },
                { { 98, 389 }, { 119, 407 } },
                { { 4, 422 }, { 24, 439 } }, { { 36, 421 }, { 56, 439 } }, { { 68, 422 }, { 88, 439 } },
                { { 100, 421 }, { 120, 439 } },
                { { 4, 453 }, { 26, 471 } }, { { 36, 453 }, { 56, 471 } }, { { 69, 453 }, { 90, 471 } },
                { { 106, 453 }, { 116, 473 } },
                { { 10, 481 }, { 20, 505 } },
        };

        SpritesheetBuilder builder = new SpritesheetBuilder().setFile(path).setCoords(coords);
        buildSprites(builder);
        setCurrentSprite(16);

        this.step = movementStep;
        this.lives = INIT_LIVES;
    }

    public void grabBall(Ball ball) {
        if (ball.isVisible()) {
            inventory.take(ball);
            ball.visible = false;
        }
    }

    public void throwBall() {
        if (inventory.size() > 0) {
            Ball b = inventory.getFirst();

            int y_pos;
            int changeX;
            int changeY;
            if (facing.equals("SOUTH")) {
                changeY = b.getStep();
                y_pos = getBounds().y + getBounds().height;
            } else {
                changeY = -b.getStep();
                y_pos = getBounds().y - getBounds().height / 2 - 5;
            }
            switch (angled) {
                case "EAST" -> changeX = 2;
                case "WEST" -> changeX = -2;
                default -> changeX = 0;
            }
            b.setPosition(x_pos, y_pos);
            b.thrown(changeX, changeY);
            b.setThrownBy(this);
            b.visible = true;
            inventory.drop(b);
        }
    }

    public Inventory<Ball> getInventory() {
        return inventory;
    }

    public int getLives() {
        return lives;
    }

    public void removeLife() {
        if (lives > 0)
            lives--;
    }

    public void reset(){
        change_x = 0;
        change_y = 0;
        setPosition(Game.SCREEN_WIDTH / 2, Game.SCREEN_HEIGHT - 100);
        inventory.empty();
        lives = 3;
    }

    public void eventKeyPress(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> {
                int[] frames = { 39, 40, 41, 42 };
                change_y = -step;
                facing = "NORTH";
                angled = "NORTH";
                setCurrentSprite(frames[(int) (Math.random() * 4)]);
            }
            case KeyEvent.VK_S -> {
                int[] frames = { 23, 24, 25, 26 };
                change_y = step;
                facing = "SOUTH";
                angled = "SOUTH";
                setCurrentSprite(frames[(int) (Math.random() * 4)]);
            }
            case KeyEvent.VK_A -> {
                int[] frames = { 47, 48, 49, 50 };
                change_x = -step;
                setCurrentSprite(frames[(int) (Math.random() * 4)]);
            }
            case KeyEvent.VK_D -> {
                int[] frames = { 31, 32, 33, 34 };
                change_x = step;
                setCurrentSprite(frames[(int) (Math.random() * 4)]);
            }
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> angled = facing;
            case KeyEvent.VK_SPACE -> throwBall();
        }
    }

    public void eventKeyRelease(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W, KeyEvent.VK_S -> change_y = 0;
            case KeyEvent.VK_A -> {
                change_x = 0;
                angled = "WEST";
                if (facing.equals("SOUTH"))
                    setCurrentSprite(51);
                else
                    setCurrentSprite(43);
            }
            case KeyEvent.VK_D -> {
                change_x = 0;
                angled = "EAST";
                if (facing.equals("SOUTH"))
                    setCurrentSprite(29);
                else
                    setCurrentSprite(38);
            }
        }
    }

}
