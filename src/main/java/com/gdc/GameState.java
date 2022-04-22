package com.gdc;

public enum GameState {
    OUT_OF_GAME(0), PAUSED(1), RUNNING(2), STOPPED(3);

    private int state;

    GameState(int state){
        this.state = state;
    }

    public int getState(){
        return state;
    }
}
