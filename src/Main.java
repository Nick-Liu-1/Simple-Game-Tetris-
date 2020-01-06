import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame implements ActionListener{
    javax.swing.Timer myTimer;
    GamePanel game;

    public static void main(String[] args) {
        Main frame = new Main();
    }

    public Main() {
        super("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,750);

        myTimer = new javax.swing.Timer(10,this);

        game = new GamePanel(this);
        add(game);

        setResizable(false);
        setVisible(true);
    }

    public void start(){
        myTimer.start();
    }

    public void actionPerformed(ActionEvent evt) {
        game.move();
        game.repaint();
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

class GamePanel extends JPanel implements KeyListener {
    private boolean[] keys;
    private Main mainFrame;
    private Image back;
    private Image hold;
    private Image scoreBoard;
    private Image nextTiles;
    private Image boardImage;

    public GamePanel(Main m) {
        keys = new boolean[KeyEvent.KEY_LAST+1];
        back = new ImageIcon("background.jpg").getImage();
        hold = new ImageIcon("holdBox.PNG").getImage();
        scoreBoard = new ImageIcon("scoreBox.PNG").getImage();
        nextTiles = new ImageIcon("nextBox.PNG").getImage();
        boardImage = new ImageIcon("gameBox.PNG").getImage();

        mainFrame = m;
        setSize(1000, 750);
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void move() {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
    }

    public void paintComponent(Graphics g) {
        g.drawImage(back,0,0,null);
    }

}