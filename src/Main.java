import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        Main frame = new Main();


    }

    public Main() {
        super("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,1000);
        setLayout(new BorderLayout());

        Board board = new Board();

        setVisible(true);
    }

    public static Tile generateTile() {
        return new Tile(randint(0, 6));
    }

    public static int randint(int low, int high){
        /*
            Returns a random integer on the interval [low, high].
        */
        return (int) (Math.random()*(high-low+1)+low);
    }


}