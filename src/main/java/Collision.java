public class Collision {

    Sprite sprite;
    int sprite_x, sprite_y, sprite_width, sprite_height;
    int scr_width = Dodgeball.SCREEN_WIDTH - 14;
    //Subtract for title bar height
    int scr_height = Dodgeball.SCREEN_HEIGHT - 36;

    public Collision(Sprite sprite) {
        this.sprite = sprite;
        sprite_x = sprite.getX_pos();
        sprite_width = sprite.getWidth();
        sprite_y = sprite.getY_pos();
        sprite_height = sprite.getHeight();
    }

    public boolean canMove(String direction){
        int spriteTopBound = 0;
        int spriteBottomBound = scr_height;
        int spriteLeftBound = 0;
        int spriteRightBound = scr_width;

        if (sprite instanceof Player)
            spriteTopBound = scr_height / 2;

        return switch (direction) {
            case "TOP" -> sprite_y - sprite.change_y > spriteTopBound;
            case "BOTTOM" -> (sprite_y + sprite_height + sprite.change_y) < spriteBottomBound;
            case "LEFT" -> sprite_x - sprite.change_x > spriteLeftBound;
            case "RIGHT" -> (sprite_x + sprite_width + sprite.change_x) < spriteRightBound;
            default -> true;
        };
    }
}
