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
        public void actionPerformed(ActionEvent evt){
            if(game != null){
                game.move();
                game.repaint();
            }
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
    private boolean[] keysDown;
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
    private Tile holdTile;
    private ArrayList<Tile> queue = new ArrayList<>();
    private Board board;
    private int counter = 0;
    public final int CONTROL_SPEED = 4;
    private int lastTile = 0;
    private boolean swapped = false;




    public GamePanel(Main m) {
        keys = new boolean[KeyEvent.KEY_LAST+1];
        keysDown = new boolean[KeyEvent.KEY_LAST+1];
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
        if (keys[KeyEvent.VK_UP] && !keysDown[KeyEvent.VK_UP]) {
            keysDown[KeyEvent.VK_UP] = true;
            board.rotate(activeTile);
        }

        if (keys[KeyEvent.VK_SPACE] && !keysDown[KeyEvent.VK_SPACE]) {
            keysDown[KeyEvent.VK_SPACE] = true;
            int travelled = board.hardDrop(activeTile);
            score += travelled * 2;
        }

        if (keys[KeyEvent.VK_C] && !keysDown[KeyEvent.VK_C] && !swapped) {
            hold();
        }
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
        if (!keys[KeyEvent.VK_UP]) {
            keysDown[KeyEvent.VK_UP] = false;
        }

        if (!keys[KeyEvent.VK_SPACE]) {
            keysDown[KeyEvent.VK_SPACE] = false;
        }
    }

    public void move() {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
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
            board.ghostTile(activeTile);
            if (keys[KeyEvent.VK_DOWN]) {
                score += 1;
            }
        }

        else {
            int cleared = board.clearTiles();
            lines += cleared;
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
            level = lines / 10 + 6;
        }
    }

    public void lockTile() {
        activeTile = queue.get(0);
        board.addTile(activeTile);
        queue.remove(0);
        queue.add(generateTile());
        if (swapped) {
            swapped = false;
        }
    }

    public void hold() {
        board.removeTile(activeTile);
        if (holdTile == null) {
            activeTile.resetToDefault();
            holdTile = activeTile;
            activeTile = queue.get(0);
            board.addTile(activeTile);
            queue.remove(0);
            queue.add(generateTile());
        }
        else {
            activeTile.resetToDefault();
            Tile temp = holdTile;
            holdTile = activeTile;
            activeTile = temp;
            board.addTile(activeTile);
            swapped = true;
        }

    }

    public Tile generateTile() {
        int choice;
        do {
            choice = Main.randint(1, 7);
        } while (choice == lastTile);
        lastTile = choice;

        return new Tile(choice, 0);
    }

    public void drawUI(Graphics g) {
        g.drawImage(back,0,0,null);
        g.drawImage(hold, 60, 55, null);
        g.drawImage(scoreBoard, 60, 360, null);
        g.drawImage(nextTiles, 720, 55, null);
        g.drawImage(boardImage, 300, 20, null);
        board.drawTiles(g);

        // Draw hold tile
        drawPreviewTile(g, holdTile, 75, 115, 190, 125);

        // Draw queued tiles
        for (int i = 0; i < queue.size(); i++) {
            drawPreviewTile(g, queue.get(i), 740, 115 + 95 * i, 180, 100);
        }

    }

    public void drawPreviewTile(Graphics g, Tile tile, int x, int y, int width, int height) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (tile != null && tile.getTile()[i][j] != 0) {
                    int xOffset = (width - 35*tile.getSize()) / 2 + x;
                    int yOffset = tile.getId() == 1 ? y + (height - 35) / 2 - 35 : y + (height - 70) / 2;
                    g.drawImage(tile.getImage(), xOffset + i * 35, yOffset + j * 35, null);
                }
            }
        }
    }



    public void paintComponent(Graphics g) {
        drawUI(g);
    }

}

class StartMenu extends JFrame implements ActionListener {
	private Image firstPage;
	private Image secondPage;
	private Image thirdPage;
	
    JPanel cards;
    CardLayout cLayout = new CardLayout();
    
    JButton menuPlay = new JButton ("PLAY");
    JButton menuHighScores = new JButton ("HIGH SCORES");
    JButton menuHowToPlay = new JButton ("HOW TO PLAY");
    JButton menuSettings = new JButton ("Settings");
    JButton instructionFirstNext = JButton ("NEXT");
    JButton instructionsMidNext = JButton ("NEXT");
    JButton instructionsMidPrev = JButton ("BACK");
    JButton instructionsLastPrev = JButton ("BACK");
    JButton done = JButton("DONE");
    
    
    public McKCard() {
    	menuPlay.addActionListener(this);
    	menuHighScores.addActionListener(this);
    	menuHowToPlay.addActionListener(this);
    	menuSettings.addActionListener(this);
    	instructionFirstNext.addActionListener(this);
    	instructionsMidNext.addActionListener(this);
    	instructionsMidPrev.addActionListener(this);
    	instructionsLastPrev.addActionListener(this);
    	done.addActionListner(this);
    	
    	JPanel menuPage = new JPanel();
    	menuPage.setLayout(new BoxLayout(menuPage,BoxLayout.Y_AXIS));
    	menuPage.add(menuPlay);
    	menuPage.add(menuHighScores);
    	menuPage.add(menuHowToPlay);
    	menuPage.add(Settings);
    	
    	JPanel gamePage = new JPanel(GamePanel);
    	
    	JPanel instructionFirstPage = new JPanel();
    	firstPage = new ImageIcon("Assets/howtoplay1.jpg").getImage();
    	
    	cards = new JPanel(cLayout);
    	cards.add(menuPage, "menu");
    	cards.add(gamePage,"game");
    	
    	add(cards);
    	
    	
    }
    
    public void actionPerformed(ActionEvnet evt){
    	Object source = evt.getsource();
    	if (source == menuPlay)
    		cLayout.show(cards, "game");
    }
    
    
}