public class Ball extends Sprite{

    private final int step;
    private boolean isMoving = false;

    private int timeInMotion = 0;

    public Ball(int movementStep, int x_pos, int y_pos){
        super(1, 2, 0, 2);
        String path = "src/main/resources/sprite/dodgeball.png";
        SpritesheetBuilder builder = new SpritesheetBuilder().setFile(path);
        buildSprites(builder, true);

        this.step = movementStep;
        this.setPosition(x_pos - this.getWidth() / 2, y_pos - this.getHeight() / 2);
    }

    public void thrown(int change_x, int change_y){
        isMoving = true;
        timeInMotion = 0;
        this.change_x = change_x;
        this.change_y = change_y;
    }

    public void bounce(Collision collisions){
        if (!collisions.canMove("LEFT"))
            change_x = step;
        else if (!collisions.canMove("RIGHT"))
            change_x = -step;
        else if (!collisions.canMove("TOP")){
            change_y = step;
        }
        else if (!collisions.canMove("BOTTOM")){
            change_y = -step;
        }

        //Need to add test sound file in to check if sound class works
        //Sound bounce = new Sound("src/main/resources/sound/bounce.wav");

    }

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

    public boolean isMoving(){
        return isMoving;
    }

    public int getTimeInMotion() { return timeInMotion; }

}
