package com.gdc;

import javax.swing.*;
import java.awt.EventQueue;

public class Dodgeball extends JFrame {

    final static int SCREEN_HEIGHT = 700;
    final static int SCREEN_WIDTH = 1000;
    static int SCREEN_MIDPOINT;

    public Dodgeball(){
        SCREEN_MIDPOINT = SCREEN_HEIGHT / 2 - 15;

        load();
    }

    /**
     * Initialize a new JFrame and set up the gameboard
     */
    public void load(){
        add(new Gameboard());
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setTitle("Cat com.gdc.Dodgeball");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    /**
     * Start running the game and display it
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            Dodgeball game = new Dodgeball();
            game.setVisible(true);
        });
    }

}
