package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {

    @BeforeAll static void init() throws IOException {
        App.input = App.parseInput("testinput.txt");
    }

    @Test void part1SumsInput() {
        assertEquals(1588, App.getSolutionPart1());
    }

    @Test void part2MultipliesInput() {
        assertEquals(2188189693529l, App.getSolutionPart2());
    }
}
