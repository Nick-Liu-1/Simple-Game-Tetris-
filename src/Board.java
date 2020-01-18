import java.util.ArrayList;
import java.util.*;

public class Board {
    private int[][] board = new int[10][22];  // 0 is empty, id for whichever tile occupies it
    private int tileX, tileY;  // Position of active tile

    public boolean canShiftDown(Tile tile) {
        /*
            Checks if the tile can shift down by looking at every cell in the tile and seeing what is below it. If the
            cell below is not empty and not part of the tile then it cannot shift down.
         */

        // Iterating through to check validity
        for (int j = 3; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (tile.getTile()[i][j] != 0) {
                    //  Bottom of board         cell below is occupied                  cell below is not part of tile
                    if (tileY + j + 1 >= 22 || (board[tileX + i][tileY + j + 1] > 0 && (j == 3 || (j < 3 && tile.getTile()[i][j+1] == 0)))) {
                        return false;
                    }
                }
            }
        }
        return true;  // If none are invalid then return valid
    }

    private boolean canShiftDown(GhostTile tile, int x, int y) {
        /*
            Checks if the tile can shift down by looking at every cell in the tile and seeing what is below it. If the
            cell below is not empty and not part of the tile then it cannot shift down.
         */

        // Iterating through
        for (int j = 3; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (tile.getTile()[i][j] != 0) {
                    if (y + j + 1 >= 22 || (board[x + i][y + j + 1] > 0 && (j == 3 || (j < 3 && tile.getTile()[i][j+1] == 0)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void shiftDown(Tile tile) {
        /*
            Shift the tile 1 row down. Does so by iterating through the tile and shifting each cell down.
         */

        // Iterating through
        for (int j = 3; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (tile.getTile()[i][j] != 0) {
                    board[tileX + i][tileY + j + 1] = board[tileX + i][tileY + j]; // Setting cell below to be current cell
                    board[tileX + i][tileY + j] = 0;  // Set current cell to be empty as the cell has shifted down
                }
            }
        }
        tileY++;  // Increase the Y coordinate

    }

    private void shiftDown(int row) {
        /*
            Shifts down all rows above specified row for line clearing purposes.
         */

        // Iterating through rows
        for (int j = row - 1; j >= 0; j--) {
            for (int i = 0; i < board.length; i++) {  // Shifting every cell in row down
                board[i][j+1] = board[i][j];
                board[i][j] = 0;
            }
        }
    }

    public ArrayList<Integer> getFullRows() {
        /*
            Returns ArrayList of all rows that are full to be cleared.
         */
        ArrayList<Integer> fullRows = new ArrayList<>();

        // Iterating through all rows
        for (int j = board[0].length - 1; j >= 0; j--) {
            boolean full = true;
            // Iterating through each element in row
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] == 0) {  // If one element is empty row is not full
                    full = false;
                }
            }

            // Add row to ArrayList
            if (full) {
                fullRows.add(j);
            }
        }
        return fullRows;
    }

    public void clearTiles(ArrayList<Integer> rows) {
        /*
            Shifts down all of the tiles for all of the rows starting from the top and going down.
         */
        Collections.sort(rows);
        for (int row : rows) {
            shiftDown(row);
        }
    }
    public boolean canShiftLeft(Tile tile) {
        /*
            Checks if tile can shift left by seeing if doing so will not cause tile to go off the board or into an
            occupied cell. Iterates through each cell and checks cell to left. If any is invalid it returns invalid.
         */
        boolean valid = true;

        // Iterating through cells
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    //  off board             left cell is occupied                                                            left cell is occupied check for leftmost column
                    if (tileX + i - 1 < 0 || (i > 0 && board[tileX + i - 1][tileY + j] > 0 && tile.getTile()[i-1][j] == 0) || (i == 0 && board[tileX + i - 1][tileY + j] > 0)) {
                        valid = false;
                    }
                }
            }
        }
        return valid;
    }


    public void shiftLeft(Tile tile) {
        /*
            Shifts all cells of the tile one column left by iterating through each cell.
         */

        // Iterating through
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    board[tileX + i - 1][tileY + j] = board[tileX + i][tileY + j];  // Setting left tile to current
                    board[tileX + i][tileY + j] = 0;  // Setting current to empty
                }
            }
        }
        tileX--;  // Decreasing x position of tile
    }

    public boolean canShiftRight(Tile tile) {
        /*
            Checks if tile can shift right by seeing if doing so will not cause tile to go off the board or into an
            occupied cell. Iterates through each cell and checks cell to left. If any is invalid it returns invalid.
         */
        boolean valid = true;

        // Iterating through cells
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    // off board               right cell occupied                                                                right cell occupied test for rightmost column
                    if (tileX + i + 1 >= 10 || (i != 3 && board[tileX + i + 1][tileY + j] > 0 && tile.getTile()[i+1][j] == 0) || (i == 3 && board[tileX + i + 1][tileY + j] > 0)) {
                        valid = false;
                    }
                }
            }
        }
        return valid;
    }

    public void shiftRight(Tile tile) {
        /*
            Shifts all cells of the tile one column right by iterating through each cell.
         */

        // Iterating through
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    board[tileX + i + 1][tileY + j] = board[tileX + i][tileY + j];  // Setting tile to the right to current
                    board[tileX + i][tileY + j] = 0; // Set current to empty
                }
            }
        }
        tileX++;  // Updating the x position of tile
    }

    public void rotate(Tile tile, int dir) {
        /*
            Checks if tile can be rotate by seeing if the rotation will cause part of the tile to go off board
            or into an occupied cell. If it can be rotated then the tile is rotated.
         */
        boolean valid = true;

        // Iterating through
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Set current tile cells to empty
                if (tileX + i < 10 && tileY + j < 22 && tile.getTile()[i][j] != 0) {
                    board[tileX + i][tileY + j] = 0;
                }
                if (Tile.rotated(tile, dir)[i][j] != 0) {  // Checking validity
                    // off left side     off right side    off the bottom      cell already occupied
                    if (tileX + i < 0 || tileX + i >= 10 || tileY + j >= 22 || board[tileX+i][tileY+j] > 0) {
                        valid = false;

                    }
                }
            }
        }

        if (valid)  {  // rotate tile if valid rotation van be made
            tile.rotate(dir);
        }

        // Iterating through tile cells to put them back in
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tileX + i < 10 && tileY + j < 22 && tile.getTile()[i][j] > 0) {
                    board[tileX + i][tileY + j] = tile.getTile()[i][j];
                }
            }
        }
    }

    public int hardDrop(Tile tile) {
        /*
            Shifts tile down as much as possible. Returns number of rows travelled for scoring purposes.
         */
        int count = 0;
        while (canShiftDown(tile)) {
            shiftDown(tile);
            count++;
        }
        return count;
    }

    private void hardDrop(GhostTile tile) {
        /*
            Shifts GhostTile down as much as possible.
         */

        // Starting position of GhostTile
        int x = tileX;
        int y = tileY;

        // Removing all GhostTiles from last method call
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] < 0) {
                    board[i][j] = 0;
                }
            }
        }

        // Shift tile down
        boolean success = canShiftDown(tile, x, y);
        while (success) {
            y++;
            success = canShiftDown(tile, x, y);
        }

        // Adding the cells of the GhostTile to the board
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // off left     off bottom      cell exists in tile         cell not occupied
                if (x + i < 10 && y + j < 22 && tile.getTile()[i][j] != 0 && board[x+i][y+j] == 0) { // Check validity
                    board[x + i][y + j] = tile.getId();
                }
            }
        }
    }

    public void ghostTile(Tile tile) {
        /*
            Adds a GhostTile to the board and hard drops it.
         */
        GhostTile ghost = new GhostTile(-tile.getId(), tile.getOrientation());
        hardDrop(ghost);
    }

    public boolean addTile(Tile tile) {
        /*
            Spawns a new tile. Returns true if successful.
         */

        // Spawn position
        tileX = 4;
        tileY = 0;

        // Iterating through to set the cells to the tile's id
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                // Checking validity to see if no cells are occupied that are needed for the spawn
                if (tile.getTile()[i-4][j] != 0 && board[i][j] != 0) {
                    return false;
                }
                if (board[i][j] == 0) {  // Setting board cells to the spawned tile
                    board[i][j] = tile.getTile()[i-4][j];
                }

            }
        }
        return true;
    }

    public void removeTile(Tile tile) {
        /*
            Removes active tile from board by iterating through and setting all of its cells to empty.
         */
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    board[tileX + i][tileY + j] = 0;
                }
            }
        }
    }


    // Getters
    public int[][] getBoard() {
        return board;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

}
