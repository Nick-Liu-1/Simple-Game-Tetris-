import java.util.*;

public class Tile {
    private int[][] tile;
    private int tileName;
    private int orientation = 0;
    private int size;
    private int[] pos;
    private final int[] sizes = { 4, 3, 3, 2, 3, 3, 3 };
    private final int[][][] defaultTiles = {
        {
            {0,0,0,0},
            {1,1,1,1},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {1,0,0,0},
            {1,1,1,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {0,0,1,0},
            {1,1,1,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {1,1,0,0},
            {1,1,0,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {0,1,1,0},
            {1,1,0,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {0,1,0,0},
            {1,1,1,0},
            {0,0,0,0},
            {0,0,0,0}
        },

        {
            {1,1,0,0},
            {0,1,1,0},
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
        tile = defaultTiles[choice];
        size = sizes[choice];
    }
}