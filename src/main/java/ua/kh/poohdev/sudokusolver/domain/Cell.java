package ua.kh.poohdev.sudokusolver.domain;

import ua.kh.poohdev.sudokusolver.exceptions.InconsistentCellStateException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static ua.kh.poohdev.sudokusolver.constants.SudokuSolverConstants.REQUIRED_VALUES;

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
        possibleValues.addAll(REQUIRED_VALUES);

        value = 0;
    }

    public Cell(int rowNumber, int colNumber, int blockNumber, Set<Integer> possibleValues, int value) {
        this.rowNumber = rowNumber;
        this.colNumber = colNumber;
        this.blockNumber = blockNumber;
        this.possibleValues = possibleValues;
        this.value = value;
    }

    public boolean isOpened() {
        return value != 0;
    }

    public void setValue(int newValue) {
        if (isOpened()) {
            throw new InconsistentCellStateException(
                String.format(OPENED_CELL_ERROR_MSG, rowNumber, colNumber, value, newValue));
        }

        value = newValue;
        possibleValues.clear();
    }

    public void removePossibleValue(int value) {
        possibleValues.remove(value);

        if (possibleValues.isEmpty() && !isOpened()) {
            throw new InconsistentCellStateException(
                String.format(REMOVE_POSSIBLE_VALUE_ERROR_MSG, rowNumber, colNumber, value));
        }
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getColNumber() {
        return colNumber;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public Set<Integer> getPossibleValues() {
        return possibleValues;
    }

    public int getValue() {
        return value;
    }

    public Cell copy() {
        return new Cell(rowNumber, colNumber, blockNumber, new HashSet<>(possibleValues), value);
    }

    @Override
    public String toString() {
//        return "(" + rowNumber + "," + colNumber + "," + blockNumber + ") => " + value +
//               " [" + possibleValues.stream().map(Object::toString).collect(Collectors.joining(",")) + "]";
        return String.valueOf(value);
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
