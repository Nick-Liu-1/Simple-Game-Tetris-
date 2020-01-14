import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel {
    private final Image menuBack = new ImageIcon("Assets/menuback.png").getImage();

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(menuBack, 0, 0, null);
    }
}

class HighScores extends JPanel {

}