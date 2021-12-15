package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class AppTest {

    @Test void part1SumsInput() throws IOException {
        App.input = App.parseInput("testinput.txt");
        assertEquals(40, App.getSolutionPart1());
    }

    @Test void part2MultipliesInput() throws IOException {
        App.input = App.parseInput("testinput.txt");
        assertEquals(315, App.getSolutionPart2());
    }
}
