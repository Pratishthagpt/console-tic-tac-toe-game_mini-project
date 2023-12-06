package org.example.Models;

import org.example.Strategies.BotPlayingStrategy.BotPlayingStrategy;

public class Bot extends Player{
    private BotDifficultyLevelType difficultyLevel;
    private BotPlayingStrategy playingStrategy;

    //    Constructor
    public Bot(String name, Gender gender, Symbol playerSymbol, PlayerType playerType, Long id,
               BotDifficultyLevelType botDifficultyLevelType, BotPlayingStrategy botPlayingStrategy) {
        super(name, gender, playerSymbol, playerType, id);
        this.difficultyLevel = botDifficultyLevelType;
        this.playingStrategy = botPlayingStrategy;
    }

    /* ----------------------Getters and Setters------------------------------------*/
    public BotDifficultyLevelType getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(BotDifficultyLevelType difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public BotPlayingStrategy getPlayingStrategy() {
        return playingStrategy;
    }

    public void setPlayingStrategy(BotPlayingStrategy playingStrategy) {
        this.playingStrategy = playingStrategy;
    }

    @Override
    public Move makeMove(Board board) {
//        move will have the cell on which bot make a move and we will set the player also to bot
        Move move = playingStrategy.makeNextMove(board);

        move.setCurrPlayer(this);
        return move;

    }
}
