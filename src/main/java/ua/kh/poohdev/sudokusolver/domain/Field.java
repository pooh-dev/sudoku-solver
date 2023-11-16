package ua.kh.poohdev.sudokusolver.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.OPENED_CELLS_PREDICATES;
import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.RELATED_CELLS_PREDICATE;
import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.REQUIRED_VALUES;
import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.UNIT_INDEXES;

public class Field {

    private static final String INCORRECT_INIT_VALUE_ERROR_MSG = "The init value must be between 0 and 9, but was %d.";
    private static final String CELL_NOT_FOUND_ERROR_MSG = "There is no Cell with row = %d and col = %d.";

    private static int quantityOfFields = 0;

    private final Set<Cell> cells;
    private boolean isCorrupted;

    public Field() {
        cells = new HashSet<>();
        isCorrupted = false;
        quantityOfFields++;
    }

    public void init(List<String> initRows) {

        for (int row: UNIT_INDEXES) {
            for (int col: UNIT_INDEXES) {
                cells.add(new Cell(row, col));
            }
        }

        for (int row: UNIT_INDEXES) {
            List<Integer> rowValues = Arrays.stream(initRows.get(row).split(","))
                    .map(String::strip)
                    .map(Integer::valueOf)
                    .toList();

            for (int col: UNIT_INDEXES) {
                var initValue = rowValues.get(col);
                if (initValue < 0 || initValue > 9) {
                    throw new IllegalArgumentException(String.format(INCORRECT_INIT_VALUE_ERROR_MSG, initValue));
                }

                if (initValue != 0) {
                    setCellValue(getCell(row, col), initValue);
                }
            }
        }
    }

    public Cell getCell(int row, int col) {
        return cells.stream()
                .filter(cell -> cell.getRowNumber() == row)
                .filter(cell -> cell.getColNumber() == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(CELL_NOT_FOUND_ERROR_MSG, row, col)));
    }

    public void setCellValue(Cell cell, int value) {
        cell.setValue(value);
        cells.stream()
                .filter(relatedCell -> RELATED_CELLS_PREDICATE.test(relatedCell, cell))
                .forEach(relatedCell -> relatedCell.removePossibleValue(value));
    }

    public Set<Cell> getCells() {
        return cells;
    }

    public boolean isOpened() {
        for (int unitIdx: UNIT_INDEXES) {
            for (var openedCellsPredicate: OPENED_CELLS_PREDICATES) {

                var requiredValues = new HashSet<>(REQUIRED_VALUES);

                cells.stream()
                        .filter(cell -> openedCellsPredicate.test(cell, unitIdx))
                        .forEach(cell -> requiredValues.remove(cell.getValue()));

                if (!requiredValues.isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setCorrupted() {
        isCorrupted = true;
    }

    public boolean isNotCorrupted() {
        return !isCorrupted;
    }

    public static int getQuantityOfFields() {
        return quantityOfFields;
    }

    public Field copy() {
        var newField = new Field();
        cells.stream()
                .map(Cell::copy)
                .forEach(newCell -> newField.getCells().add(newCell));

        return newField;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row: UNIT_INDEXES) {
            for (int col: UNIT_INDEXES) {
                result.append(getCell(row, col).toString());
                result.append("  ");
            }
            result.append("\n");
        }
        return result.toString();
    }
}
