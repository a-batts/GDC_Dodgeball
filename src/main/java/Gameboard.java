import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Gameboard extends JPanel implements ActionListener {

    private Player player;

    public Gameboard(){
        setup();
    }

    public void setup() {
        addKeyListener(new KeyPress());
        setFocusable(true);
        setBackground(Color.DARK_GRAY);

        player = new Player();

        Timer timer = new Timer(5, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick();
    }

    public void tick(){
        player.move();
        repaint(player.getX_pos() - 2, player.getY_pos() - 2, player.getWidth() + 5, player.getHeight() + 5);
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        draw(graphics);
        Toolkit.getDefaultToolkit().sync();
    }

    private void draw(Graphics graphics) {
        Graphics2D painter = (Graphics2D) graphics;
        painter.drawImage(player.getImage(), player.getX_pos(), player.getY_pos(), player.getWidth(), player.getHeight(), this);
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

