package com.abatts.dodgeball;

import com.abatts.dodgeball.entity.Ball;
import com.abatts.dodgeball.entity.Enemy;
import com.abatts.dodgeball.entity.Player;

import javax.swing.*;
import java.awt.*;

public class DrawHandler {
    private final Board board;

    public DrawHandler(Board board){
        this.board = board;
    }

    public void draw(Graphics graphics) {
        Graphics2D painter = (Graphics2D) graphics;
        switch(Game.getGameState()){
            case OUT_OF_GAME -> drawOutOfGame(painter);
            case PAUSED -> drawPaused(painter);
            case RUNNING -> drawInGame(painter);
            case STOPPED -> drawStopped(painter);
        }
    }

    private void drawOutOfGame(Graphics2D painter) {
    }

    private void drawPaused(Graphics2D painter){
        painter.setFont(new Font("Consolas", Font.PLAIN, 90));
        painter.setColor(Color.BLACK);
        painter.drawString("PAUSED", 100, Game.SCREEN_MIDPOINT);
        painter.setFont(new Font("Consolas", Font.PLAIN, 30));
        painter.drawString("PRESS ESC TO CONTINUE", 100, Game.SCREEN_MIDPOINT + 40);
    }

    private void drawInGame(Graphics2D painter){
        Player player = board.player;
        Enemy enemy = board.enemy;

        Image background = new ImageIcon("src/main/resources/floor.png").getImage();
        painter.drawImage(background, -7, 0, board);

        for(Ball b: board.balls){
            if (b.isVisible())
                painter.drawImage(b.currentSprite(),b.getX_pos(), b.getY_pos(), b.getWidth(), b.getHeight(), board);
        }
        painter.drawImage(player.currentSprite(), player.getX_pos(), player.getY_pos(), player.getWidth(), player.getHeight(), board);
        painter.drawImage(enemy.currentSprite(), enemy.getX_pos(), enemy.getY_pos(), enemy.getWidth(), enemy.getHeight(), board);

        drawScoreboard(painter);
    }

    private void drawScoreboard(Graphics2D painter) {
        Player player = board.player;
        Enemy enemy = board.enemy;

        int step = 15;
        int x_pos = 25;
        int spriteSize = 30;

        Image life = new ImageIcon("src/main/resources/sprite/life.png").getImage();
        for(int i = 0; i < player.getLives(); i ++){
            painter.drawImage(life, x_pos, 30, spriteSize, spriteSize, board);
            x_pos = x_pos + step + spriteSize;
        }

        Image ball = new ImageIcon("src/main/resources/sprite/dodgeball_outlined.png").getImage();
        x_pos = Game.SCREEN_WIDTH - 70;
        for(int i = 0; i < player.getInventory().size(); i ++){
            painter.drawImage(ball, x_pos, 30, spriteSize, spriteSize, board);
            x_pos = x_pos - step - spriteSize;
        }

        painter.setFont(new Font("Consolas", Font.PLAIN, 20));
        painter.setColor(Color.BLACK);
        painter.drawString("Score: " + (Enemy.INIT_LIVES - enemy.getLives()), 31, 85);
        painter.drawString("Wins: " + Game.PREV_WINS, 31, 115);

    }

    private void drawStopped(Graphics2D painter){
        Player player = board.player;
        Enemy enemy = board.enemy;

        painter.setFont(new Font("Consolas", Font.PLAIN, 90));

        if (player.getLives() == 0){
            painter.setColor(Color.RED);
            painter.drawString("YOU LOST, GAME OVER", 20, Game.SCREEN_MIDPOINT + 160);
        }
        else if (enemy.getLives() == 0){
            painter.setColor(Color.BLACK);
            painter.drawString("YOU WON, GOOD GAME", 50, Game.SCREEN_MIDPOINT + 160);
        }
        painter.setFont(new Font("Consolas", Font.PLAIN, 25));
        painter.setColor(Color.BLACK);
        painter.drawString("Press enter to start a new game!", 50, Game.SCREEN_MIDPOINT + 230);
    }
}
