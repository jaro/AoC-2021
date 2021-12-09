package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    static int[] input;

    public static int getSolutionPart1() {
        List<Integer> costList = new ArrayList<>();
        for (int target=0;target<input.length;target++) {
            int totalCost = 0;
            for (int pos=0;pos<input.length;pos++){
                int distance = Math.abs(target-pos);
                int cost = distance*input[pos];
                totalCost += cost;
            }

            costList.add(totalCost);
        }

        return costList.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
    }


    public static int getSolutionPart2() {
        List<Integer> costList = new ArrayList<>();
        for (int target=0;target<input.length;target++) {
            int totalCost = 0;
            for (int pos=0;pos<input.length;pos++){
                int distance = Math.abs(target-pos);
                int distanceCost = ((distance*(distance+1))/2);
                int cost = distanceCost*input[pos];
                totalCost += cost;
            }

            costList.add(totalCost);
        }

        return costList.stream().mapToInt(v -> v).min().orElseThrow(NoSuchElementException::new);
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2());} else {System.out.println(getSolutionPart1());}
    }

    private static int[] parseInput(String filename) throws IOException {
        var lines = Files.lines(Path.of(filename)).collect(Collectors.toList());
        var crabs = Arrays.asList(lines.get(0).split(","));
        var numbers = crabs.stream().map(Integer::parseInt).collect(Collectors.toList());

        System.out.println(numbers.stream().mapToInt(v -> v).max().orElseThrow(NoSuchElementException::new));

        int[] positions = new int[1937];
        for (String crab : crabs) {
            positions[Integer.parseInt(crab)]++;
        }

        return positions;
    }
}