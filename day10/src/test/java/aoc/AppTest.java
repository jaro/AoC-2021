package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {

    @BeforeEach void init() throws IOException {
        App.input = App.parseInput("testinput.txt");
    }

    @Test void part1SumsInput() {
        assertEquals(26397, App.getSolutionPart1());
    }

    @Test void part2MultipliesInput() {
        assertEquals(288957, App.getSolutionPart2());
    }
}
