package ua.kh.poohdev.sudokusolver;

import ua.kh.poohdev.sudokusolver.algorithms.CellFinder;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderByMinQuantityOfPossibleValues;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderBySinglePossibleValue;
import ua.kh.poohdev.sudokusolver.algorithms.CellFinderByUniquePossibleValue;
import ua.kh.poohdev.sudokusolver.domain.Field;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.CELLS_IN_UNIT_PREDICATES;
import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.UNIT_INDEXES;

public class Solver {

    private static final List<CellFinder> CELL_FINDERS = Arrays.asList(
            new CellFinderBySinglePossibleValue(),
            new CellFinderByUniquePossibleValue()
    );

    private static final CellFinder MIN_QUANTITY_POSSIBLE_VALUES_FINDER =
            new CellFinderByMinQuantityOfPossibleValues();

    private static final Queue<Field> FIELD_QUEUE = new LinkedList<>();

    public Optional<Field> solve(Field inputField) {

        FIELD_QUEUE.add(inputField);

        while (!FIELD_QUEUE.isEmpty()) {
            Field field = FIELD_QUEUE.remove();
            boolean isFieldCorrupted = false;

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
                                } catch (IllegalArgumentException e) {
                                    isFieldCorrupted = true;
                                    break;
                                }

                            }

                        }
                    }
                }
            } while (isAnyCellFound && !isFieldCorrupted);

            if (!isFieldCorrupted && field.isOpened()) {
                FIELD_QUEUE.clear();
                return Optional.of(field);
            }

            if (!isFieldCorrupted) {

                var cellToContinueSearching =
                        MIN_QUANTITY_POSSIBLE_VALUES_FINDER.find(field.getCells().stream()).getCell();
                for (int possibleValue : cellToContinueSearching.getPossibleValues()) {
                    var copiedField = field.copy();
                    var cellInCopiedField = copiedField.getCell(
                            cellToContinueSearching.getRowNumber(),
                            cellToContinueSearching.getColNumber()
                    );

                    copiedField.setCellValue(cellInCopiedField, possibleValue);
                    FIELD_QUEUE.add(copiedField);
                }
            }
        }

        return Optional.empty();
    }
}
