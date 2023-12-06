package org.example.Strategies.BotPlayingStrategy;

import org.example.Models.Board;
import org.example.Models.Cell;
import org.example.Models.CellStatus;
import org.example.Models.Move;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MediumBotPlayingStrategy implements BotPlayingStrategy{
    private static Set<Cell> setToTrackUndoMoves = new HashSet<>();
    @Override
    public Move makeNextMove(Board board) {
        Random rand = new Random();
        int randomRow = rand.nextInt(board.getBoardSize());
        int randomCol = rand.nextInt(board.getBoardSize());

        while (!board.getGameBoard()[randomRow][randomCol].getStatus()
                .equals(CellStatus.EMPTY)) {

            randomRow = rand.nextInt(board.getBoardSize());
            randomCol = rand.nextInt(board.getBoardSize());
        }
        Cell cell = board.getGameBoard()[randomRow][randomCol];
        if (randomRow == board.getBoardSize() - 1 && randomCol == board.getBoardSize() - 1) {
            return new Move(null, cell);
        }
        else if (setToTrackUndoMoves.contains(cell)) {
            setToTrackUndoMoves.remove(cell);
        }
        return new Move(null, cell);
    }
    public static Set<Cell> getSetToTrackUndoMoves () {
        return setToTrackUndoMoves;
    }
}
