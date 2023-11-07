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

    public static void main(String[] args) {
        var field = new Field();
        field.init(SIMPLE_PUZZLE);
        System.out.println(field);
    }


}
