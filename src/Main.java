import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
    javax.swing.Timer myTimer;
    GamePanel game;

    public static void main(String[] args) {
        Main frame = new Main();
    }

    public Main() {
        super("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,770);

        myTimer = new javax.swing.Timer(10, new TickListener());

        game = new GamePanel(this);
        add(game);

        setResizable(false);
        setVisible(true);
    }

    public void start(){
        myTimer.start();

    }

    class TickListener implements ActionListener {
        private int counter = 0;
        public void actionPerformed(ActionEvent evt){
            if(game != null){
                game.move();
                game.repaint();
            }
            counter++;
        }

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

    private final int[] speedCurve = { 0, 60, 48, 37, 28, 21, 16, 11, 8, 6, 4 };
    private int speed = 0;
    private int score = 0;
    private int level = 5;
    private int lines = 0;
    private Tile activeTile;
    private ArrayList<Tile> queue = new ArrayList<>();
    private Board board;
    private int counter = 0;
    public final int CONTROL_SPEED = 4;

    private boolean upPressed = false;
    private boolean spacePressed = false;



    public GamePanel(Main m) {
        keys = new boolean[KeyEvent.KEY_LAST+1];
        back = new ImageIcon("Assets/background.jpg").getImage();
        hold = new ImageIcon("Assets/holdBox.PNG").getImage();
        scoreBoard = new ImageIcon("Assets/scoreBox.PNG").getImage();
        nextTiles = new ImageIcon("Assets/nextBox.PNG").getImage();
        boardImage = new ImageIcon("Assets/gameBox.jpg").getImage();

        mainFrame = m;
        setSize(1000, 770);
        addKeyListener(this);

        board = new Board();

        activeTile = generateTile();
        for (int i = 0; i < 3; i++) {
            queue.add(generateTile());
        }
        this.board.addTile(activeTile);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

        if (keys[KeyEvent.VK_RIGHT]) {
            board.shiftRight(activeTile);
        }
        if (keys[KeyEvent.VK_LEFT]) {
            board.shiftLeft(activeTile);
        }
        if (keys[KeyEvent.VK_UP] && !upPressed) {
            upPressed = true;
            board.rotate(activeTile);
        }

        if (keys[KeyEvent.VK_DOWN]) {

        }

        if (keys[KeyEvent.VK_SPACE] && !spacePressed) {
            spacePressed = true;
            int travelled = board.hardDrop(activeTile);
            score += travelled * 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        if (!keys[KeyEvent.VK_UP]) {
            upPressed = false;
        }

        if (!keys[KeyEvent.VK_SPACE]) {
            spacePressed = false;
        }
    }

    public void move() {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        //System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
        moveTile();
        counter++;
    }

    public void moveTile() {
        speed = speedCurve[level];
        if (keys[KeyEvent.VK_DOWN]) {
            speed = CONTROL_SPEED;
        }
        boolean success = true;
        if (counter % speed == 0) {
            success = board.shiftDown(activeTile);
        }

        if (success) {
            if (keys[KeyEvent.VK_DOWN]) {
                score += 1;
            }
        }

        else {
            int cleared = board.clearTiles();
            switch (cleared) {
                case(1):
                    score += 100 * level;
                    break;
                case(2):
                    score += 300 * level;
                    break;
                case(3):
                    score += 500 * level;
                    break;
                case(4):
                    score += 800 * level;
                    break;
            }
            lockTile();
            System.out.println(score);
        }
    }

    public void lockTile() {
        activeTile = queue.get(0);
        board.addTile(activeTile);
        queue.remove(0);
        queue.add(generateTile());
    }

    public static Tile generateTile() {
        return new Tile(Main.randint(1, 7));
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