package org.example.Models;

public class Move {
    private Player currPlayer;
    private Cell cell;

//    Constructor
    public Move(Player currPlayer, Cell cell) {
        this.currPlayer = currPlayer;
        this.cell = cell;
    }

    /* ----------------------Getters and Setters------------------------------------*/
    public Player getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
