package org.example.Models;

import java.util.Scanner;

public class Player {

    private Long id;
    private String name;
    private Gender gender;
    private Symbol playerSymbol;
    private PlayerType playerType;

    //    Constructor
    public Player(String name, Gender gender, Symbol playerSymbol, PlayerType playerType, Long id) {
        this.name = name;
        this.gender = gender;
        this.playerSymbol = playerSymbol;
        this.playerType = playerType;
        this.id = id;
    }


    /* ----------------------Getters and Setters------------------------------------*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Symbol getPlayerSymbol() {
        return playerSymbol;
    }

    public void setPlayerSymbol(Symbol playerSymbol) {
        this.playerSymbol = playerSymbol;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Move makeMove (Board board) {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the row no. you want to " +
                "make a move on: [0 - " + (board.getBoardSize() - 1) + "]");
        int row = input.nextInt();
        System.out.println("Please enter the col no. you want to " +
                "make a move on: [0 - " + (board.getBoardSize() - 1) + "]");
        int col = input.nextInt();

        return new Move(this, new Cell(row, col));
    }
}
