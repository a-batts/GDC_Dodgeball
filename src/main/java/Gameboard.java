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

    private final int NUMBER_BALLS = 6;

    public Gameboard(){
        setup();
    }


    public void setup() {
        addKeyListener(new KeyPress());
        setFocusable(true);
        setBackground(Color.ORANGE);

        player = new Player();

        int xStep = Dodgeball.SCREEN_WIDTH / NUMBER_BALLS / 2;
        int currentX = xStep;

        for (int i = 0; i < NUMBER_BALLS; i++){
            balls.add(new Ball(2, currentX, Dodgeball.SCREEN_HEIGHT / 2));
            currentX += (2 * xStep);
        }

        Timer timer = new Timer(5, this);
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

        painter.drawImage(player.currentSprite(), player.getX_pos(), player.getY_pos(), player.getWidth(), player.getHeight(), this);
        for(Ball b: balls){
            if (b.isVisible())
                painter.drawImage(b.currentSprite(),b.getX_pos(), b.getY_pos(), b.getWidth(), b.getHeight(), this);
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

