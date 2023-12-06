package org.example.Models;

import org.example.Exceptions.DuplicateSymbolException;
import org.example.Exceptions.InvalidNumberOfBotsException;
import org.example.Strategies.BotPlayingStrategy.BotPlayingStrategy;
import org.example.Strategies.BotPlayingStrategy.EasyBotPlayingStrategy;
import org.example.Strategies.BotPlayingStrategy.MediumBotPlayingStrategy;
import org.example.Strategies.WinningStrategy.WinningStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Game {
    private Board board;
    private List<Player> players;
    private GameStatus gameStatus;
    private List<Move> moves;
    private Player winner;
    private List<WinningStrategy> winningStrategies;

//    We have to track the move index also, that which player has the turn now
    private int nextMovePlayerIndex;

    /**
     We will use Builder Pattern here, because we have a lot of attributes and
     also validations like -
     1. No of bot player = 1,
     2. No two players should have same Symbol

     Along with that we have to make sure that only one object of Game should be created,
     and for that we will make the constructor of Game as private
     */

    public static class Builder {

//        Attributes that we have to take from users (Client)
        private int boardSize;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        public Builder() {
            this.boardSize = 0;
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
        }

        public Builder setBoardSize(int boardSize) {
            this.boardSize = boardSize;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

//       Game Validation Checks
        public boolean validateNoOfBotsAllowed () {
//          Check for only one Bot player is there in game
            int countBot = 0;
            for (Player player : players) {
                if (player.getPlayerType().equals(PlayerType.BOT)) {
                    countBot++;
                }
            }
            if (countBot > 1) return false;

            return true;
        }
        public boolean validateUniqueSymbols () {

//          Check for no two players should have same symbol
            Set<Symbol> numOfSymbols = new HashSet<>();
            for (Player player : players) {
                numOfSymbols.add(player.getPlayerSymbol());
            }
            if (numOfSymbols.size() != players.size()) return false;

            return true;
        }

        public Game build () {
            if (!validateNoOfBotsAllowed()) {
                String message = "Validation Failed !! The the no. of Bot players entered is greater than one." +
                        " Please enter only one Bot player to continue the game.";
                throw new InvalidNumberOfBotsException(message);
            }
            if (!validateUniqueSymbols()) {
                String message = "Validation Failed !! Two players have same symbols. Please enter the symbols again" +
                        " so that each player should have unique symbol.";
                throw new DuplicateSymbolException(message);
            }
            return new Game(boardSize, players, winningStrategies);
        }
    }
    public static Builder getBuilder() {
        return new Builder();
    }


    //    Constructor -> initialization at start of every new Game
    private Game(int boardSize, List<Player> players,
                List<WinningStrategy> winningStrategies) {
        this.board = new Board(boardSize);
        this.players = players;
        this.gameStatus = GameStatus.ONGOING;
        this.moves = new ArrayList<>();
        this.nextMovePlayerIndex = 0;
        this.winningStrategies = winningStrategies;
    }


    /* ----------------------Getters and Setters------------------------------------*/
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void setWinningStrategies(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public void setNextMovePlayerIndex(int nextMovePlayerIndex) {
        this.nextMovePlayerIndex = nextMovePlayerIndex;
    }

    public void makeMove (Game game) {
//        Get the current player from players list
        Player currPlayer = players.get(nextMovePlayerIndex);

        System.out.println("It is " + currPlayer.getName() + "'s move.");

        Move move = currPlayer.makeMove(this.board);

        if (!validateMove(move)) {
            System.out.println(currPlayer.getName() + " have entered invalid move. Try Again!!");
            return;
        }

        System.out.println(currPlayer.getName() + " has make a move on row: " +
                move.getCell().getRow() + " and col: " + move.getCell().getCol() + "."
        );

//        Fill the board with the valid move
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Cell finalCell = board.getGameBoard()[row][col];
        finalCell.setStatus(CellStatus.FILLED);
        finalCell.setPlayer(currPlayer);

//        for this move, the cell status has got changed now
        Move finalMove = new Move(currPlayer, finalCell);

        moves.add(finalMove);
        int currMoveSize = moves.size();

        nextMovePlayerIndex = (nextMovePlayerIndex + 1) % players.size();

//        Now check the winner of game
        if (checkWinner(finalMove, board)) {
            winner = currPlayer;
            game.setGameStatus(GameStatus.ENDED);
        }

        else if (moves.size() == board.getBoardSize() * board.getBoardSize()) {
//            Game has drawn
            game.setGameStatus(GameStatus.DRAWN);
        }
    }


    public boolean validateMove(Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        return row >= 0 && row < board.getBoardSize()
                && col >= 0 && col < board.getBoardSize()
                && board.getGameBoard()[row][col].getStatus().equals(CellStatus.EMPTY);
    }

    private boolean checkWinner(Move move, Board board) {
        for(WinningStrategy winningStrategy : winningStrategies) {
            if (winningStrategy.checkWinner(move, board)) {
                return true;
            }
        }
        return false;
    }
    public void printBoard () {
        board.displayGameBoard();
    }

    public void undoGame (Game game) {
        /**
            Steps to undo the game:
         1. Get the last move that happens.
         2. Remove the move from moves list.
         3. Remove the move from board also (make the cell status empty).
         4. Give the turn to the player whose move got undo.
         5. Update the winner after undo also.
         6. Update the next player index.
         **/

//        Edge Case
        if (moves.isEmpty()) {
            System.out.println("Not there enough moves to undo.");
            return;
        }

        Move moveToBeRemoved = moves.get(moves.size() - 1);
        moves.remove(moveToBeRemoved);

        Cell cellToBeUndo = moveToBeRemoved.getCell();
        Player playerWhoseTurnGotUndo = moveToBeRemoved.getCurrPlayer();

//            To make sure that bot doesn't play on the same cell that has been undone
        EasyBotPlayingStrategy.getSetToTrackUndoMoves().add(cellToBeUndo);
        MediumBotPlayingStrategy.getSetToTrackUndoMoves().add(cellToBeUndo);

        if (cellToBeUndo.getStatus().equals(CellStatus.FILLED)) {
            cellToBeUndo.setStatus(CellStatus.EMPTY);
            cellToBeUndo.setPlayer(playerWhoseTurnGotUndo);
        }


        updateWinnerAfterUndo (moveToBeRemoved, board);

        System.out.println(playerWhoseTurnGotUndo.getName() + "'s move at row: " + cellToBeUndo.getRow()
                + " and col: " + cellToBeUndo.getCol() + " has been undone/removed.");

        nextMovePlayerIndex = players.size() - 1;
        nextMovePlayerIndex %= players.size();
        nextMovePlayerIndex = (nextMovePlayerIndex < 0) ? 0 : nextMovePlayerIndex;
    }

    private void updateWinnerAfterUndo(Move move, Board board) {
        for (WinningStrategy winningStrategy : winningStrategies) {
            winningStrategy.updateWinningStrategyAfterUndo(move, board);
        }
    }
}
