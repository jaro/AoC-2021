package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {
    static List<Integer> input = Arrays.asList(3,4,3,1,2);

    @BeforeAll static void init() {App.input = input;}

    @Test void part1SumsInput() {
        assertEquals(5934, App.getSolutionPart1(80));
    }

    @Test void part2MultipliesInput() {
        //assertEquals(26984457539l, App.run());
    }
}
