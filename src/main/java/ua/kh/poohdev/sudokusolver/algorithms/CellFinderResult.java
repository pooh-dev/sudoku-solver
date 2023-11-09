package ua.kh.poohdev.sudokusolver.algorithms;

import ua.kh.poohdev.sudokusolver.domain.Cell;

public class CellFinderResult {

    private final Cell cell;
    private final int value;

    public CellFinderResult(Cell cell, int value) {
        this.cell = cell;
        this.value = value;
    }

    public boolean isCellFound() {
        return cell != null && value != 0;
    }

    public static CellFinderResult notFound() {
        return new CellFinderResult(null, 0);
    }

    public static CellFinderResult found(Cell cell, int value) {
        return new CellFinderResult(cell, value);
    }
}
