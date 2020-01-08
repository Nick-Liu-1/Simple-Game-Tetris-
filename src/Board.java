import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private int[][] board = new int[10][22];
    private int tileX, tileY;

    public Board() {
        /*for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = Main.randint(1, 7);
            }
        }*/
    }

    public void shiftDown(Tile tile) {
        //System.out.println(Arrays.deepToString(board));
        boolean valid = true;
        for (int j = 3; j >= 0; j--) {
            for (int i = 0; i < 3; i++) {
                if (tile.getTile()[i][j] != 0) {
                    if (tileY + j + 1 >= 22 || (board[tileX + i][tileY + j + 1] != 0 && (j+1 < 3 && tile.getTile()[i][j+1] == 0))) {
                        valid = false;
                    }
                }
            }
        }
        if (valid) {
            for (int j = 3; j >= 0; j--) {
                for (int i = 0; i < 3; i++) {
                    if (tile.getTile()[i][j] != 0) {
                        if (tileY + j + 1 < 22 && board[tileX + i][tileY + j + 1] == 0) {
                            board[tileX + i][tileY + j + 1] = board[tileX + i][tileY + j];
                            board[tileX + i][tileY + j] = 0;
                        }
                    }
                }
            }
            tileY++;
        }

    }

    public void shiftLeft(Tile tile) {

    }

    public void shiftRight(Tile tile) {

    }

    public void update() {

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
                if (board[i][j] != 0) {
                    g.drawImage(Tile.images[board[i][j]], 302 + 35 * i, 22 + 35 * (j-2), null);
                }
            }
        }
    }
}
