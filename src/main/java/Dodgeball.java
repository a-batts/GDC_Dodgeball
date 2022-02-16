import javax.swing.*;
import java.awt.EventQueue;

public class Dodgeball extends JFrame {

    final static int SCREEN_HEIGHT = 700;
    final static int SCREEN_WIDTH = 1000;

    public Dodgeball(){
        load();
    }

    public void load(){
        add(new Gameboard());
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setTitle("Cat Dodgeball");

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            Dodgeball game = new Dodgeball();
            game.setVisible(true);
        });
    }

}
