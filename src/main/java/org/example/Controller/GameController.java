package org.example.Controller;

import org.example.Models.Game;
import org.example.Models.GameStatus;
import org.example.Models.Player;
import org.example.Strategies.WinningStrategy.WinningStrategy;

import java.util.List;

public class GameController {

    public Game startGame(int boardSize, List<Player> players,
                          List<WinningStrategy> winningStrategies){
        return Game.getBuilder()
                .setBoardSize(boardSize)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
    }

    public void makeMove(Game game) {
        game.makeMove(game);
    }

    public void displayBoard(Game game) {
        game.printBoard();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void undoGame (Game game) {
        game.undoGame(game);
    }

    public GameStatus getGameStatus (Game game) {
        return game.getGameStatus();
    }

    public List<Player> getPlayers (Game game) {
        return game.getPlayers();
    }

}
