package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private final List<String> input;
    public App(List<String> input) { this.input = input; }
    public Integer getSolutionPart1() {
        int depth = 0; int horizontal = 0;
        for (String cmd : input) {
            String[] cmdArr = cmd.split(" ");
            switch (cmdArr[0]) {
                case "forward": horizontal += Integer.parseInt(cmdArr[1]); break;
                case "down": depth += Integer.parseInt(cmdArr[1]); break;
                case "up": depth -= Integer.parseInt(cmdArr[1]); break;
            }
        }
        return depth * horizontal;
    }
    public Integer getSolutionPart2() {
        int aim = 0; int depth = 0; int horizontal = 0;
        for (String cmd : input) {
            String[] cmdArr = cmd.split(" "); int value = Integer.parseInt(cmdArr[1]);
            switch (cmdArr[0]) {
                case "forward": horizontal += value; depth += aim * value; break;
                case "down": aim += Integer.parseInt(cmdArr[1]); break;
                case "up": aim -= Integer.parseInt(cmdArr[1]); break;
            }
        }
        return depth * horizontal;
    }
    public static void main(String[] args) throws IOException {
        List<String> input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(new App(input).getSolutionPart2());} else {System.out.println(new App(input).getSolutionPart1());}
    }
    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename)) .collect(Collectors.toList());
    }
}