package ua.kh.poohdev.sudokusolver.algorithms;

import ua.kh.poohdev.sudokusolver.domain.Cell;

import java.util.stream.Stream;

public class CellFinderBySinglePossibleValue implements CellFinder {

    @Override
    public CellFinderResult find(Stream<Cell> cellsInUnit) {
        return cellsInUnit
                .filter(cell -> !cell.isOpened())
                .filter(cell -> cell.getPossibleValues().size() == 1)
                .map(cell -> CellFinderResult.found(cell, cell.getPossibleValues().iterator().next()))
                .findFirst()
                .orElseGet(CellFinderResult::notFound);
    }
}
