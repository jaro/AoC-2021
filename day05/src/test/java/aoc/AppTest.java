package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AppTest {
    static Map map;

    @BeforeAll static void init() throws IOException{
        map = App.parseInput("testinput.txt", 10);
    }

    @Test void part1SumsInput() {
        assertEquals(5, App.getSolutionPart1(map));
    }

    @Test void part2MultipliesInput() {
        assertEquals(12, App.getSolutionPart2(map));
    }
}
