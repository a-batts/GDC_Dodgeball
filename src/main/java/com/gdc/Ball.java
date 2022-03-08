package com.gdc;

public class Ball extends Sprite{

    private final int step;
    private boolean isMoving = false;

    private int timeInMotion = 0;

    /**
     * Initialize a new ball
     * @param movementStep step to change on tick
     * @param x_pos init x position
     * @param y_pos init y position
     */
    public Ball(int movementStep, int x_pos, int y_pos){
        super(1, 2, 0, 2);
        String path = "src/main/resources/sprite/dodgeball.png";
        SpritesheetBuilder builder = new SpritesheetBuilder().setFile(path);
        buildSprites(builder, true);

        this.step = movementStep;
        this.setPosition(x_pos - this.getWidth() / 2, y_pos - this.getHeight() / 2);
    }

    /**
     * Set ball into motion on throw
     * @param change_x x step to change on tick
     * @param change_y y step to change on tick
     */
    public void thrown(int change_x, int change_y){
        isMoving = true;
        timeInMotion = 0;
        this.change_x = change_x;
        this.change_y = change_y;
    }

    /**
     * Bounces ball on collision with wall
     * @param collisions Collision object for ball
     */
    public void bounce(Collision collisions){
        if (!collisions.canMove("LEFT"))
            change_x = step;
        else if (!collisions.canMove("RIGHT"))
            change_x = -step;
        else if (!collisions.canMove("UP")){
            change_y = step;
        }
        else if (!collisions.canMove("DOWN")){
            change_y = -step;
        }

        //Need to add test sound file in to check if sound class works
        //com.gdc.Sound bounce = new com.gdc.Sound("src/main/resources/sound/bounce.wav");

    }

    /**
     * Transfer momentum of ball on hit
     * @param hitBy Ball that collided with instance
     */
    public void collide(Ball hitBy) {
        hitBy.stopMoving();
        change_y = hitBy.change_y;
        timeInMotion = hitBy.getTimeInMotion();
        isMoving = true;
    }

    @Override
    public void move(){
        timeInMotion = (timeInMotion + Gameboard.TICK_DELAY_MS);

        double speed = (15 -  .05 * ((timeInMotion - 18) ^ 2));
        if (change_y < 0)
            change_y = -speed;
        else
            change_y = speed;

        if(speed > 0) {
            Collision collisions = new Collision(this);
            if (collisions.atYBoundry() || collisions.atXBoundry()){
                bounce(collisions);
            }
            super.move();
        }
        else
            isMoving = false;
    }

    public void stopMoving(){
        isMoving = false;
    }

    public int getStep(){
        return step;
    }

    /**
     * Return if the ball is moving
     * @return boolean
     */
    public boolean isMoving(){
        return isMoving;
    }

    /**
     * Return how long the ball has been traveling for
     * @return int
     */
    public int getTimeInMotion() { return timeInMotion; }

}
