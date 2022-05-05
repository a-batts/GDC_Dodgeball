package com.abatts.dodgeball;

import java.awt.event.KeyEvent;

public class KeyPressHandler {
    public static void handleKeyPress(KeyEvent e, Board board) {
        switch(Game.getGameState()){
            case OUT_OF_GAME -> {
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    Game.setGameState(GameState.RUNNING);
            }
            case PAUSED -> {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    Game.setGameState(GameState.RUNNING);
            }
            case RUNNING -> {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                    Game.setGameState(GameState.PAUSED);
                else
                    board.player.eventKeyPress(e);
            }
            case STOPPED -> {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    board.player.reset();
                    board.enemy.reset();
                    Game.setGameState(GameState.RUNNING);
                }
            }
        }
    }

    public static void handleKeyRelease(KeyEvent e, Board board) {
        switch(Game.getGameState()){
            case OUT_OF_GAME, PAUSED -> {

            }
            case RUNNING -> {
                board.player.eventKeyRelease(e);
            }
            case STOPPED -> {
            }
        }
    }
}
