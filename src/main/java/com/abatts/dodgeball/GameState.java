package com.abatts.dodgeball;

public enum GameState {
    OUT_OF_GAME(0), PAUSED(1), RUNNING(2), STOPPED(3);

    private final int state;

    GameState(int state){
        this.state = state;
    }

    public int getState(){
        return state;
    }
}
