package ua.kh.poohdev.sudokusolver;

import ua.kh.poohdev.sudokusolver.algorithms.CellFinder;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderByMinQuantityOfPossibleValues;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderBySinglePossibleValue;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderByUniquePossibleValue;
import ua.kh.poohdev.sudokusolver.domain.Cell;
import ua.kh.poohdev.sudokusolver.domain.Field;
import ua.kh.poohdev.sudokusolver.exceptions.InconsistentCellStateException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.CELLS_IN_UNIT_PREDICATES;
import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.UNIT_INDEXES;

public class Solver {

    private static final Queue<Field> FIELD_QUEUE = new LinkedList<>();

    private static final List<CellFinder> CELL_FINDERS = Arrays.asList(
            new CellFinderBySinglePossibleValue(),
            new CellFinderByUniquePossibleValue()
    );

    private static final CellFinder MIN_QUANTITY_POSSIBLE_VALUES_FINDER =
            new CellFinderByMinQuantityOfPossibleValues();


    public Optional<Field> solve(Field inputField) {
        FIELD_QUEUE.add(inputField);

        while (!FIELD_QUEUE.isEmpty()) {
            Field field = FIELD_QUEUE.remove();
            tryToFindSolution(field);

            if (field.isNotCorrupted()) {
                // solution is found
                if (field.isOpened()) {
                    FIELD_QUEUE.clear();
                    return Optional.of(field);
                }
                // find possible solutions
                populateFieldQueueWithPossibleSolutions(field);
            }
        }

        return Optional.empty();
    }

    private void tryToFindSolution(Field field) {
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
                            try {
                                field.setCellValue(cellFinderResult.getCell(), cellFinderResult.getValue());
                                isAnyCellFound = true;
                            } catch (InconsistentCellStateException e) {
                                field.setCorrupted();
                                break;
                            }
                        }

                    }
                }
            }

        } while (isAnyCellFound && field.isNotCorrupted());
    }

    private void populateFieldQueueWithPossibleSolutions(Field field) {
        Cell cellWithMinOfPossibleValues =
                MIN_QUANTITY_POSSIBLE_VALUES_FINDER.find(field.getCells().stream()).getCell();
        int rowNumber = cellWithMinOfPossibleValues.getRowNumber();
        int colNumber = cellWithMinOfPossibleValues.getColNumber();

        for (int possibleValue : cellWithMinOfPossibleValues.getPossibleValues()) {
            Field copiedField = field.copy();
            Cell cellInCopiedField = copiedField.getCell(rowNumber, colNumber);
            copiedField.setCellValue(cellInCopiedField, possibleValue);
            FIELD_QUEUE.add(copiedField);
        }
    }
}
