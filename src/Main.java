import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.applet.*;

public class Main extends JFrame implements ActionListener {
    private javax.swing.Timer myTimer;  // Game Timer
    private GamePanel game;  // GamePanel for the actual game

    // Cards for switching between pages of menu
    private JPanel cards;
    private CardLayout cLayout = new CardLayout();

    // Buttons

    // Play button and images
    private ImageIcon menuPlayImage = new ImageIcon("Buttons/play.png");
    private JButton menuPlay = new JButton(menuPlayImage);

    // High scores button
    private ImageIcon menuHighScoresImage = new ImageIcon("Buttons/highScores.png");
    JButton menuHighScores = new JButton (menuHighScoresImage);

    // How to play button
    private ImageIcon menuHowToPlayImage = new ImageIcon("Buttons/howtoplay.png");
    JButton menuHowToPlay = new JButton (menuHowToPlayImage);

    // All of the home buttons that return you to main menu
    private ImageIcon homeImage = new ImageIcon("Buttons/home.png");
    private JButton home = new JButton(homeImage);
    private JButton home1 = new JButton(homeImage);
    private JButton home2 = new JButton(homeImage);

    // All the done buttons that return you to main menu
    private ImageIcon doneImage = new ImageIcon("Buttons/done.png");
    private JButton done = new JButton(doneImage);
    private JButton done1 = new JButton(doneImage);
    private JButton done2 = new JButton(doneImage);

    // Left arrow buttons for how to play menu
    private ImageIcon leftArrowImage = new ImageIcon("Buttons/prev.png");
    private JButton leftArrow = new JButton(leftArrowImage);
    private JButton leftArrow1 = new JButton(leftArrowImage);

    // Right arrow buttons for how to play menu
    private ImageIcon rightArrowImage = new ImageIcon("Buttons/prevClick.png");
    private JButton rightArrow = new JButton(rightArrowImage);
    private JButton rightArrow1 = new JButton(rightArrowImage);

    // Nickname entry on game over screen
    private JTextField nickname = new JTextField();  // Text field for user to enter their nickname
    private String name = "";  // Name inputted
    private int score = 0;  // User's score

    // Pages
    private Menu menuPage;
    private HighScores highScorePage;
    private GameOver gameOver;
    private HowToPlay howToPlay1;
    private HowToPlay howToPlay2;
    private HowToPlay howToPlay3;


    public static void main(String[] args) {
        // Creating the frame
        Main frame = new Main();
    }

    public Main() {
        // Creating frame
        super("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,770);

        // Cards
        cards = new JPanel(cLayout);

        // Main menu
        menuPage = new Menu();
        menuInit();
        cards.add(menuPage, "menu");

        // High Score page
        highScorePage = new HighScores();
        highScoreInit();
        cards.add(highScorePage,"high score");

        // Instructions page. Creating and adding all 3 pages.
        howToPlay1 = new HowToPlay(1);
        cards.add(howToPlay1,"how to play 1");
        howToPlay2 = new HowToPlay(2);
        cards.add(howToPlay2,"how to play 2");
        howToPlay3 = new HowToPlay(3);
        cards.add(howToPlay3,"how to play 3");
        howToPlayInit();

        // GamePanel
        game = new GamePanel(this);
        cards.add(game,"game");

        // GameOver
        gameOver = new GameOver();
        cards.add(gameOver, "game over");

        add(cards); // Add cards to frame

        myTimer = new javax.swing.Timer(10, new TickListener());

        setResizable(false);
        setVisible(true);

    }

    public void menuInit() {
        /*
            Creates all of the buttons for the main menu. Adds their action listeners, sets their position and sizes,
            and adds them to the menuPage.
         */

        // Adding action listeners to buttons
        menuPlay.addActionListener(this);
        menuHighScores.addActionListener(this);
        menuHowToPlay.addActionListener(this);

        menuPage.setLayout(null);

        // Play button
        menuPlay.setBounds(305 + (350 - menuPlayImage.getIconWidth()) / 2, 280, menuPlayImage.getIconWidth(), menuPlayImage.getIconHeight());
        menuPage.add(menuPlay);

        // High scores button
        menuHighScores.setBounds(305 + (350 - menuHighScoresImage.getIconWidth()) / 2, 380, menuHighScoresImage.getIconWidth(), menuHighScoresImage.getIconHeight());
        menuPage.add(menuHighScores);

        // How to play button
        menuHowToPlay.setBounds(305 + (350 - menuHowToPlayImage.getIconWidth()) / 2, 480, menuHowToPlayImage.getIconWidth(), menuHowToPlayImage.getIconHeight());
        menuPage.add(menuHowToPlay);
    }

    public void highScoreInit() {
        /*
            Creates all of the buttons for the high scores page. Adds their action listeners, sets their position and sizes,
            and adds them to the highScorePage.
         */

        highScorePage.setLayout(null);

        // Home button
        home.addActionListener(this);
        home.setBounds(305 + (350 - homeImage.getIconWidth()) / 2, 600, homeImage.getIconWidth(), homeImage.getIconHeight());
        highScorePage.add(home);

    }

    public void howToPlayInit() {
        /*
            Creates all of the buttons for all pages of the how to play menu. Adds their action listeners, sets their
            position and sizes, and adds them to the howToPlay page.
         */

        // First page
        howToPlay1.setLayout(null);

        // Done button
        done.addActionListener(this);
        done.setBounds(305 + (350 - doneImage.getIconWidth()) / 2, 680, doneImage.getIconWidth(), doneImage.getIconHeight());
        howToPlay1.add(done);

        // Right arrow button
        rightArrow.addActionListener(this);
        rightArrow.setBounds(550, 680, rightArrowImage.getIconWidth(), rightArrowImage.getIconHeight());
        howToPlay1.add(rightArrow);

        // Second page
        howToPlay2.setLayout(null);

        // Done button
        done1.addActionListener(this);
        done1.setBounds(305 + (350 - doneImage.getIconWidth()) / 2, 680, doneImage.getIconWidth(), doneImage.getIconHeight());
        howToPlay2.add(done1);

        // Left arrow button
        leftArrow1.addActionListener(this);
        leftArrow1.setBounds(360, 680, leftArrowImage.getIconWidth(), leftArrowImage.getIconHeight());
        howToPlay2.add(leftArrow1);

        // Right arrow button
        rightArrow1.addActionListener(this);
        rightArrow1.setBounds(550, 680, rightArrowImage.getIconWidth(), rightArrowImage.getIconHeight());
        howToPlay2.add(rightArrow1);

        // Third page
        howToPlay3.setLayout(null);

        // Done button
        done2.addActionListener(this);
        done2.setBounds(305 + (350 - doneImage.getIconWidth()) / 2, 680, doneImage.getIconWidth(), doneImage.getIconHeight());
        howToPlay3.add(done2);

        // How to play button
        leftArrow.addActionListener(this);
        leftArrow.setBounds(360, 680, leftArrowImage.getIconWidth(), leftArrowImage.getIconHeight());
        howToPlay3.add(leftArrow);
    }

    public void start(){
        /*
            Called when game starts. Starts the timer and resets nickname and score for high score purposes.
         */

        myTimer.start();
        name = "";
        score = 0;
        nickname.setText("");
    }

    public void gameOver(int score)  {
        /*
            Creates all of the buttons and text field to enter nickname for game over screen. Adds their action listeners,
            sets their position and sizes, and adds them to the gameOver screen.
         */
        gameOver.setLayout(null);
        cLayout.show(cards, "game over");
        this.score = score;  // Setting score in main class

        // Nickname text field
        nickname.addActionListener(this);
        nickname.setBounds(318, 400, 325, 50);
        nickname.setFont(new Font("Helvetica", Font.PLAIN, 36));
        gameOver.add(nickname);

        // Setting the score of the gameOver page
        gameOver.setScore(score);

        // Home button
        home2.addActionListener(this);
        home2.setBounds(305 + (350 - homeImage.getIconWidth())/2, 600, homeImage.getIconWidth(), homeImage.getIconHeight());
        gameOver.add(home2);

    }

    public void actionPerformed(ActionEvent evt){
        /*
            Deals with the buttons as they are pressed. Takes user to the specified page depending on what
            button was clicked.
         */

        Object source = evt.getSource();

        // Play button; takes user to game panel
        if (source == menuPlay) {
            cLayout.show(cards,"game");
            game.grabFocus();  // Focuses on game panel
            game.init();  // Initiates game
        }

        // High scores button; takes user to high score page
        else if (source == menuHighScores) {
            // Read high scores from file to display to user
            try {
                highScorePage.readFromFile("scores.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            cLayout.show(cards,"high score");
        }

        // How to play button; takes user to how to play menu
        else if (source == menuHowToPlay) {
            cLayout.show(cards, "how to play 1");
        }

        // Various home and done buttons; takes user to main menu
        else if (source == home || source == home1 || source == done1 || source == done2 || source == done) {
            cLayout.show(cards,"menu");
        }

        // Right arrows for how to play menu to switch between pages
        else if (source == rightArrow) {
            cLayout.show(cards,"how to play 2");
        }

        else if (source == rightArrow1) {
            cLayout.show(cards,"how to play 3");
        }

        // Left arrows for how to play menu to switch between pages
        else if (source == leftArrow1) {
            cLayout.show(cards,"how to play 1");
        }

        else if (source == leftArrow) {
            cLayout.show(cards,"how to play 2");
        }

        // Home button for game over screen. Updates high scores in addition to taking user to main menu.
        else if (source == home2) {
            cLayout.show(cards,"menu");
            name = nickname.getText();  // Getting name from what user typed into text field.

            // Updating high scores by writing to file
            try {
                highScorePage.readFromFile("scores.txt");
                highScorePage.addScore(name, score);
                highScorePage.writeToFile("scores.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Resetting score, name, and text in textfield
            score = 0;
            name = "";
            nickname.setText("");
        }

    }

    // Class that deals with actions within the game
    class TickListener implements ActionListener {
        public void actionPerformed(ActionEvent evt){
            /*
                Method is called every tick while the game is running. Calls the move and paint methods
                which deal with game logic.
            */
            if (game != null) {
                game.grabFocus();
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

class GamePanel extends JPanel implements KeyListener, MouseListener {
    private boolean started = false;  // Indicates if game has started
    private boolean[] keys;   // Array of keys that keeps track if they are down or not
    private boolean[] keysDown;   // Flag for keys that keeps track if a key is already down
    private Main mainFrame;   // Frame of the program

    // Images
    private final Image back;  // Background
    private final Image hold;   // Hold box
    private final Image scoreBoard;  // Box that has score, lines, and level
    private final Image nextTiles;  // Box that shows next tiles
    private final Image boardImage;  // Tetris board

    private final int[] speedCurve = { 0, 60, 48, 37, 28, 21, 16, 11, 8, 6, 4, 3, 2 };  // At level i, tile shifts down after speedCurve[i] frames

    // Various game information
    private int speed;  // Frequency that tiles drop down at
    private int score;  // User's score
    private int level;  // Current level. For every 10 lines cleared level increases by 1
    private int lines;  // Number of lines cleared
    private Tile activeTile;  // Tile that is currently being controlled by the user
    private Tile holdTile;  // Tile currently held by user in the hold box
    private ArrayList<Tile> queue = new ArrayList<>();  // Tiles that are queued, displayed in the next box
    private Board board;  // Game board
    private int counter;  // Number of frames the game has progressed
    private int lastTile;  // Previously generated tile. Used to ensure a tile is not generated twice in a row
    private boolean swapped;  // Stores if current tile was swapped in as you are not allowed to swap out a tile you swapped in from hold
    private boolean tileStopped;  // Stores if current tile is stopped because it has hit a tile below
    private int stopTime;  // Time in frames that the tile was stopped at
    private boolean hardDropped;  // Indicates that a tile was hard dropped
    private boolean fullRow;  // Indicates that the dropped tile has created a full row that is to be cleared.
    private int rowTime;  // Time in frames that the row was made full at
    private ArrayList<Integer> rows;  // Arraylist of all rows that are full that need to be cleared
    private int comboCount;  // Counter for what the combo is at for scoring purposes
    private boolean lastClearTetris;  // Indicates that the last clear was a Tetris (4 lines cleared) for combo scoring purposes

    private Point mouse;  // Mouse position
    private Point offset;  // Position of window
    private Point oldMouse;  // Mouse position from previous frame. Used to check if mouse had moved
    private boolean mouseControls = true;  // Indicates that the set of controls being used are the mouse set

    // Sounds
    public final File dropTileSound = new File("Assets/dropTile.wav");
    public final File lineDropSound = new File("Assets/lineDrop.wav");
    public final File rotateTileSound = new File("Assets/rotateTile.wav");
    public final File loseSound = new File("Assets/lose.wav");
    public final File theme = new File("Assets/Theme.wav");

    // Audio clip players for sounds
    AudioClip sound1;
    AudioClip sound2;
    AudioClip sound3;
    AudioClip sound4;
    AudioClip music;

    // Loading music audio clip
    {
        try {
            music = Applet.newAudioClip(theme.toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    public GamePanel(Main m) {
        keys = new boolean[KeyEvent.KEY_LAST+1];
        keysDown = new boolean[KeyEvent.KEY_LAST+1];

        // Loading images
        back = new ImageIcon("Assets/background.jpg").getImage();
        hold = new ImageIcon("Assets/holdBox.PNG").getImage();
        scoreBoard = new ImageIcon("Assets/scoreBox.PNG").getImage();
        nextTiles = new ImageIcon("Assets/nextBox.PNG").getImage();
        boardImage = new ImageIcon("Assets/gameBox.jpg").getImage();

        // Setting panel
        mainFrame = m;
        setSize(1000, 770);

        // Adding action listeners
        addKeyListener(this);
        addMouseListener(this);

        board = new Board();

    }

    public void addNotify() {
        // Gets focus
        super.addNotify();
        requestFocus();
    }

    public void grabFocus() {
        // Gets focus
        addNotify();
    }


    public void mouseClicked(MouseEvent e) {
        mouseControls = true;  // Sets controls to mouse controls

        if (e.getButton() == MouseEvent.BUTTON1) {  // Left click
            int travelled = board.hardDrop(activeTile);  // Hard dropping the tile and keeping track of how many rows it travelled
            score += travelled * 2;  // Updating score
            hardDropped = true;
            try {
                sound1 = Applet.newAudioClip(dropTileSound.toURL());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //sound1.play();
        }
        else if (e.getButton() == MouseEvent.BUTTON3 && !swapped) {  // Right click. Holds current tile if it was not a tile that was swapped in
            hold();
        }

    }

    // Mouse listener methods that are not used but need to be included
    public void mousePressed(MouseEvent e) {

    }


    public void mouseReleased(MouseEvent e) {

    }


    public void mouseEntered(MouseEvent e) {

    }


    public void mouseExited(MouseEvent e) {

    }

    // Unused method
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

        if (keys[KeyEvent.VK_UP] && !keysDown[KeyEvent.VK_UP]) {
            keysDown[KeyEvent.VK_UP] = true;
            board.rotate(activeTile, Tile.RIGHT);
            try {
                sound2 = Applet.newAudioClip(rotateTileSound.toURL());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //sound2.play();
        }

        if (keys[KeyEvent.VK_SPACE] && !keysDown[KeyEvent.VK_SPACE]) {
            keysDown[KeyEvent.VK_SPACE] = true;
            int travelled = board.hardDrop(activeTile);
            score += travelled * 2;
            hardDropped = true;
            try {
                sound1 = Applet.newAudioClip(dropTileSound.toURL());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //sound1.play();
        }

        if (keys[KeyEvent.VK_C] && !keysDown[KeyEvent.VK_C] && !swapped) {
            hold();
        }

        if (keys[KeyEvent.VK_Z] && !keysDown[KeyEvent.VK_Z]) {
            board.rotate(activeTile, Tile.LEFT);
            try {
                sound2 = Applet.newAudioClip(rotateTileSound.toURL());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //sound2.play();
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

    public void init() {
        speed = 0;
        score = 0;
        level = 1;
        lines = 0;
        activeTile = null;
        holdTile = null;
        board = new Board();
        queue.clear();
        counter = 0;
        lastTile = 0;
        swapped = false;
        tileStopped = false;
        stopTime = 0;
        hardDropped = false;
        fullRow = false;
        rowTime = 0;
        rows = new ArrayList<>();
        comboCount = 0;
        lastClearTetris = false;

        mouse = null;
        offset = null;
        oldMouse = new Point(0, 0);
        mouseControls = true;
        counter = 0;

        started = true;
        music.loop();

        activeTile = generateTile();
        for (int i = 0; i < 3; i++) {
            queue.add(generateTile());
        }
        board.addTile(activeTile);

        mainFrame.start();

    }

    public void move() {
        if (started) {
            mouse = MouseInfo.getPointerInfo().getLocation();
            offset = getLocationOnScreen();
            mouseControls = !(mouse.x == oldMouse.x && mouse.y == oldMouse.y);
            oldMouse.x = mouse.x;
            oldMouse.y = mouse.y;
            //System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
            moveTile();
            counter++;
        }
    }

    public void moveTile() {
        speed = speedCurve[level];
        rows = board.getFullRows();


        if (mouseControls) {
            int pos = (mouse.x - offset.x - 302) / 35;
            while (board.canShiftRight(activeTile) && board.getTileX() < pos) {
                board.shiftRight(activeTile);
            }

            while (board.canShiftLeft(activeTile) && board.getTileX() > pos) {
                board.shiftLeft(activeTile);
            }
        }

        if (keys[KeyEvent.VK_DOWN]) {
            int CONTROL_DOWN_SPEED = 4;
            speed = Math.min(CONTROL_DOWN_SPEED, speedCurve[level]);
            mouseControls = false;
        }

        int CONTROL_SIDE_SPEED = 8;
        if (keys[KeyEvent.VK_RIGHT] && counter % CONTROL_SIDE_SPEED == 0) {
            boolean success = board.canShiftRight(activeTile);
            if (success) {
                board.shiftRight(activeTile);
                if (tileStopped) {
                    stopTime = counter;
                }
            }
        }
        if (keys[KeyEvent.VK_LEFT] && counter % CONTROL_SIDE_SPEED == 0) {
            boolean success = board.canShiftLeft(activeTile);
            if (success) {
                board.shiftLeft(activeTile);
                if (tileStopped) {
                    stopTime = counter;
                }
            }
        }

        boolean success = true;
        if (counter % speed == 0 || tileStopped) {
            success = board.canShiftDown(activeTile);
            if (success) {
                board.shiftDown(activeTile);
                if (keys[KeyEvent.VK_DOWN]) {
                    score += 1;
                }
            }
        }

        if (success) {
            board.ghostTile(activeTile);
            tileStopped = false;
        }
        else {
            if (!tileStopped && !fullRow) {
                tileStopped = true;
                stopTime = counter;
            }
        }

        if ((tileStopped && counter - stopTime == 24 || hardDropped) && !fullRow) {
            if (rows.size() > 0) {
                fullRow = true;
                rowTime = counter;
                tileStopped = false;
                comboCount++;
                try {
                    sound3 = Applet.newAudioClip(lineDropSound.toURL());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                //sound3.play();
            }
            else {
                comboCount = 0;
                lockTile();
                hardDropped = false;
                level = lines / 10 + 1;
                lastClearTetris = false;
            }
        }

        if (fullRow && counter - rowTime == 30) {
            clearTiles();
            lockTile();
            hardDropped = false;
            level = lines / 10 + 1;
            fullRow = false;
        }
    }

    public void clearTiles() {
        board.clearTiles(rows);
        lines += rows.size();

        switch (rows.size()) {
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
                if (lastClearTetris) {
                    score += 400 * level;
                }
                lastClearTetris = true;
                break;
        }
        if (rows.size() < 4) {
            lastClearTetris = false;
        }
        score += 50 * level * comboCount;
    }

    public void lockTile() {
        activeTile = queue.get(0);

        boolean canAddTile = board.addTile(activeTile);
        if (!canAddTile) {
            mainFrame.gameOver(score);
            started = false;
            try {
                sound4 = Applet.newAudioClip(loseSound.toURL());
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //sound4.play();
            music.stop();
            return;
        }

        queue.remove(0);
        queue.add(generateTile());
        if (swapped) {
            swapped = false;
        }
        tileStopped = false;
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

        // Draw hold tile
        drawPreviewTile(g, holdTile, 75, 115, 190, 125);

        // Draw queued tiles
        for (int i = 0; i < queue.size(); i++) {
            drawPreviewTile(g, queue.get(i), 740, 115 + 95 * i, 180, 100);
        }

        // Text
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            Font helvetica = new Font("Helvetica", Font.PLAIN, 36);
            FontMetrics fontMetrics = new JLabel().getFontMetrics(helvetica);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(helvetica);
            g2.setColor(Color.WHITE);

            int x, y;
            int width;

            // Score
            width = fontMetrics.stringWidth(String.valueOf(score));
            x = 75 + (185 - width) / 2 + 3;
            y = 457;
            g2.drawString(String.valueOf(score),x,y);

            // Level
            width = fontMetrics.stringWidth(String.valueOf(level));
            x = 75 + (185 - width) / 2 + 3;
            y = 550;
            g2.drawString(String.valueOf(level),x,y);


            // Lines
            width = fontMetrics.stringWidth(String.valueOf(lines));
            x = 75 + (185 - width) / 2 + 3;
            y = 645;
            g2.drawString(String.valueOf(lines),x,y);
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

    public void drawTiles(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        float alpha;

        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 2; j < board.getBoard()[0].length; j++) {
                if (i - board.getTileX() < 4 && i - board.getTileX() >= 0 && j - board.getTileY() < 4 && j - board.getTileY() >= 0
                    && board.getBoard()[i][j] == activeTile.getTile()[i-board.getTileX()][j-board.getTileY()] && tileStopped) {
                    alpha = Math.min(1, 1 / ((float) ((counter - stopTime) % 24)) + (float) 0.2);
                }
                else if (fullRow && rows.contains(j)) {
                    alpha = Math.min(1, 1 / ((float) ((counter - rowTime))));
                }
                else {
                    alpha = 1;
                }

                Image inImage = board.getBoard()[i][j] > 0 ? Tile.images[board.getBoard()[i][j]] : GhostTile.images[-board.getBoard()[i][j]];
                g2d.setComposite(AlphaComposite.SrcOver.derive(alpha));
                int x = 306 + 35 * i;
                int y = 23 + 35 * (j-2);
                g2d.drawImage(inImage, x, y, this);

            }
        }
    }

    public void paintComponent(Graphics g) {
        drawUI(g);
        drawTiles(g);
    }

}