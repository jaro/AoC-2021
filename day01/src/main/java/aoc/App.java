/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private final List<Integer> input;

    public App(List<Integer> input) {
        this.input = input;
    }

    public long countDays(int[] params) {
        if (params[1] == 0)
            return 1;
        else
        if (params[0] > 0) {
            var p = new int[2];
            p[0] = params[0]-1;
            p[1] = params[1]-1;
            return countDays(p);
        } else {
            var p = new int[2];
            p[0] = 6;
            p[1] = params[1]-1;

            var p2 = new int[2];
            p2[0] = 8;
            p2[1] = params[1]-1;
            return countDays(p) + countDays(p2);
        }
    }

    public Integer getSolutionPart1() {
        int largerThanPrevious = 0;
        int previous = input.get(0);

        for(int i=1;i<input.size();i++) {
            if (input.get(i) > previous) {
                largerThanPrevious++;
            }
            previous = input.get(i);
        }

        return largerThanPrevious;
    }

    public Integer getSolutionPart2() {
        int largerThanPrevious = 0;
        int previousSum = getSum(2);

        for(int i=3;i<input.size();i++) {
            int sum  = getSum(i);
            if (sum > previousSum) {
                largerThanPrevious++;
            }
            previousSum = sum;
        }

        return largerThanPrevious;
    }

    private int getSum(int lastIndex) {
        return input.get(lastIndex-2) + input.get(lastIndex-1) + input.get(lastIndex);
    }

    public static void main(String[] args) throws IOException {
        List<Integer> input = parseInput("input.txt");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2"))
            System.out.println(new App(input).getSolutionPart2());
        else
            System.out.println(new App(input).getSolutionPart1());
    }

    private static List<Integer> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private boolean isPrime(int number) {
        if (number < 2) return false;
        if (number % 2 == 0) return (number == 2);
        int root = (int)Math.sqrt((double)number);
        for (int i = 3; i <= root; i += 2)
        {
            if (number % i == 0) return false;
        }
        return true;
    }
}
