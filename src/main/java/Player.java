import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Sprite{

    private final int step;
    private int lives;
    private ArrayList<Ball> inventory = new ArrayList<>();

    public Player(int movementStep){
        super("src/main/resources/player.png", .025, Dodgeball.SCREEN_WIDTH / 2, Dodgeball.SCREEN_HEIGHT - 100, 1.5);
        this.step = movementStep;
        this.lives = 3;
    }

    public Player(){
        this(2);
    }

    public void eventKeyPress(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> {
                change_y = -step;
                facing = "NORTH";
                angled = "NORTH";
            }
            case KeyEvent.VK_S -> {
                change_y = step;
                facing = "SOUTH";
                angled = "SOUTH";
            }
            case KeyEvent.VK_A -> change_x = -step;
            case KeyEvent.VK_D -> change_x = step;
            case KeyEvent.VK_LEFT -> angled = "WEST";
            case KeyEvent.VK_RIGHT -> angled = "EAST";
            case KeyEvent.VK_SPACE -> throwBall();
        }
    }

    public void eventKeyRelease(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W, KeyEvent.VK_S -> change_y = 0;
            case KeyEvent.VK_A, KeyEvent.VK_D -> change_x = 0;
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
                y_pos = getBounds().y - getBounds().height - 10;
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
}
