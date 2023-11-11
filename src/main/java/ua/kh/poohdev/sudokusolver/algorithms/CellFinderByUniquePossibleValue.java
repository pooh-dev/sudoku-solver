package ua.kh.poohdev.sudokusolver.algorithms;

import ua.kh.poohdev.sudokusolver.domain.Cell;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class CellFinderByUniquePossibleValue implements CellFinder {

    @Override
    public CellFinderResult find(Stream<Cell> cellsInUnit) {
        var possibleValuesMap = new HashMap<Integer, Set<Cell>>();

        cellsInUnit.forEach(cell ->
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
