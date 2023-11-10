package ua.kh.poohdev.sudokusolver.algorithms;

import ua.kh.poohdev.sudokusolver.domain.Cell;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Stream;

public class CellFinderByUniquePossibleValue implements CellFinder {

    private static final int[] INDEXES = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    private static final List<BiPredicate<Cell, Integer>> SELECTED_CELLS_PREDICATES = Arrays.asList(
            (cell, colNumber) -> cell.getColNumber() == colNumber,
            (cell, rowNumber) -> cell.getRowNumber() == rowNumber,
            (cell, blockNumber) -> cell.getBlockNumber() == blockNumber
    );

    @Override
    public CellFinderResult find(Set<Cell> cells) {

        for (int idx: INDEXES) {
            var foundCell = SELECTED_CELLS_PREDICATES.stream()
                    .map(predicate -> findInSelectedCells(cells.stream().filter(cell -> predicate.test(cell, idx))))
                    .filter(CellFinderResult::isCellFound)
                    .findFirst();

            if (foundCell.isPresent()) {
                return foundCell.get();
            }
        }

        return CellFinderResult.notFound();
    }

    private CellFinderResult findInSelectedCells(Stream<Cell> selectedCells) {

        var possibleValuesMap = new HashMap<Integer, Set<Cell>>();

        selectedCells.forEach(cell ->
            cell.getPossibleValues().forEach(value -> {
                if (!possibleValuesMap.containsKey(value)) {
                    possibleValuesMap.put(value, new HashSet<>());
                }
                possibleValuesMap.get(value).add(cell);
            }));

        return possibleValuesMap.entrySet().stream()
                .filter(entry -> entry.getValue().size() == 1)
                .findFirst()
                .map(entry -> CellFinderResult.found(entry.getValue().iterator().next(), entry.getKey()))
                .orElseGet(CellFinderResult::notFound);
    }
}
