package org.example.Strategies.BotPlayingStrategy;

import org.example.Models.Board;
import org.example.Models.Cell;
import org.example.Models.CellStatus;
import org.example.Models.Move;

import java.util.HashSet;
import java.util.Set;

public class EasyBotPlayingStrategy implements BotPlayingStrategy{
    private static Set<Cell> setToTrackUndoMoves = new HashSet<>();
    @Override
    public Move makeNextMove(Board board) {
        for (int r = 0; r < board.getBoardSize(); r++) {
            for (int c = 0; c < board.getBoardSize(); c++) {
                Cell cell = board.getGameBoard()[r][c];
                if (cell.getStatus().equals(CellStatus.EMPTY)) {
                    if (r == board.getBoardSize() - 1 && c == board.getBoardSize() - 1) {
                        return new Move(null, cell);
                    }
                    else if (setToTrackUndoMoves.contains(cell)) {
                        setToTrackUndoMoves.remove(cell);
                    }
                    else {
                        return new Move(null, cell);
                    }
                }
            }
        }
        return null;
    }

    public static Set<Cell> getSetToTrackUndoMoves () {
        return setToTrackUndoMoves;
    }
}
