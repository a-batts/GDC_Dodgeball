package com.gdc;

import com.gdc.entity.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class Board extends JPanel implements ActionListener {
    public static final int TICK_DELAY_MS = 10;

    protected final LinkedList<Ball> balls = new LinkedList<>();
    protected final Enemy enemy = new Enemy();
    protected final Player player = new Player();

    private final DrawHandler painter = new DrawHandler(this);

    public Board(){
        addKeyListener(new KeyPress());
        setFocusable(true);
        loadEntities();

        Timer timer = new Timer(TICK_DELAY_MS, this);
        timer.start();
    }

    public void loadEntities(){
        balls.clear();
        int numberBalls = 6;
        int dx = Game.SCREEN_WIDTH / numberBalls / 2;
        int curx = dx - 5;

        for (int i = 0; i < numberBalls; i++){
            balls.add(new Ball(2, curx, Game.SCREEN_MIDPOINT));
            curx += dx * 2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        doGameTick();
    }

    public void doGameTick(){
        switch(Game.getGameState()){
            case OUT_OF_GAME, PAUSED -> {

            }
            case RUNNING -> {
                player.move();
                enemy.move(balls, player);
                doBallTick();

                if (enemy.getLives() == 0 || player.getLives() == 0) {
                    Game.setGameState(GameState.STOPPED);
                    if (enemy.getLives() == 0)
                        Game.PREV_WINS ++;
                }
            }
            case STOPPED -> {
                loadEntities();
            }
        }
        repaint();
    }

    public void doBallTick(){
        for(Ball b: balls){
            if(b.isInMotion()){
                b.move();
                if (b.wasThrownBy(enemy) && player.isCollidingWith(b)) {
                    player.removeLife();
                    b.doHit();
                }
                else if (b.wasThrownBy(player) && enemy.isCollidingWith(b)){
                    enemy.removeLife();
                    b.doHit();
                }

                Ball colliding = (Ball) b.isCollidingWith(new LinkedList<>(balls));
                if (colliding != null)
                    colliding.collide(b);
            }
            else{
                if (player.getInventory().hasEmptyHand() && player.isCollidingWith(b))
                    player.grabBall(b);
                else if (enemy.getInventory().hasEmptyHand() && enemy.isCollidingWith(b))
                    enemy.grabBall(b);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        painter.draw(graphics);
        Toolkit.getDefaultToolkit().sync();
    }

    private class KeyPress extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            KeyPressHandler.handleKeyPress(e, getSelfInstance());
        }

        @Override
        public void keyReleased(KeyEvent e) {
            KeyPressHandler.handleKeyRelease(e, getSelfInstance());
        }
    }

    public Board getSelfInstance(){
        return this;
    }
}
