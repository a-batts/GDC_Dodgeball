public class Ball extends Sprite{

    private final int step;
    private boolean isMoving = false;
    private int[] throwPosition;

    private final double THROW_DISTANCE = Dodgeball.SCREEN_HEIGHT * .5;

    public Ball(int movementStep, int x_pos, int y_pos){
        super(.75, 2, 0, 2);
        String path = "src/main/resources/sprite/dodgeball.png";
        SpritesheetBuilder builder = new SpritesheetBuilder().setFile(path);
        buildSprites(builder, true);

        this.step = movementStep;
        this.setPosition(x_pos - this.getWidth() / 2, y_pos - this.getHeight() / 2);
    }

    public void thrown(int change_x, int change_y){
        isMoving = true;
        this.change_x = change_x;
        this.change_y = change_y;
        throwPosition = new int[]{x_pos , y_pos};
    }

    public boolean isMoving(){
        return isMoving;
    }

    public void bounce(Collision collisions){
        if (!collisions.canMove("LEFT"))
            change_x = step;
        else if (!collisions.canMove("RIGHT"))
            change_x = -step;

        //Need to add test sound file in to check if sound class works
        //Sound bounce = new Sound("src/main/resources/bounce.wav");

    }

    @Override
    public void move(){
        if (this.y_pos > throwPosition[1] - THROW_DISTANCE){
            Collision collisions = new Collision(this);
            if (collisions.atYBoundry())
                bounce(collisions);
            super.move();
            if (collisions.atXBoundry())
                isMoving = false;
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
}
