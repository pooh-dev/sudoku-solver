package ua.kh.poohdev.sudokusolver.algorithms;

import ua.kh.poohdev.sudokusolver.domain.Cell;

import java.util.Comparator;
import java.util.stream.Stream;

public class CellFinderByMinQuantityOfPossibleValues implements CellFinder {

    @Override
    public CellFinderResult find(Stream<Cell> cellsInUnit) {
        return cellsInUnit
                .filter(cell -> !cell.isOpened())
                .min(Comparator.comparingInt(cell -> cell.getPossibleValues().size()))
                .stream().findFirst()
                .map(cell -> CellFinderResult.found(cell, cell.getValue()))
                .orElseGet(CellFinderResult::notFound);
    }
}
