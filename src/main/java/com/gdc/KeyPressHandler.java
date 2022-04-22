package com.gdc;

import java.awt.event.KeyEvent;

public class KeyPressHandler {
    public static void handleKeyPress(KeyEvent e, Board board) {
        switch(Game.getGameState()){
            case OUT_OF_GAME -> {

            }
            case PAUSED -> {

            }
            case RUNNING -> {
                board.player.eventKeyPress(e);
            }
            case STOPPED -> {
            }
        }
    }

    public static void handleKeyRelease(KeyEvent e, Board board) {
        switch(Game.getGameState()){
            case OUT_OF_GAME -> {

            }
            case PAUSED -> {

            }
            case RUNNING -> {
                board.player.eventKeyRelease(e);
            }
            case STOPPED -> {
            }
        }
    }
}
