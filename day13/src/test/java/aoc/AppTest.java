package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {
    static List<String> input = Arrays.asList("6,10","0,14","9,10","0,3","10,4","4,11","6,0","6,12","4,1","0,13","10,12","3,4","3,0","8,4","1,10","2,14","8,10","9,0","","fold along y=7","fold along x=5");

    @BeforeEach void init() throws IOException {
        App.input = App.parseInput(input);
    }

    @Test void part1SumsInput() {
        assertEquals(17, App.getSolutionPart1());
    }

    @Test void part2MultipliesInput() {
        assertEquals(16, App.getSolutionPart2());
    }
}
