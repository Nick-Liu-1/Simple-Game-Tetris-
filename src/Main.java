import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    javax.swing.Timer myTimer;
    GamePanel game;

    private static final int[] speedCurve = { 0, 60, 48, 37, 28, 21, 16, 11, 8, 6, 4 };
    private static int score = 0;
    private static  int level = 5;
    private static int lines = 0;
    private static Tile activeTile;
    private static ArrayList<Tile> queue = new ArrayList<>();
    public static Board board;

    public static void main(String[] args) {
        Main frame = new Main();
        board.addTile(activeTile);

    }

    public Main() {
        super("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,750);

        myTimer = new javax.swing.Timer(10, new TickListener());

        board = new Board();

        game = new GamePanel(this, board);
        add(game);



        setResizable(false);
        setVisible(true);
    }

    public void start(){
        myTimer.start();
        activeTile = generateTile();
        for (int i = 0; i < 3; i++) {
            queue.add(generateTile());
        }
    }

    class TickListener implements ActionListener {
        private int counter = 0;
        public void actionPerformed(ActionEvent evt){
            if(game != null){
                game.move();
                game.repaint();
                moveTile();
            }
            counter++;
        }

        public void moveTile() {
            if (counter % speedCurve[level] == 0) {
                board.shiftDown(activeTile);
            }
        }

    }

    public static Tile generateTile() {
        return new Tile(randint(1, 7));
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
    private Board board;

    public GamePanel(Main m, Board board) {
        keys = new boolean[KeyEvent.KEY_LAST+1];
        back = new ImageIcon("Assets/background.jpg").getImage();
        hold = new ImageIcon("Assets/holdBox.PNG").getImage();
        scoreBoard = new ImageIcon("Assets/scoreBox.PNG").getImage();
        nextTiles = new ImageIcon("Assets/nextBox.PNG").getImage();
        boardImage = new ImageIcon("Assets/gameBox.jpg").getImage();

        mainFrame = m;
        setSize(1000, 750);
        addKeyListener(this);

        this.board = board;
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
        //System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
    }

    public void drawUI(Graphics g) {
        g.drawImage(back,0,0,null);
        g.drawImage(hold, 75, 55, null);
        g.drawImage(scoreBoard, 75, 410, null);
        g.drawImage(nextTiles, 720, 55, null);
        g.drawImage(boardImage, 300, 20, null);
        board.drawTiles(g);
    }

    public void paintComponent(Graphics g) {
        drawUI(g);
    }

}