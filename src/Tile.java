/*
    Tile.java
    Nick Liu + Zihan Dong
    ICS4U-01
    File containing the Tile and Ghost tile classes. These are the tiles used in the game and help store the information
    for them to be used by other classes as objects.
 */


import javax.swing.*;
import java.awt.*;


public class Tile {
    private int[][] tile;  // 2D array representing the tile
    public final int id;  // Which piece the tile is
    private int orientation = 0;  // Which rotation the tile is in
    private int size;  // Length of longest side
    private Image image;  // Image to be displayed on the board
    private final int[][] defaultTile;  // Default rotation
    public static final int[] sizes = { 0, 4, 3, 3, 2, 3, 3, 3 };  // Sizes for the default pieces
    public static final int RIGHT = 1;
    public static final int LEFT = 2;
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


    private final int[][][] defaultTiles = {  // The default tile pieces represented as 2D arrays
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


    public void rotate(int dir) {
        /*
            Rotates the tile 90 degrees in the specified direction by rotating the portion of the array that the tile
            can exist in. Only rotates the size x size sub array to rotate the piece.
         */
        if (dir == RIGHT) {  // Rotate right
            orientation = (orientation + 1) % 4;  // Updating orientation
            int[][] temp = {{0,0,0,0},  // Temporary array to do the rotation
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}};

            // Iterating through tile array and setting the temporary array to the rotated version
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    temp[j][size-1-i] = tile[i][j];
                }
            }

            // Copying the temporary rotated array back to the tile array
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    tile[i][j] = temp[i][j];
                }
            }
        }
        else {  // Rotate Left
            orientation = (orientation + 4 - 1) % 4;  // Updating orientation
            int[][] temp = {{0,0,0,0},  // Temporary array to do the rotation
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}};

            // Iterating through tile array and setting the temporary array to the rotated version
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    temp[size-1-j][i] = tile[i][j];
                }
            }

            // Copying the temporary rotated array back to the tile array
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    tile[i][j] = temp[i][j];
                }
            }
        }

    }

    public static int[][] rotated(Tile tile, int dir) {
        /*
            Produces a copy of the rotated version of the tile array but does not actually rotate it.
         */
        if (dir == RIGHT) {  // Rotate right
            int[][] temp = {{0,0,0,0},  // Array to be outputted
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}};

            // Rotating the array
            for (int i = 0; i < tile.size; i++) {
                for (int j = 0; j < tile.size; j++) {
                    temp[j][tile.size-1-i] = tile.tile[i][j];
                }
            }

            return temp;
        }
        else {  // Rotate left
            int[][] temp = {{0,0,0,0},  // Array to be outputted
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0}};

            // Rotating the array
            for (int i = 0; i < tile.size; i++) {
                for (int j = 0; j < tile.size; j++) {
                    temp[tile.size-1-j][i] = tile.tile[i][j];
                }
            }

            return temp;
        }
    }

    public void resetToDefault() {
        /*
            Set tile orientation to the default orientation.
         */
        tile = deepCopy(defaultTile);
        orientation = 0;
    }

    public int[][] deepCopy(int[][] arr) {
        /*
            Copies a 2D array by iterating through the array.
         */
        int[][] temp = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                temp[i][j] = arr[i][j];
            }
        }
        return temp;

    }

    public Tile(int choice, int orientation) {
        id = choice;  // Choosing the corresponding
        defaultTile = defaultTiles[Math.abs(id)];  // Setting the default tile. Math.abs because the ghost tile ids are negative
        tile = deepCopy(defaultTile);
        size = sizes[Math.abs(id)];  // Setting size
        image = images[Math.abs(id)];  // Setting image
        for (int i = 0; i < orientation; i++) {  // Rotating tile to the specified orientation
            this.rotate(RIGHT);
        }
    }

    // Getters
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

/*
    Class for the ghost tiles. Behaves almost identically to normal tiles, with the difference being the images
    displayed and the ids for them. The negative ids tell the board class to not include them in the collision
    calculations.
 */

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
    public Image getImage() {  // Overriding the getImage to get the GhostTile image
        return image;
    }

    public GhostTile(int choice, int orientation) {
        super(choice, orientation);  // Constructing the parent class
        image = images[Math.abs(id)];  // Setting the image
    }
}