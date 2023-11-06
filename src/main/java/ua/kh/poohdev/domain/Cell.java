package ua.kh.poohdev.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cell {

    private static final String OPENED_CELL_ERROR_MSG =
        "The Cell (row = %d, col = %d) is already opened and has the value = %d, " +
        "but it has tried to set a new value = %d.";

    private static final String REMOVE_POSSIBLE_VALUE_ERROR_MSG =
        "The Cell (row = %d, col = %d) has NO possible values but still has not opened. " +
        "The last removed possible value = %d.";

    private final int rowNumber;
    private final int colNumber;
    private final int blockNumber;

    private final Set<Integer> possibleValues;
    private int value;

    public Cell(int row, int col) {
        rowNumber = row;
        colNumber = col;
        // only integer part of the number is needed
        blockNumber = 3 * (row / 3) + (col / 3);

        possibleValues = new HashSet<>();
        possibleValues.addAll(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        value = 0;
    }

    public boolean isOpened() {
        return value != 0;
    }

    public void setValue(int newValue) {

        if (isOpened()) {
            throw new IllegalArgumentException(
                String.format(OPENED_CELL_ERROR_MSG, rowNumber, colNumber, value, newValue));
        }

        value = newValue;
        possibleValues.clear();
    }

    public void removePossibleValue(int value) {
        possibleValues.remove(value);

        if (possibleValues.isEmpty() && !isOpened()) {
            throw new IllegalArgumentException(
                String.format(REMOVE_POSSIBLE_VALUE_ERROR_MSG, rowNumber, colNumber, value));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return rowNumber == cell.rowNumber &&
               colNumber == cell.colNumber &&
               blockNumber == cell.blockNumber &&
               value == cell.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowNumber, colNumber, blockNumber, value);
    }
}
