import javax.swing.*;
import java.awt.EventQueue;

public class Dodgeball extends JFrame {

    public Dodgeball(){
        setupInterface();
    }

    public void setupInterface(){
        add(new Gameboard());
        setSize(500, 500);
        setTitle("Cat Dodgeball");
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
