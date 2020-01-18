/*
    Menu.java
    Nick Liu + Zihan Dong
    ICS4U-01
    File containing all of the menu pages with a class for each page. These classes are used by the Main class.
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class Menu extends JPanel {
    // Basic UI images used by most menu pages
    private final Image back = new ImageIcon("Assets/background.jpg").getImage();
    private final Image hold = new ImageIcon("Assets/holdBox.PNG").getImage();
    private final Image scoreBoard = new ImageIcon("Assets/scoreBox.PNG").getImage();
    private final Image nextTiles = new ImageIcon("Assets/nextBox.PNG").getImage();
    private final Image boardImage = new ImageIcon("Assets/menucentre.jpg").getImage();
    private final Image tetrisLogo = new ImageIcon("Assets/logo.PNG").getImage();

    @Override
    public void addNotify() {
        /*
            Gets focus of panel
         */
        super.addNotify();
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        /*
            Paints the stuff to be drawn. Called automatically
         */
        drawBasicUI(g);
        g.drawImage(tetrisLogo, 322, 40, null);
    }

    public void drawBasicUI(Graphics g) {
        /*
            Draws the basic UI
         */
        g.drawImage(back,0,0,null);
        g.drawImage(hold, 60, 55, null);
        g.drawImage(scoreBoard, 60, 360, null);
        g.drawImage(nextTiles, 720, 55, null);
        g.drawImage(boardImage, 300, 20, null);
    }
}

class HighScores extends Menu {
    private ArrayList<Integer> scores = new ArrayList<>();  // High scores list
    private ArrayList<String> names = new ArrayList<>();  // Names of scores list

    private final Image grid = new ImageIcon("Assets/highScores.png").getImage();


    @Override
    public void paintComponent(Graphics g) {
        drawBasicUI(g);
        g.drawImage(grid, 317, 50, null);

        if (g instanceof Graphics2D) {
            printScores(g);
        }
    }

    public void readFromFile(String file) throws IOException {
        /*
            Reads the high scores from the text file and retrieves the information.
         */
        // Resetting the list of names and scores
        names = new ArrayList<>();
        scores = new ArrayList<>();
        Scanner stdin = new Scanner(new BufferedReader(new FileReader(file)));
        while (stdin.hasNextLine()) {
            String[] line = stdin.nextLine().split(",");  // File in the form name,score; splitting gets the components
            names.add(line[0]);
            scores.add(Integer.parseInt(line[1]));
        }

        stdin.close();
    }

    public void writeToFile(String file) throws IOException {
        /*
            Writes the high scores to the text file to be stored.
         */
        PrintWriter stdout = new PrintWriter(new BufferedWriter (new FileWriter (file)));
        for (int i = 0; i < Math.min(10, scores.size()); i++) {  // Only top 10 scores are written. Also possibility there are less than 10 so Math.min is done
            String s = names.get(i);
            if (s.length() > 12) {  // Max length of name is 12 characters
                s = s.substring(0, 12);
            }
            stdout.println(s + "," + scores.get(i));  // Print data in form name,score
        }
        stdout.close();
    }


    public void printScores(Graphics g) {
        /*
            Outputs the names and high scores to the high scores page.
         */
        Graphics2D g2 = (Graphics2D) g;

        // Font
        Font helvetica24 = new Font("Helvetica", Font.PLAIN, 24);
        FontMetrics fontMetrics = new JLabel().getFontMetrics(helvetica24);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setFont(helvetica24);
        g2.setColor(Color.WHITE);

        // Drawing the text
        for (int i = 0; i < names.size(); i++) {
            g2.drawString(names.get(i), 343, 143 + 38*i);
            int width = fontMetrics.stringWidth(String.valueOf(scores.get(i)));
            g2.drawString(String.valueOf(scores.get(i)), 619 - width, 143 + 38*i);
        }

    }

    public void addScore(String name, int score) {
        /*
            Add a new name score combination to the high score list.
         */
        for (int i = 0; i < scores.size(); i++) {  // Searches the list until it finds one smaller than the new score and inserts it
            if (score > scores.get(i)) {
                scores.add(i, score);
                names.add(i, name);
                return;
            }
        }
        if (scores.size() < 10) {  // If there are less than 10 scores and it is not larger than any of the scores add to end
            scores.add(score);
            names.add(name);
        }
    }



}

class GameOver extends Menu {
    private final Image gameOverText = new ImageIcon("Assets/gameOver.png").getImage();
    private int score;  // Player score

    @Override
    public void paintComponent(Graphics g) {
        drawBasicUI(g);
        g.drawImage(gameOverText, 327, 50, null);

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) g;

            // Font
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
    private final Image[] images = {  // How to play images
        new ImageIcon("Assets/howtoplay1.png").getImage(),
        new ImageIcon("Assets/howtoplay2.png").getImage(),
        new ImageIcon("Assets/howtoplay3.png").getImage()
    };

    private final Image image;  // Image to be displayed

    public HowToPlay(int page) {
        this.image = images[page-1];  // Set image to corresponding page
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(image, 0, 0, null);

    }

}

