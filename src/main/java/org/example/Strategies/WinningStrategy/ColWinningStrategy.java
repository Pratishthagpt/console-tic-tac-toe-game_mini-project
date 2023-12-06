package org.example.Strategies.WinningStrategy;

import org.example.Models.Board;
import org.example.Models.Cell;
import org.example.Models.Move;
import org.example.Models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColWinningStrategy implements WinningStrategy{

    private Map<Integer, Map<Symbol, Integer>> colMaps = new HashMap<>();
    @Override
    public boolean checkWinner(Move move, Board board) {
        Cell cell = move.getCell();
        int col = cell.getCol();
        Symbol symbol = move.getCurrPlayer().getPlayerSymbol();

        if (!colMaps.containsKey(col)) {
            colMaps.put(col, new HashMap<>());
        }

        Map<Symbol, Integer> currColMap = colMaps.get(col);

        if (currColMap.containsKey(symbol)) {
            currColMap.put(symbol, currColMap.get(symbol) + 1);
        }
        else currColMap.put(symbol, 1);

        return currColMap.get(symbol) == board.getBoardSize();
    }

    @Override
    public void updateWinningStrategyAfterUndo(Move move, Board board) {
        Cell cellToBeReversed = move.getCell();
        int col = cellToBeReversed.getCol();
        Symbol symbol = move.getCurrPlayer().getPlayerSymbol();

        Map<Symbol, Integer> currColMap = colMaps.get(col);
        if (currColMap.containsKey(symbol)) {
            currColMap.put(symbol, currColMap.get(symbol) - 1);
        }
    }
}
