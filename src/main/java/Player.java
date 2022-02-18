import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Sprite{

    private final int step;
    private int lives;
    private ArrayList<Ball> inventory = new ArrayList<>();

    public Player(){
        this(2);
    }

    public Player(int movementStep){
        super(2.5, Dodgeball.SCREEN_WIDTH / 2, Dodgeball.SCREEN_HEIGHT - 100, 1.5);

        String dir = "src/main/resources/sprite/player/";
        File[] files = new File(dir).listFiles();

        Random rand = new Random();
        assert files != null;
        File file = files[rand.nextInt(files.length)];
        String path = dir + file.getName();

        int[][][] coords = {
                {{4, 5},{29, 23}}, {{35, 6},{61, 23}}, {{67, 6},{93, 23}}, {{98, 7},{125, 23}},
                {{2, 39},{29, 55}}, {{34, 40},{61, 55}}, {{65, 41},{93, 55}}, {{96, 42},{125, 55}},
                {{0, 75}, {29, 87}},
                {{4, 101},{29, 119}}, {{36, 101},{61, 118}}, {{68, 101},{92, 119}}, {{100, 101},{123, 119}},
                {{4, 133},{26, 151}}, {{36, 134},{59, 151}}, {{68, 134},{93, 151}}, {{100, 133},{125, 151}},
                {{4, 165}, {29, 183}},{{36, 165}, {61, 183}},
                {{4, 198},{26, 215}}, {{35, 198},{57, 215}}, {{68, 198},{90, 215}}, {{100, 197},{121, 215}},
                {{10, 225},{20, 249}}, {{42, 226},{51, 250}}, {{74, 225},{84, 249}}, {{106, 226},{116, 250}},
                {{6, 261},{27, 279}}, {{37, 262},{59, 279}}, {{70, 261},{91, 279}}, {{101, 262},{121, 279}},
                {{7, 293},{27, 311}}, {{39, 294},{59, 311}}, {{71, 293},{91, 311}}, {{103, 295},{123, 311}},
                {{6, 325},{27, 343}}, {{37, 326},{59, 343}}, {{70, 325},{91, 343}}, {{101, 326},{123, 343}},
                {{10, 357},{20, 377}}, {{42, 358},{52, 378}}, {{74, 357},{84, 377}}, {{106, 358},{116, 378}},
                {{2, 390},{24, 407}}, {{34, 389},{55, 407}}, {{66, 390},{88, 407}}, {{98, 389},{119, 407}},
                {{4, 422},{24, 439}}, {{36, 421},{56, 439}}, {{68, 422},{88, 439}}, {{100, 421},{120, 439}},
                {{4, 453},{26, 471}}, {{36, 453},{56, 471}}, {{69, 453},{90, 471}}, {{106, 453},{116, 473}},
                {{10, 481},{20, 505}},
        };

        SpritesheetBuilder builder = new SpritesheetBuilder().setFile(path).setCoords(coords);
        buildSprites(builder);
        setCurrentSprite(16);

        this.step = movementStep;
        this.lives = 3;
    }

    public void eventKeyPress(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> {
                change_y = -step;
                facing = "NORTH";
                angled = "NORTH";
                setCurrentSprite(41);
            }
            case KeyEvent.VK_S -> {
                change_y = step;
                facing = "SOUTH";
                angled = "SOUTH";
                setCurrentSprite(23);
            }
            case KeyEvent.VK_A -> {
                change_x = -step;
                setCurrentSprite(48);
            }
            case KeyEvent.VK_D -> {
                change_x = step;
                setCurrentSprite(31);
            }
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> angled = facing;
            case KeyEvent.VK_SPACE -> throwBall();
        }
    }

    public void eventKeyRelease(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W, KeyEvent.VK_S -> change_y = 0;
            case KeyEvent.VK_A -> {
                change_x = 0;
                angled = "WEST";
                if(facing.equals("SOUTH"))
                    setCurrentSprite(51);
                else
                    setCurrentSprite(43);
            }
            case KeyEvent.VK_D -> {
                change_x = 0;
                angled = "EAST";
                if(facing.equals("SOUTH"))
                    setCurrentSprite(29);
                else
                    setCurrentSprite(38);
            }
        }
    }

    public void grabBall(Ball ball){
        if(ball.isVisible()){
            inventory.add(ball);
            ball.visible = false;
        }
    }

    public void throwBall(){
        if (inventorySize() > 0){
            Ball b = inventory.get(0);

            int y_pos; int changeX; int changeY;
            if (facing.equals("SOUTH")){
                changeY = b.getStep();
                y_pos = getBounds().y + getBounds().height;
            }
            else{
                changeY = -b.getStep();
                y_pos = getBounds().y - getBounds().height / 2 - 5;
            }
            switch (angled){
                case "EAST" -> changeX = 2;
                case "WEST" -> changeX = -2;
                default -> changeX = 0;
            }
            b.setPosition(x_pos, y_pos);
            b.thrown(changeX, changeY);
            b.visible = true;
            inventory.remove(b);
        }
    }

    public int inventorySize(){
        int c = 0;
        for(Ball b: inventory){
            if (! b.isMoving())
                c++;
        }
        return c;
    }

    public ArrayList<Ball> getInventory() {
        return inventory;
    }

    public void removeLife(){
        if (lives > 0)
            lives--;
    }

    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }
}
