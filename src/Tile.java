import javax.swing.*;
import java.awt.*;
import java.util.*;

public class Tile {
    private int[][] tile;
    public final int id;
    private int orientation = 0;
    private int size;
    private int x, y;
    public static final int[] sizes = { 0, 4, 3, 3, 2, 3, 3, 3 };
    public static final Image[] images = {
        null,
        new ImageIcon("Assets/CyanBlock.png").getImage(),
        new ImageIcon("Assets/BlueBlock.png").getImage(),
        new ImageIcon("Assets/OrangeBlock.png").getImage(),
        new ImageIcon("Assets/YellowBlock.png").getImage(),
        new ImageIcon("Assets/GreenBlock.png").getImage(),
        new ImageIcon("Assets/PurpleBlock.png").getImage(),
        new ImageIcon("Assets/RedBlock.png").getImage()
    };

    private final int[][][] defaultTiles = {
        null,
        {
            {0,0,0,0},
            {1,1,1,1},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {2,0,0,0},
            {2,2,2,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {0,0,3,0},
            {3,3,3,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {4,4,0,0},
            {4,4,0,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {0,5,5,0},
            {5,5,0,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {0,6,0,0},
            {6,6,6,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {7,7,0,0},
            {0,7,7,0},
            {0,0,0,0},
            {0,0,0,0}
        }
    };

    public void rotate() {
        orientation = (orientation + 1) % 4;
        int[][] temp = {{0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0}};

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp[j][size-1-i] = tile[i][j];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tile[i][j] = temp[i][j];
            }
        }
    }

    public Tile(int choice) {
        id = choice;
        tile = defaultTiles[id];
        size = sizes[id];
    }

    public int[][] getTile() {
        return tile;
    }
}