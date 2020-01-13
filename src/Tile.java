import javax.swing.*;
import java.awt.*;
import java.util.Arrays;


public class Tile {
    private int[][] tile;
    public final int id;
    private int orientation = 0;
    private int size;
    private Image image;
    private final int[][] defaultTile;
    public static final int[] sizes = { 0, 4, 3, 3, 2, 3, 3, 3 };
    public static final Image[] images = {
        null,
        new ImageIcon("Assets/CyanBlock.png").getImage(),
        new ImageIcon("Assets/BlueBlock.png").getImage(),
        new ImageIcon("Assets/OrangeBlock.png").getImage(),
        new ImageIcon("Assets/YellowBlock.png").getImage(),
        new ImageIcon("Assets/GreenBlock.png").getImage(),
        new ImageIcon("Assets/PurpleBlock.png").getImage(),
        new ImageIcon("Assets/RedBlock.png").getImage(),
    };

    private final int[][][] defaultTiles = {
        null,
        {
            {0,1,0,0},
            {0,1,0,0},
            {0,1,0,0},
            {0,1,0,0}
        },

        {
            {2,2,0,0},
            {0,2,0,0},
            {0,2,0,0},
            {0,0,0,0}
        },

        {
            {0,3,0,0},
            {0,3,0,0},
            {3,3,0,0},
            {0,0,0,0}
        },

        {
            {4,4,0,0},
            {4,4,0,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {0,5,0,0},
            {5,5,0,0},
            {5,0,0,0},
            {0,0,0,0}
        },

        {
            {0,6,0,0},
            {6,6,0,0},
            {0,6,0,0},
            {0,0,0,0}
        },

        {
            {7,0,0,0},
            {7,7,0,0},
            {0,7,0,0},
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

    public static int[][] rotated(Tile tile) {
        int[][] temp = {{0,0,0,0},
            {0,0,0,0},
            {0,0,0,0},
            {0,0,0,0}};

        for (int i = 0; i < tile.size; i++) {
            for (int j = 0; j < tile.size; j++) {
                temp[j][tile.size-1-i] = tile.tile[i][j];
            }
        }

        return temp;
    }

    public void resetToDefault() {
        tile = deepCopy(defaultTile);
        orientation = 0;
    }

    public int[][] deepCopy(int[][] arr) {
        int[][] temp = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                temp[i][j] = arr[i][j];
            }
        }
        return temp;

    }

    public Tile(int choice, int orientation) {
        id = choice;
        defaultTile = defaultTiles[Math.abs(id)];
        tile = deepCopy(defaultTile);
        size = sizes[Math.abs(id)];
        image = images[Math.abs(id)];
        for (int i = 0; i < orientation; i++) {
            this.rotate();
        }
    }

    public int[][] getTile() {
        return tile;
    }

    public int getId() {
        return id;
    }

    public int getOrientation() {
        return orientation;
    }

    public Image getImage() {
        return image;
    }

    public int getSize() {
        return size;
    }


}

class GhostTile extends Tile {
    private Image image;
    public static final Image[] images = {
        null,
        new ImageIcon("Assets/cyansquare.png").getImage(),
        new ImageIcon("Assets/bluesquare.png").getImage(),
        new ImageIcon("Assets/orangesquare.png").getImage(),
        new ImageIcon("Assets/yellowsquare.png").getImage(),
        new ImageIcon("Assets/greensquare.png").getImage(),
        new ImageIcon("Assets/purplesquare.png").getImage(),
        new ImageIcon("Assets/redsquare.png").getImage()
    };

    @Override
    public Image getImage() {
        return image;
    }

    public GhostTile(int choice, int orientation) {
        super(choice, orientation);
        image = images[Math.abs(id)];
    }
}