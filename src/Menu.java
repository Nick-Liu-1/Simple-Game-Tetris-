import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class Menu extends JPanel {
    final Image back = new ImageIcon("Assets/background.jpg").getImage();
    final Image hold = new ImageIcon("Assets/holdBox.PNG").getImage();
    final Image scoreBoard = new ImageIcon("Assets/scoreBox.PNG").getImage();
    final Image nextTiles = new ImageIcon("Assets/nextBox.PNG").getImage();
    final Image boardImage = new ImageIcon("Assets/menucentre.jpg").getImage();
    final Image tetrisLogo = new ImageIcon("Assets/logo.PNG").getImage();

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawBasicUI(g);
        g.drawImage(tetrisLogo, 322, 40, null);
    }

    public void drawBasicUI(Graphics g) {
        g.drawImage(back,0,0,null);
        g.drawImage(hold, 60, 55, null);
        g.drawImage(scoreBoard, 60, 360, null);
        g.drawImage(nextTiles, 720, 55, null);
        g.drawImage(boardImage, 300, 20, null);
    }
}

class HighScores extends Menu {
    private ArrayList<Integer> scores = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();

    private final Image grid = new ImageIcon("Assets/highScores.png").getImage();

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawBasicUI(g);
        g.drawImage(grid, 317, 50, null);

        if (g instanceof Graphics2D) {
            printScores(g);
        }
    }

    public void readFromFile(String file) throws IOException {
        names = new ArrayList<>();
        scores = new ArrayList<>();
        Scanner stdin = new Scanner(new BufferedReader(new FileReader(file)));
        while (stdin.hasNextLine()) {
            String[] line = stdin.nextLine().split(",");
            names.add(line[0]);
            scores.add(Integer.parseInt(line[1]));
        }
        stdin.close();
    }

    public void writeToFile(String file) throws IOException {
        PrintWriter stdout = new PrintWriter(new BufferedWriter (new FileWriter (file)));
        for (int i = 0; i < Math.min(10, scores.size()); i++) {
            stdout.println(names.get(i) + "," + scores.get(i));
        }
        stdout.close();
    }


    public void printScores(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Font helvetica24 = new Font("Helvetica", Font.PLAIN, 24);
        FontMetrics fontMetrics = new JLabel().getFontMetrics(helvetica24);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(helvetica24);
        g2.setColor(Color.WHITE);

        for (int i = 0; i < names.size(); i++) {
            g2.drawString(names.get(i), 343, 143 + 38*i);

            int width = fontMetrics.stringWidth(String.valueOf(scores.get(i)));
            g2.drawString(String.valueOf(scores.get(i)), 619 - width, 143 + 38*i);
        }

    }

    public void addScore(String name, int score) {
        for (int i = 0; i < scores.size(); i++) {
            if (score > scores.get(i)) {
                scores.add(i, score);
                names.add(i, name);
                break;
            }
        }
    }



}

class GameOver extends Menu {
    private final Image gameOverText = new ImageIcon("Assets/gameOver.png").getImage();
    private int score;

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawBasicUI(g);
        g.drawImage(gameOverText, 327, 50, null);

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            Font helvetica = new Font("Helvetica", Font.PLAIN, 45);
            FontMetrics fontMetrics = new JLabel().getFontMetrics(helvetica);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(helvetica);
            g2.setColor(Color.WHITE);

            // Game over title
            int width = fontMetrics.stringWidth("SCORE");
            int x = 305 + (350 - width) / 2;
            int y = 215;
            g2.drawString("SCORE", x, y);

            // Score
            width = fontMetrics.stringWidth(String.valueOf(score));
            x = 305 + (350 - width) / 2;
            y = 265;
            g2.drawString(String.valueOf(score), x, y);

            // Enter a name
            width = fontMetrics.stringWidth("ENTER A NAME");
            x = 305 + (350 - width) / 2;
            y = 380;
            g2.drawString("ENTER A NAME", x, y);

        }
    }

    public void setScore(int score) {
        this.score = score;
    }
}

class HowToPlay extends Menu {
    private final Image[] images = {
        new ImageIcon("Assets/howtoplay1.png").getImage(),
        new ImageIcon("Assets/howtoplay2.png").getImage(),
        new ImageIcon("Assets/howtoplay3.png").getImage()
    };

    private final Image image;
    private final int page;

    public HowToPlay(int page) {
        this.page = page;
        this.image = images[page-1];
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);

    }
}

class Settings extends Menu {
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawBasicUI(g);

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;
            Font helvetica = new Font("Helvetica", Font.PLAIN, 45);
            Font helvetica24 = new Font("Helvetica", Font.PLAIN, 24);
            FontMetrics fontMetrics = new JLabel().getFontMetrics(helvetica);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(helvetica);
            g2.setColor(Color.WHITE);

            // Game over title
            int width = fontMetrics.stringWidth("OPTIONS");
            int x = 305 + (350 - width) / 2;
            int y = 80;
            g2.drawString("OPTIONS", x, y);

            g2.setFont(helvetica24);
            x = 315;
            y = 180;
            g2.drawString("SHOW GHOSTPIECE", x, y);




        }

    }
}
