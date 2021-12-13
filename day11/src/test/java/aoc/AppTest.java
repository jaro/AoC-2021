package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AppTest {
    @BeforeAll static void init() throws IOException {
        App.input = App.parseInput("testinput.txt");
    }

    @Test void part1SumsInput() {
        assertEquals(1656, App.getSolutionPart1(100));
    }

    @Test void part2MultipliesInput() {
        assertEquals(195, App.getSolutionPart2(500));
    }
}
