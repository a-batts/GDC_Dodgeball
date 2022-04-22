package com.gdc;

import javax.swing.*;
import java.awt.EventQueue;

public class Game extends JFrame {

    //Constants for screen dimensions
    public final static int SCREEN_HEIGHT = 700;
    public final static int SCREEN_WIDTH = 1000;
    public final static int SCREEN_MIDPOINT = SCREEN_HEIGHT/ 2 - 15;

    public static int PREV_WINS = 0;

    private static GameState GAMESTATE = GameState.OUT_OF_GAME;

    public Game(){
        load();
    }

    /**
     * Initialize a new JFrame and set up the gameboard
     */
    public void load(){
        add(new Board());
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setTitle("Cat Dodgeball");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Start running the game and display it
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }

    public static GameState getGameState(){
        return GAMESTATE;
    }

    public static void setGamestate(GameState state){
        GAMESTATE = state;
    }

}
