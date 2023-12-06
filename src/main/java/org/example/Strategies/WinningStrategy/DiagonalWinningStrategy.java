package org.example.Strategies.WinningStrategy;

import org.example.Models.Board;
import org.example.Models.Move;
import org.example.Models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{

    private final Map<Symbol, Integer> leftDiagonalMap = new HashMap<>();
    private final Map<Symbol, Integer> rightDiagonalMap = new HashMap<>();

    @Override
    public boolean checkWinner(Move move, Board board) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getCurrPlayer().getPlayerSymbol();

        if (row == col) {
            if (leftDiagonalMap.containsKey(symbol)) {
                leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol) + 1);
            }
            else leftDiagonalMap.put(symbol, 1);
        }
        if (row + col == board.getBoardSize() - 1) {

            if (rightDiagonalMap.containsKey(symbol)) {
                rightDiagonalMap.put(symbol, rightDiagonalMap.get(symbol) + 1);
            }
            else rightDiagonalMap.put(symbol, 1);
        }

        if (row == col && leftDiagonalMap.get(symbol) == board.getBoardSize()) {
            return true;
        }

        if (row + col == board.getBoardSize() - 1 && rightDiagonalMap.get(symbol) == board.getBoardSize()) {
            return true;
        }
        return false;
    }

    @Override
    public void updateWinningStrategyAfterUndo(Move move, Board board) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getCurrPlayer().getPlayerSymbol();

        if (row == col) {
            leftDiagonalMap.put(symbol, leftDiagonalMap.get(symbol) - 1);
        }
        if (row + col == board.getBoardSize() - 1) {
            rightDiagonalMap.put(symbol, rightDiagonalMap.get(symbol) - 1);
        }
    }
}
