package ua.kh.poohdev.sudokusolver;

import ua.kh.poohdev.sudokusolver.algorithms.CellFinder;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderBySinglePossibleValue;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderByUniquePossibleValue;
import ua.kh.poohdev.sudokusolver.domain.Field;

import java.util.Arrays;
import java.util.List;

import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.CELLS_IN_UNIT_PREDICATES;
import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.UNIT_INDEXES;

public class Solver {

    private static final List<CellFinder> CELL_FINDERS = Arrays.asList(
            new CellFinderBySinglePossibleValue(),
            new CellFinderByUniquePossibleValue()
    );

    public Field solve(Field field) {
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

        if (field.isOpened()) {
            return field;
        }

        // solution is not found
        // TODO: add logic to suggest a possible solution
        var newField = field.copy();
        return newField;
    }
}
