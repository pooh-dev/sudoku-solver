package ua.kh.poohdev.sudokusolver;

import ua.kh.poohdev.sudokusolver.algorithms.CellFinder;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderBySinglePossibleValue;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderByUniquePossibleValue;
import ua.kh.poohdev.sudokusolver.domain.Cell;
import ua.kh.poohdev.sudokusolver.domain.Field;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

public class Solver {

    private static final int[] UNIT_INDEXES = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    private static final List<BiPredicate<Cell, Integer>> CELLS_IN_UNIT_PREDICATES = Arrays.asList(
            (cell, colNumber) -> cell.getColNumber() == colNumber,
            (cell, rowNumber) -> cell.getRowNumber() == rowNumber,
            (cell, blockNumber) -> cell.getBlockNumber() == blockNumber
    );

    private static final List<CellFinder> CELL_FINDERS = Arrays.asList(
            new CellFinderBySinglePossibleValue(),
            new CellFinderByUniquePossibleValue()
    );

    public void solve(Field field) {
        boolean isAnyCellFound;
        do {
            isAnyCellFound = false;

            for (var cellFinder : CELL_FINDERS) {
                for (var cellsPredicate : CELLS_IN_UNIT_PREDICATES) {
                    for (var unitIdx : UNIT_INDEXES) {

                        var cellsInUnit = field.getCells().stream()
                                .filter(cell -> cellsPredicate.test(cell, unitIdx));

                        var cellFinderResult = cellFinder.find(cellsInUnit);
                        if (cellFinderResult.isCellFound()) {
                            field.setCellValue(cellFinderResult.getCell(), cellFinderResult.getValue());
                            isAnyCellFound = true;
                        }

                    }
                }
            }
        } while (isAnyCellFound);
    }
}
