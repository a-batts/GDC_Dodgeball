import java.awt.event.KeyEvent;

public class Player extends Sprite{
    private final int step;

    public Player(int movementStep){
        super("src/main/resources/player.png", .025, Dodgeball.SCREEN_WIDTH / 2, Dodgeball.SCREEN_HEIGHT / 2, 1);
        this.step = movementStep;
    }

    public Player(){
        this(2);
    }

    public void eventKeyPress(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP -> change_y = -step;
            case KeyEvent.VK_DOWN -> change_y = step;
            case KeyEvent.VK_LEFT -> change_x = -step;
            case KeyEvent.VK_RIGHT -> change_x = step;
        }
    }

    public void eventKeyRelease(KeyEvent e){
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP, KeyEvent.VK_DOWN -> change_y = 0;
            case KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT -> change_x = 0;
        }
    }
}
