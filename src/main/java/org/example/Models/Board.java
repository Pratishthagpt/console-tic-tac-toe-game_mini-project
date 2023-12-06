package org.example.Models;

public class Board {
    private int boardSize;
    private Cell[][] gameBoard;

    //    Constructor -> for initialization of new Board
    public Board(int boardSize) {
        this.boardSize = boardSize;
        gameBoard = new Cell[boardSize][boardSize];

        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                gameBoard[r][c] = new Cell(r, c);
            }
        }
    }

    /* ----------------------Getters and Setters------------------------------------*/
    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public Cell[][] getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(Cell[][] gameBoard) {
        this.gameBoard = gameBoard;
    }


    /* ----------------------Methods------------------------------------*/

    public void displayGameBoard() {
        for (int r = 0; r < boardSize; r++) {
            for (int c = 0; c < boardSize; c++) {
                Cell cell = gameBoard[r][c];
                if (cell.getStatus().equals(CellStatus.EMPTY)) {
                    if (c == boardSize - 1 && r == boardSize - 1) {
                        System.out.print("   ");
                    }
                    else if (c == boardSize - 1) {
                        System.out.print("___");
                    }
                    else if (r == boardSize - 1) {
                        System.out.print("   |");
                    }
                    else {
                        System.out.print("___|");
                    }
                }
                else {
                    if (c == boardSize - 1 && r == boardSize - 1) {
                        System.out.print(" " + cell.getPlayer().getPlayerSymbol().getSymbol() + " ");
                    }
                    else if (c == boardSize - 1) {
                        System.out.print("_" + cell.getPlayer().getPlayerSymbol().getSymbol() + "_");
                    }
                    else {
                        System.out.print("_" + cell.getPlayer().getPlayerSymbol().getSymbol() + "_|");
                    }
                }
            }
            System.out.println();
        }
    }
}
