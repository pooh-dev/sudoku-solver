package ua.kh.poohdev.sudokusolver;

import ua.kh.poohdev.sudokusolver.domain.Field;

import java.util.Arrays;
import java.util.List;

public class Main {

    private static final List<String> SIMPLE_PUZZLE = Arrays.asList(
            "0,1,3,0,8,5,0,0,6",
            "0,0,0,0,3,1,0,0,0",
            "0,0,7,6,0,0,0,0,0",
            "0,0,6,9,0,0,0,0,3",
            "1,7,0,0,0,0,0,2,5",
            "3,0,0,0,0,6,9,0,0",
            "0,0,0,0,0,2,4,0,0",
            "0,0,0,8,7,0,0,0,0",
            "9,0,0,5,6,0,3,1,0"
    );

    private static final List<String> DIFFICULT_PUZZLE = Arrays.asList(
            "0,9,0,7,8,5,0,1,0",
            "0,0,0,4,0,0,6,0,0",
            "0,0,0,0,0,0,0,0,0",
            "0,3,0,0,0,0,0,0,0",
            "9,0,0,0,2,0,0,0,4",
            "0,0,0,0,0,0,0,5,0",
            "0,0,0,0,0,0,0,0,0",
            "0,0,9,0,0,8,0,0,0",
            "0,4,0,3,6,1,0,7,0"
    );

    public static void main(String[] args) {
        var field = new Field();
        field.init(DIFFICULT_PUZZLE);

        var solver = new Solver();
        var result = solver.solve(field);

        result.ifPresent(System.out::println);

        System.out.println("[ " + Field.getQuantityOfFields() + " ] attempts were made to find a solution." );
    }
}
