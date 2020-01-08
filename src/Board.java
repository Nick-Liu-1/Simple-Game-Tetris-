import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int[][] board = new int[10][22];
    private int tileX, tileY;

    public Board() {

    }

    public boolean shiftDown(Tile tile) {
        //System.out.println(Arrays.deepToString(board));
        boolean valid = true;
        for (int j = 3; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (tile.getTile()[i][j] != 0) {
                    if (tileY + j + 1 >= 22 || (board[tileX + i][tileY + j + 1] != 0 && j+1 < 3 && tile.getTile()[i][j+1] == 0)) {
                        valid = false;
                    }
                }
            }
        }
        if (valid) {
            for (int j = 3; j >= 0; j--) {
                for (int i = 0; i < 4; i++) {
                    if (tile.getTile()[i][j] != 0) {
                        board[tileX + i][tileY + j + 1] = board[tileX + i][tileY + j];
                        board[tileX + i][tileY + j] = 0;
                    }
                }
            }
            tileY++;
        }
        return valid;
    }

    public boolean shiftLeft(Tile tile) {
        boolean valid = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    if (tileX + i - 1 < 0 || (i != 0 && board[tileX + i - 1][tileY + j] != 0 && tile.getTile()[i-1][j] == 0)) {
                        valid = false;
                    }
                }
            }
        }
        if (valid) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (tile.getTile()[i][j] != 0) {
                        board[tileX + i - 1][tileY + j] = board[tileX + i][tileY + j];
                        board[tileX + i][tileY + j] = 0;
                    }
                }
            }
            if (tileX > 0) {
                tileX--;
            }
        }
        return valid;
    }

    public boolean shiftRight(Tile tile) {
        boolean valid = true;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    if (tileX + i + 1 >= 10 || (i != 3 && board[tileX + i + 1][tileY + j] != 0 && tile.getTile()[i+1][j] == 0)) {
                        valid = false;
                    }
                }
            }
        }
        if (valid) {
            for (int i = 3; i >= 0; i--) {
                for (int j = 0; j < 4; j++) {
                    if (tile.getTile()[i][j] != 0) {
                        board[tileX + i + 1][tileY + j] = board[tileX + i][tileY + j];
                        board[tileX + i][tileY + j] = 0;
                    }
                }
            }
            tileX++;

        }
        return valid;
    }

    public void update(Tile tile) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                //System.out.println((tileX+i) + " " + (tileY + j));
                if (tileX + i < 10 && tileY + j < 22) {
                    board[tileX + i][tileY + j] = tile.getTile()[i][j];
                }
            }
        }
    }

    public void addTile(Tile tile) {
        tileX = 4;
        tileY = 0;
        for (int i = 4; i < 8; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = tile.getTile()[i-4][j];
            }
        }
    }

    public void drawTiles(Graphics g) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 2; j < board[0].length; j++) {
                if (board[i][j] != 0 && j >= 2) {
                    g.drawImage(Tile.images[board[i][j]], 306 + 35 * i, 23 + 35 * (j-2), null);
                }
            }
        }
    }
}
