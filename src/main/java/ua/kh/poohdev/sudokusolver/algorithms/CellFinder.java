package ua.kh.poohdev.sudokusolver.algorithms;

import ua.kh.poohdev.sudokusolver.domain.Cell;

import java.util.stream.Stream;

public interface CellFinder {
    CellFinderResult find(Stream<Cell> cellsInUnit);
}
