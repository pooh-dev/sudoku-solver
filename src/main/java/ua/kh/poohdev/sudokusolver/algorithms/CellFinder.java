package ua.kh.poohdev.sudokusolver.algorithms;

import ua.kh.poohdev.sudokusolver.domain.Cell;

import java.util.Set;

public interface CellFinder {
    CellFinderResult find(Set<Cell> cells);
}
