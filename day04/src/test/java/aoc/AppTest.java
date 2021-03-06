package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTest {
    static Game game;

    @BeforeAll static void init() throws IOException{
        game = App.parseInput("testinput.txt");
    }

    @Test void part1SumsInput() {
        assertEquals(4512, App.getSolutionPart1(game));
    }

    @Test void part2MultipliesInput() {
        assertEquals(1924, App.getSolutionPart2(game));
    }


}
