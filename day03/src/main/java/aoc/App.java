package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static Integer getSolutionPart1(final List<String> input) {
        int[] gammaAndEpsilon = getGammaAndEpsilon(input);
        return gammaAndEpsilon[0] * gammaAndEpsilon[1];
    }

    private static int[] getGammaAndEpsilon(List<String> list) {
        var size = list.get(0).length();
        int[] count = new int[size];
        for (String cmd : list) {
            for (int pos=0;pos<size;pos++) {
                if (cmd.charAt(pos) == '1') {
                    count[pos] = count[pos]+1;
                }
            }
        }

        int gamma=0, epsilon=0, lenght = list.size();

        for (int pos=0; pos<count.length;pos++) {
            if (count[pos] >= lenght-count[pos]) {
                gamma += 0b1 << count.length-1-pos;
            } else {
                epsilon += 0b1 << count.length-1-pos;
            }
        }

        return new int[]{gamma, epsilon};
    }

    public static Integer getSolutionPart2(final List<String> input) {
        int oxygen = 0, CO2 = 0, reportLength = input.get(0).length();

        var StringValues = getGammaAndEpsilon(input);
        char[] gammaSign = binaryToCharArr(StringValues[0], reportLength);

        List<String> list = new ArrayList<>(input);

        for (int pos=0;pos < gammaSign.length;pos++) {
            List<String> keep = new ArrayList<>();
            for (String row: list) {
                if (gammaSign[pos] == row.charAt(pos)) {
                    keep.add(row);
                }
            }
            list = keep;

            StringValues = getGammaAndEpsilon(list);
            gammaSign = binaryToCharArr(StringValues[0], reportLength);

            if (list.size() == 1) {
                oxygen = Integer.parseInt(list.get(0), 2);
                break;
            }
        }

        list = new ArrayList<>(input);

        StringValues = getGammaAndEpsilon(input);
        var epsilonSign = binaryToCharArr(StringValues[1], reportLength);

        for (int pos=0;pos < epsilonSign.length;pos++) {
            List<String> keep = new ArrayList<>();
            for (String row: list) {
                if (epsilonSign[pos] == row.charAt(pos)) {
                    keep.add(row);
                }
            }

            list = keep;
            StringValues = getGammaAndEpsilon(list);
            epsilonSign = binaryToCharArr(StringValues[1], reportLength);

            if (list.size() == 1) {
                CO2 = Integer.parseInt(list.get(0), 2);
                break;
            }
        }

        return oxygen * CO2;
    }

    private static char[] binaryToCharArr(int value, int size) {
        return String.format("%"+size+"s", Integer.toBinaryString(value)).replace(' ', '0').toCharArray();
    }

    public static void main(String[] args) throws IOException {
        List<String> input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2(input));} else {System.out.println(getSolutionPart1(input));}
    }
    private static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename)) .collect(Collectors.toList());
    }
}