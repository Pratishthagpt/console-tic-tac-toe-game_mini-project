package org.example;

import org.example.Controller.GameController;
import org.example.Models.*;
import org.example.Strategies.BotPlayingStrategy.BotPlayingStrategy;
import org.example.Strategies.BotPlayingStrategy.EasyBotPlayingStrategy;
import org.example.Strategies.BotPlayingStrategy.MediumBotPlayingStrategy;
import org.example.Strategies.WinningStrategy.ColWinningStrategy;
import org.example.Strategies.WinningStrategy.DiagonalWinningStrategy;
import org.example.Strategies.WinningStrategy.RowWinningStrategy;
import org.example.Strategies.WinningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class TicTactoeMain {
    public static void main(String[] args) {
        GameController gameController = new GameController();

//        Asking the user for the Player details and board dimensions
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the size of board for TicTacToe Game: ");
        int boardsize = sc.nextInt();

        int noOfPlayers = boardsize - 1;

        List<Player> players = getPlayersInputFromUser(noOfPlayers);

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());

//        Start the game
        Game game = gameController.startGame(boardsize, players, winningStrategies);
        int movesCounter = 0;

        System.out.println("Let's start the game.");
        while (gameController.getGameStatus(game).equals(GameStatus.ONGOING)) {
//            Display the game board
            System.out.println("This is current state of board.");
            gameController.displayBoard(game);

            int playerIndx = game.getNextMovePlayerIndex();
            Player currPlayer = game.getPlayers().get(playerIndx);

//            Asking for if human player wish to undo the game
            if(movesCounter > 1 && currPlayer.getPlayerType().equals(PlayerType.HUMAN)) {
                System.out.println("Do you want to undo the last move: (Y/N)");
                String input = sc.next();

                if (input.equalsIgnoreCase("Y")) {
                    gameController.undoGame(game);
                }
                else {
                    gameController.makeMove(game);
                }
            }
            else {
                gameController.makeMove(game);
            }
            movesCounter++;
        }
        if (gameController.getGameStatus(game).equals(GameStatus.ENDED)) {
            gameController.displayBoard(game);
            System.out.println("Game has ended. Winner is " + gameController.getWinner(game).getName() +
                     ". Thanks for playing the game. Bye!!");
        }
        else {
            System.out.println("Game has DRAWN!!");
        }
    }

    public static List<Player> getPlayersInputFromUser (int noOfPlayers) {
        Scanner sc = new Scanner(System.in);
        List<Player> players = new ArrayList<>();

        for (int i = 1; i <= noOfPlayers; i++) {
            System.out.println("Please enter the player name: ");
            String name = sc.next();

            System.out.println("Please enter the player type: (HUMAN/ BOT)");
            String type = sc.next();

            PlayerType playerType = PlayerType.HUMAN;

            if(type.equalsIgnoreCase("BOT")) {
                playerType = PlayerType.BOT;
            }

            Long id = (long)i;
            Player p;

            if (playerType.equals(PlayerType.BOT)) {
                System.out.println("Please enter the bot symbol: ");
                String symbol = sc.next();
                Symbol botSymbol = new Symbol(symbol.charAt(0));

                System.out.println("Please enter the game difficulty level: (EASY / MEDIUM)");
                String level = sc.next();
                BotDifficultyLevelType gameDifficultyLevelType = BotDifficultyLevelType.EASY;
                BotPlayingStrategy botPlayingStrategy = new EasyBotPlayingStrategy();

                if (level.equalsIgnoreCase("MEDIUM")) {
                    gameDifficultyLevelType = BotDifficultyLevelType.MEDIUM;
                    botPlayingStrategy = new MediumBotPlayingStrategy();
                }

                p = new Bot("BOT", Gender.O, botSymbol, playerType,
                        id, gameDifficultyLevelType, botPlayingStrategy);
            }
            else {
                System.out.println("Please enter the player symbol: ");
                String symbol = sc.next();
                Symbol playerSymbol = new Symbol(symbol.charAt(0));

                System.out.println("Please enter the gender of player: (F/M/O)");
                String genderChar = sc.next();
                Gender gender = Gender.O;
                if (genderChar.equalsIgnoreCase("F")) {
                    gender = Gender.F;
                }
                else if (genderChar.equalsIgnoreCase("M")) {
                    gender = Gender.M;
                }
                p = new Player(name, gender, playerSymbol, playerType, id);
            }
            players.add(p);
        }
        return players;
    }
}