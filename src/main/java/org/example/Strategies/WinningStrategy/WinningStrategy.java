package org.example.Strategies.WinningStrategy;

import org.example.Models.Board;
import org.example.Models.Move;

public interface WinningStrategy {
    boolean checkWinner(Move move, Board board);
    void updateWinningStrategyAfterUndo (Move move, Board board);
}
