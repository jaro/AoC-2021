package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {
    static int[] input = new int[] {16,1,2,0,4,2,7,1,2,14};

    @BeforeAll static void init() {
        int[] positions = new int[17];
        for (int crab : input) {
            positions[crab]++;
        }
        App.input = positions;
    }

    @Test void part1SumsInput() {
        assertEquals(37, App.getSolutionPart1());
    }

    @Test void part2MultipliesInput() {
        assertEquals(168, App.getSolutionPart2());
    }
}
