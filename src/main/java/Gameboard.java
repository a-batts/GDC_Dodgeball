import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Gameboard extends JPanel implements ActionListener {

    private Player player;
    private final ArrayList<Ball> balls = new ArrayList<>();

    static final int TICK_DELAY_MS = 10;

    public Gameboard(){
        setup();
    }

    public void setup() {
        addKeyListener(new KeyPress());
        setFocusable(true);

        player = new Player();

        int numberBalls = 6;
        int xStep = Dodgeball.SCREEN_WIDTH / numberBalls / 2;
        int currentX = xStep - 5;

        for (int i = 0; i < numberBalls; i++){
            balls.add(new Ball(2, currentX, Dodgeball.SCREEN_MIDPOINT));
            currentX += (2 * xStep);
        }

        Timer timer = new Timer(TICK_DELAY_MS, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick();
    }

    public void tick(){
        player.move();
        updateBalls();

        repaint();
    }

    public void updateBalls(){
        for (Ball b : balls) {
            if (b.isMoving()){
                b.move();
                if (Collision.isCollidingWithOther(player, b) && ! player.getInventory().contains(b)){
                    player.removeLife();
                    b.move();
                    b.stopMoving();
                }
            }
            else{
                if (player.inventorySize() < 2 && Collision.isCollidingWithOther(player, b))
                    player.grabBall(b);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
        Toolkit.getDefaultToolkit().sync();
    }

    private void draw(Graphics graphics) {
        Graphics2D painter = (Graphics2D) graphics;

        Image background = new ImageIcon("src/main/resources/floor.png").getImage();
        painter.drawImage(background, -7, 0, this);

        for(Ball b: balls){
            if (b.isVisible())
                painter.drawImage(b.currentSprite(),b.getX_pos(), b.getY_pos(), b.getWidth(), b.getHeight(), this);
        }
        painter.drawImage(player.currentSprite(), player.getX_pos(), player.getY_pos(), player.getWidth(), player.getHeight(), this);

        drawScoreboard(painter);
    }

    private void drawScoreboard(Graphics2D painter) {
        int step = 15;
        int x_pos = 25;
        int spriteSize = 30;

        Image life = new ImageIcon("src/main/resources/sprite/life.png").getImage();
        for(int i = 0; i < player.getLives(); i ++){
            painter.drawImage(life, x_pos, 30, spriteSize, spriteSize, this);
            x_pos = x_pos + step + spriteSize;
        }

        Image ball = new ImageIcon("src/main/resources/sprite/dodgeball_outlined.png").getImage();
        x_pos = Dodgeball.SCREEN_WIDTH - 70;
        for(int i = 0; i < player.inventorySize(); i ++){
            painter.drawImage(ball, x_pos, 30, spriteSize, spriteSize, this);
            x_pos = x_pos - step - spriteSize;
        }

    }

    private class KeyPress extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.eventKeyPress(e);
        }
        @Override
        public void keyReleased(KeyEvent e) {
            player.eventKeyRelease(e);
        }
    }
}

