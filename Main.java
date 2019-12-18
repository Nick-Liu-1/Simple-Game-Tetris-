import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Main extends JFrame {
  public Main() {
		super("Tetris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setLayout(new BorderLayout());

		setVisible(true);
  }

  public static void main(String[] args) {
    Main frame = new Main();
  }
}