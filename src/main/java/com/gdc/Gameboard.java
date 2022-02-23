package com.gdc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Gameboard extends JPanel implements ActionListener {

    private Player player;
    private Enemy enemy;
    private ArrayList<Ball> balls;
    private final Timer timer = new Timer(TICK_DELAY_MS, this);

    private int previousWins = 0;
    private boolean running = true;

    static final int TICK_DELAY_MS = 10;

    public Gameboard(){
        addKeyListener(new KeyPress());
        setFocusable(true);
        setup();
    }

    public void setup() {
        player = new Player();
        enemy = new Enemy();

        int numberBalls = 6;
        int xStep = Dodgeball.SCREEN_WIDTH / numberBalls / 2;
        int currentX = xStep - 5;

        balls = new ArrayList<>();
        for (int i = 0; i < numberBalls; i++){
            balls.add(new Ball(2, currentX, Dodgeball.SCREEN_MIDPOINT));
            currentX += (2 * xStep);
        }
        timer.start();
        running = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick();
    }

    public void tick(){
        if (player.getLives() > 0 && enemy.getLives() > 0){
            player.move();
            enemy.move(balls, player);
            updateBalls();
        }
        else{
            running = false;
            player.clearInventory();
        }

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
                else if (Collision.isCollidingWithOther(enemy, b) && ! enemy.getInventory().contains(b)){
                    enemy.removeLife();
                    b.move();
                    b.stopMoving();
                }

                ArrayList<Sprite> spr = new ArrayList<>(balls);
                if (Collision.isCollidingWithOther(b, spr)){
                    Ball ballToMove = (Ball) Collision.getCollidedWith(b, spr);
                    if (ballToMove != null)
                        ballToMove.collide(b);
                }
            }
            else{
                if (player.inventorySize() < 2 && Collision.isCollidingWithOther(player, b))
                    player.grabBall(b);
                else if (enemy.inventorySize() < 2 && Collision.isCollidingWithOther(enemy, b))
                    enemy.grabBall(b);
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
        painter.drawImage(enemy.currentSprite(), enemy.getX_pos(), enemy.getY_pos(), enemy.getWidth(), enemy.getHeight(), this);

        drawScoreboard(painter);

        painter.setFont(new Font("Consolas", Font.PLAIN, 90));
        if(!running){
            if (player.getLives() == 0){
                painter.setColor(Color.RED);
                painter.drawString("YOU LOST, GAME OVER", 20, Dodgeball.SCREEN_MIDPOINT + 160);
            }
            else if (enemy.getLives() == 0){
                painter.setColor(Color.BLACK);
                painter.drawString("YOU WON, GOOD GAME", 50, Dodgeball.SCREEN_MIDPOINT + 160);
            }
            painter.setFont(new Font("Consolas", Font.PLAIN, 25));
            painter.setColor(Color.BLACK);
            painter.drawString("Press enter to start a new game!", 50, Dodgeball.SCREEN_MIDPOINT + 230);
        }
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

        painter.setFont(new Font("Consolas", Font.PLAIN, 20));
        painter.setColor(Color.BLACK);
        painter.drawString("Score: " + (Enemy.INIT_LIVES - enemy.getLives()), 31, 85);
        painter.drawString("Wins: " + previousWins, 31, 115);

    }

    private class KeyPress extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!running && e.getKeyCode() == KeyEvent.VK_ENTER){
                if (enemy.getLives() == 0)
                    previousWins++;
                timer.stop();
                setup();
            }
            else
                player.eventKeyPress(e);
        }
        @Override
        public void keyReleased(KeyEvent e) {
            player.eventKeyRelease(e);
        }
    }
}

