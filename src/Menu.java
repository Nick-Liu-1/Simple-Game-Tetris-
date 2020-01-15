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

    private final Image highScoresText = new ImageIcon("Assets/highScores.png").getImage();

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        drawBasicUI(g);

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
    }

    public void printScores(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Font helvetica24 = new Font("Helvetica", Font.PLAIN, 24);
        FontMetrics fontMetrics = new JLabel().getFontMetrics(helvetica24);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(helvetica24);
        g2.setColor(Color.WHITE);

        for (int i = 0; i < names.size(); i++) {
            g2.drawString(names.get(i), 315, 150 + 40*i);

            int width = fontMetrics.stringWidth(String.valueOf(scores.get(i)));
            g2.drawString(String.valueOf(scores.get(i)), 645 - width, 150 + 40*i);
        }

    }

    public void addScore(String name, int score) {

    }
}

class GameOver extends Menu {
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
            Font helvetica = new Font("Helvetica", Font.PLAIN, 48);
            FontMetrics fontMetrics = new JLabel().getFontMetrics(helvetica);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(helvetica);
            g2.setColor(Color.WHITE);

            // Game over title
            int width = fontMetrics.stringWidth("GAME OVER");
            int x = 305 + (350 - width) / 2;
            int y = 115;
            g2.drawString("GAME OVER", x, y);

        }
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
    }
}