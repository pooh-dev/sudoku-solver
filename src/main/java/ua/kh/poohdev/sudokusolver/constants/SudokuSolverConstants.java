package ua.kh.poohdev.sudokusolver.constants;

import ua.kh.poohdev.sudokusolver.domain.Cell;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

public class SudokuSolverConstants {
    public static final int[] UNIT_INDEXES = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    public static final List<Integer> REQUIRED_VALUES = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static final BiPredicate<Cell, Cell> RELATED_CELLS_PREDICATE = (c1, c2) ->
            c1.getColNumber() == c2.getColNumber() ||
            c1.getRowNumber() == c2.getRowNumber() ||
            c1.getBlockNumber() == c2.getBlockNumber();

    public static final List<BiPredicate<Cell, Integer>> CELLS_IN_UNIT_PREDICATES = Arrays.asList(
            (cell, rowNumber) -> cell.getRowNumber() == rowNumber,
            (cell, colNumber) -> cell.getColNumber() == colNumber,
            (cell, blockNumber) -> cell.getBlockNumber() == blockNumber
    );

    public static final List<BiPredicate<Cell, Integer>> OPENED_CELLS_PREDICATES = Arrays.asList(
            (cell, rowNumber) -> cell.isOpened() && cell.getRowNumber() == rowNumber,
            (cell, colNumber) -> cell.isOpened() && cell.getColNumber() == colNumber,
            (cell, blockNumber) -> cell.isOpened() && cell.getBlockNumber() == blockNumber
    );

}
