package ua.kh.poohdev.sudokusolver.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;

public class Field {

    private static final String INCORRECT_INIT_VALUE_ERROR_MSG = "The init value must be between 0 and 9, but was %d.";
    private static final String CELL_NOT_FOUND_ERROR_MSG = "There is no Cell with row = %d and col = %d.";

    private static final BiPredicate<Cell, Cell> RELATED_CELLS_PREDICATE = (c1, c2) ->
            c1.getColNumber() == c2.getColNumber() ||
            c1.getRowNumber() == c2.getRowNumber() ||
            c1.getBlockNumber() == c2.getBlockNumber();

    private final Set<Cell> cells;

    public Field() {
        cells = new HashSet<>();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells.add(new Cell(row, col));
            }
        }
    }

    public void init(List<String> initRows) {
        for (int row = 0; row < 9; row++) {
            List<Integer> rowValues = Arrays.stream(initRows.get(row).split(","))
                    .map(String::strip)
                    .map(Integer::valueOf)
                    .toList();

            for (int col = 0; col < 9; col++) {
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

    public void setCellValue(Cell cell, int value) {
        cell.setValue(value);
        cells.stream()
                .filter(relatedCell -> RELATED_CELLS_PREDICATE.test(relatedCell, cell))
                .forEach(relatedCell -> relatedCell.removePossibleValue(value));
    }

    public Set<Cell> getCells() {
        return cells;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                result.append(getCell(row, col).toString());
                result.append("   ");
            }
            result.append("\n\n");
        }
        return result.toString();
    }

    private Cell getCell(int row, int col) {
        return cells.stream()
                .filter(cell -> cell.getRowNumber() == row)
                .filter(cell -> cell.getColNumber() == col)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(CELL_NOT_FOUND_ERROR_MSG, row, col)));
    }
}
