package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {
    static int[][] input = new int[][] {{2,1,9,9,9,4,3,2,1,0}, {3,9,8,7,8,9,4,9,2,1}, {9,8,5,6,7,8,9,8,9,2}, {8,7,6,7,8,9,6,7,8,9}, {9,8,9,9,9,6,5,6,7,8}};

    @BeforeAll static void init() {
        App.input = input;
    }

    @Test void part1SumsInput() {
        assertEquals(15, App.getSolutionPart1());
    }

    @Test void part2MultipliesInput() {
        assertEquals(1134, App.getSolutionPart2());
    }
}
