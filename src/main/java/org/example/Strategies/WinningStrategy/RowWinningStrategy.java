package org.example.Strategies.WinningStrategy;

import org.example.Models.*;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{

    private Map<Integer, Map<Symbol, Integer>> rowMaps = new HashMap<>();

    @Override
    public boolean checkWinner(Move move, Board board) {
        Cell cell = move.getCell();
        int row = cell.getRow();
        Symbol symbol = move.getCurrPlayer().getPlayerSymbol();

        if (!rowMaps.containsKey(row)) {
            rowMaps.put(row, new HashMap<>());
        }

        Map<Symbol, Integer> currRowMap = rowMaps.get(row);

        if (currRowMap.containsKey(symbol)) {
            currRowMap.put(symbol, currRowMap.get(symbol) + 1);
        }
        else currRowMap.put(symbol, 1);

        return currRowMap.get(symbol) == board.getBoardSize();
    }

    @Override
    public void updateWinningStrategyAfterUndo(Move move, Board board) {
        Cell cellToBeReversed = move.getCell();
        int row = cellToBeReversed.getRow();
        Symbol symbol = move.getCurrPlayer().getPlayerSymbol();

        Map<Symbol, Integer> currRowMap = rowMaps.get(row);
        if (currRowMap.containsKey(symbol)) {
            currRowMap.put(symbol, currRowMap.get(symbol) - 1);
        }
    }
}
