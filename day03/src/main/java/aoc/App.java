package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private final List<String> input;
    public App(List<String> input) { this.input = input; }

    public Integer getSolutionPart1() {
        int gamma = 0; int epsilon = 0;

        String[] values = getGammaAndEpsilon(input);
        gamma = Integer.parseInt(values[0], 2);
        epsilon = Integer.parseInt(values[1], 2);

        return gamma * epsilon;
    }

    private String[] getGammaAndEpsilon(List<String> list) {
        var size = list.get(0).length();
        int[] count = new int[size];
        for (String cmd : list) {
            for (int pos=0;pos<size;pos++) {
                if (cmd.charAt(pos) == '1') {
                    count[pos] = count[pos]+1;
                }
            }
        }

        String gamma = "";
        String epsilon = "";
        int lenght = list.size();
        for (int i:count) {
            int ones = i;
            int zeroes = lenght-i;

            if (i >= zeroes) {
                gamma = gamma + "1";
                epsilon = epsilon + "0";
            } else {
                gamma = gamma + "0";
                epsilon = epsilon + "1";
            }
        }

        return new String[] {gamma, epsilon};
    }

    public Integer getSolutionPart2() {
        int oxygen = 0;
        int CO2 = 0;

        var values = getGammaAndEpsilon(input);
        char[] gammaSign = values[0].toCharArray();
        System.out.println("List: " + input);
        System.out.println("Gamma: " + String.valueOf(gammaSign));

        List<String> list = new ArrayList<>(input);

        for (int pos=0;pos < gammaSign.length;pos++) {
            List<String> keep = new ArrayList<>();
            for (String row: list) {
                if (gammaSign[pos] == row.charAt(pos)) {
                    keep.add(row);
                }
            }
            list = keep;
            values = getGammaAndEpsilon(list);
            gammaSign = values[0].toCharArray();
            System.out.println("List: " + keep);
            System.out.println("Gamma: " + String.valueOf(gammaSign));
            if (list.size() == 1) {
                oxygen = Integer.parseInt(list.get(0), 2);
                break;
            }
        }

        list = new ArrayList<>(input);
        values = getGammaAndEpsilon(input);
        var epsilonSign = values[1].toCharArray();


        for (int pos=0;pos < epsilonSign.length;pos++) {
            List<String> keep = new ArrayList<>();
            for (String row: list) {
                if (epsilonSign[pos] == row.charAt(pos)) {
                    keep.add(row);
                }
            }

            list = keep;
            values = getGammaAndEpsilon(list);
            epsilonSign = values[1].toCharArray();
            System.out.println("List: " + keep);
            System.out.println("Epsilon: " + String.valueOf(epsilonSign));
            if (list.size() == 1) {
                CO2 = Integer.parseInt(list.get(0), 2);
                break;
            }
        }

        System.out.println("Oxygen: " + oxygen);
        System.out.println("CO2 " + CO2);

        return oxygen * CO2;
    }
    public static void main(String[] args) throws IOException {
        List<String> input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(new App(input).getSolutionPart2());} else {System.out.println(new App(input).getSolutionPart1());}
    }
    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename)) .collect(Collectors.toList());
    }
}