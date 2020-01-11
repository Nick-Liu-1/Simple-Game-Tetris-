import java.awt.*;
import java.util.ArrayList;
import java.util.*;

public class Board {
    private int[][] board = new int[10][22];
    private int tileX, tileY;

    public boolean shiftDown(Tile tile) {
        for (int j = 3; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (tile.getTile()[i][j] != 0) {
                    if (tileY + j + 1 >= 22 || (board[tileX + i][tileY + j + 1] > 0 && (j == 3 || (j < 3 && tile.getTile()[i][j+1] == 0)))) {
                        return false;
                    }
                }
            }
        }

        for (int j = 3; j >= 0; j--) {
            for (int i = 0; i < 4; i++) {
                if (tile.getTile()[i][j] != 0) {
                    board[tileX + i][tileY + j + 1] = board[tileX + i][tileY + j];
                    board[tileX + i][tileY + j] = 0;
                }
            }
        }
        tileY++;

        return true;
    }

    public boolean shiftDown(GhostTile tile, int x, int y) {
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

    public void shiftDown(int row) {
        for (int j = row - 1; j >= 0; j--) {
            for (int i = 0; i < board.length; i++) {
                board[i][j+1] = board[i][j];
                board[i][j] = 0;
            }
        }
    }

    public ArrayList<Integer> getFullRows() {
        ArrayList<Integer> fullRows = new ArrayList<>();
        for (int j = board[0].length - 1; j >= 0; j--) {
            boolean full = true;
            for (int i = 0; i < board.length; i++) {
                if (board[i][j] == 0) {
                    full = false;
                }
            }
            if (full) {
                fullRows.add(j);
            }
        }
        return fullRows;
    }

    public void clearTiles(ArrayList<Integer> rows) {
        Collections.sort(rows);
        for (int row : rows) {
            shiftDown(row);
        }
    }

    public boolean shiftLeft(Tile tile) {
        boolean valid = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    if (tileX + i - 1 < 0 || (i > 0 && board[tileX + i - 1][tileY + j] > 0 && tile.getTile()[i-1][j] == 0) || (i == 0 && board[tileX + i - 1][tileY + j] > 0)) {
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

            tileX--;

        }
        return valid;
    }

    public boolean shiftRight(Tile tile) {
        boolean valid = true;
        for (int i = 3; i >= 0; i--) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    if (tileX + i + 1 >= 10 || (i != 3 && board[tileX + i + 1][tileY + j] > 0 && tile.getTile()[i+1][j] == 0) || (i == 3 && board[tileX + i + 1][tileY + j] > 0)) {
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

    public void rotate(Tile tile) {
        boolean valid = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tileX + i < 10 && tileY + j < 22 && tile.getTile()[i][j] != 0) {
                    board[tileX + i][tileY + j] = 0;
                }
                if (Tile.rotated(tile)[i][j] != 0) {
                    if (tileX + i < 0 || tileX + i >= 10 || tileY + j >= 22 || board[tileX+i][tileY+j] > 0) {
                        valid = false;
                    }
                }
            }
        }

        if (valid) {
            tile.rotate();
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tileX + i < 10 && tileY + j < 22 && tile.getTile()[i][j] > 0) {
                    board[tileX + i][tileY + j] = tile.getTile()[i][j];
                }
            }
        }
    }

    public int hardDrop(Tile tile) {
        boolean success;
        int count = 0;
        do {
            success = shiftDown(tile);
            count++;
        } while (success);
        return count;
    }

    public void hardDrop(GhostTile tile) {
        int x = tileX;
        int y = tileY;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] < 0) {
                    board[i][j] = 0;
                }
            }
        }

        boolean success;
        do {
            success = shiftDown(tile, x, y);
            if (success) {
                y++;
            }
        } while (success);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (x + i < 10 && y + j < 22 && tile.getTile()[i][j] != 0 && board[x+i][y+j] == 0) {
                    board[x + i][y + j] = tile.getId();
                }
            }
        }
    }

    public void ghostTile(Tile tile) {
        GhostTile ghost = new GhostTile(-tile.getId(), tile.getOrientation());
        hardDrop(ghost);
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

    public void removeTile(Tile tile) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile.getTile()[i][j] != 0) {
                    board[tileX + i][tileY + j] = 0;
                }
            }
        }
    }

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
