package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {
    private static final List<String> INPUT = List.of("00100", "11110", "10110", "10111", "10101","01111", "00111","11100","10000","11001","00010","01010");

    @Test void part1SumsInput() {
        assertEquals(198, App.getSolutionPart1(INPUT));
    }

    @Test void part2MultipliesInput() {
        assertEquals(230, App.getSolutionPart2(INPUT));
    }
}
