package aoc;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    static List<Row> input;
    static final String segment0 = "abcefg";
    static final String segment1 = "cf";
    static final String segment2 = "acdeg";
    static final String segment3 = "acdfg";
    static final String segment4 = "bcdf";
    static final String segment5 = "abdfg";
    static final String segment6 = "abdefg";
    static final String segment7 = "acf";
    static final String segment8 = "abcdefg";
    static final String segment9 = "abcdfg";
    static List<String> segments = Arrays.asList(segment0, segment1, segment2, segment3, segment4, segment5, segment6, segment7, segment8, segment9);

    public static int getSolutionPart1() {
        int ones=0, fours=0, sevens=0, eights=0;
        for (Row row : input) {
            ones += row.numbOfDigitsInOutput(1);
            fours += row.numbOfDigitsInOutput(4);
            sevens += row.numbOfDigitsInOutput(7);
            eights += row.numbOfDigitsInOutput(8);
        }

        return ones + fours + sevens + eights;
    }


    public static int getSolutionPart2() {
        int sum = 0;
        for(Row row : input) {
            var mapping = row.getMapping();
            String s = "";
            for (Digit dig : row.output) {
                s += String.valueOf(mapping.get(dig.segments));
            }
            System.out.println("Number: " + s);
            sum += Integer.parseInt(s);
        }

        return sum;
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2());} else {System.out.println(getSolutionPart1());}
    }

    static List<Row> parseInput(String filename) throws IOException {
        List<Row> rows = new ArrayList<>();
        var lines = Files.lines(Path.of(filename)).collect(Collectors.toList());

        for (String line : lines) {
            List<Digit> inputRows = new ArrayList<>();
            List<Digit> outputRows = new ArrayList<>();
            var inputOutput = line.split("\\|");
            var inputs = inputOutput[0].trim().split(" ");
            for (String input: inputs) {
                inputRows.add(new Digit(Row.sort(input.trim())));
            }
            var outputs = inputOutput[1].trim().split(" ");
            for (String output: outputs) {
                outputRows.add(new Digit(Row.sort(output.trim())));
            }

            rows.add(new Row(inputRows, outputRows));
        }

        return rows;
    }
}

class Row {
    List<Digit> input = new ArrayList<>();
    List<Digit> output = new ArrayList<>();

    Row(List<Digit> input, List<Digit> output) {
        this.input.addAll(input);
        this.output.addAll(output);
    }

    int numbOfDigitsInOutput(int number) {
        int count = 0;
        for (Digit digit : output) {
            if (digit.digit == number) {
                count++;
            }
        }

        return count;
    }

    Map<String, Integer> getMapping() {
        Map<String, String> mapping = new HashMap<>();
        String seg1 = "";
        String seg3 = "";
        String seg4 = "";
        String seg7 = "";

        String seg8 = "";

        for (Digit d : input) {
            if (d.digit == 1) {
                seg1 = d.segments;
            } else if (d.digit == 7) {
                seg7 = d.segments;
            } else if (d.digit == 4) {
                seg4 = d.segments;
            } else if (d.digit == 8) {
                seg8 = d.segments;
            }
        }
        String a="", b="", c="", d="", e="", f="", g="";
        a = remove(seg7, seg1);
        mapping.put("a", a);

        var seg = seg1.toCharArray();
        var first = String.valueOf(seg[0]);
        var second = String.valueOf(seg[1]);


        //Find g, d and b
        for (Digit dig : input) {
            if (dig.segments.length() == 5 && dig.segments.contains(first) && dig.segments.contains(second)) {
                seg3 = dig.segments;
                g = remove(remove(seg3, seg4), a);
                mapping.put("g", g);
                d = remove(remove(remove(dig.segments, seg1), a), g);
                mapping.put("d", d);
                b = remove(remove(seg4, seg1), d);
                mapping.put("b", b);

            }
        }


        for (Digit dig : input) {
            if (dig.segments.length() == 5 && dig.segments.contains(b)) { // Find 5
                f = remove(remove(remove(remove(dig.segments, a), d), g), b);
                mapping.put("f", f);
                c = remove(seg1, f);
                mapping.put("c", c);
                e = remove(remove(remove(seg8, seg4), a), g);
                mapping.put("e", e);
            }
        }


        System.out.println("1: " + seg1 + ", 7: " + seg7 + ", " + ", 4: " + seg4 + ", 8: " + seg8);
        System.out.println("a->" + mapping.get("a"));
        System.out.println("b->" + mapping.get("b"));
        System.out.println("c->" + mapping.get("c"));
        System.out.println("d->" + mapping.get("d"));
        System.out.println("e->" + mapping.get("e"));
        System.out.println("f->" + mapping.get("f"));
        System.out.println("g->" + mapping.get("g"));

        Map<String, Integer> result = new HashMap<>();
        String zero = a+b+c+e+f+g;
        String one = seg1;
        String two = a+c+d+e+g;
        String three = a+c+d+f+g;
        String four = seg4;
        String five = a+b+d+f+g;
        String six = a+b+d+e+f+g;
        String seven = seg7;
        String eight = seg8;
        String nine = a+b+c+d+f+g;

        result.put(sort(zero), 0);
        result.put(sort(one), 1);
        result.put(sort(two), 2);
        result.put(sort(three), 3);
        result.put(sort(four), 4);
        result.put(sort(five), 5);
        result.put(sort(six), 6);
        result.put(sort(seven), 7);
        result.put(sort(eight), 8);
        result.put(sort(nine), 9);

        return result;
    }

    static String sort(String s) {
        char tempArray[] = s.toCharArray();
        Arrays.sort(tempArray);
        return new String(tempArray);
    }

    static String remove(String source, String charsToRemove) {
        String result = source;
        for (char c : charsToRemove.toCharArray()) {
            result = result.replace(c, ' ');
        }

        return result.replaceAll(" ", "");
    }
}

/*
  0:      1:      2:      3:      4:
 aaaa    ....    aaaa    aaaa    ....
b    c  .    c  .    c  .    c  b    c
b    c  .    c  .    c  .    c  b    c
 ....    ....    dddd    dddd    dddd
e    f  .    f  e    .  .    f  .    f
e    f  .    f  e    .  .    f  .    f
 gggg    ....    gggg    gggg    ....

  5:      6:      7:      8:      9:
 aaaa    aaaa    aaaa    aaaa    aaaa
b    .  b    .  .    c  b    c  b    c
b    .  b    .  .    c  b    c  b    c
 dddd    dddd    ....    dddd    dddd
.    f  e    f  .    f  e    f  .    f
.    f  e    f  .    f  e    f  .    f
 gggg    gggg    ....    gggg    gggg
 */

// a finns i 0, 2, 3, 5, 6, 7, 8, 9
// b finns i 0, 4, 5, 6, 8, 9
// c finns i 0, 1, 2, 3, 4, 7, 8, 9
// d finns i 2, 3, 4, 5, 6, 8, 9
// e finns i 0, 2, 6, 8
// f finns i 0, 1, 3, 4, 5, 6, 7, 8, 9

class Digit {
    // 1 = 2 segments
    // 7 = 3 segments
    // 4 = 4 segments
    // 8 = 7 segments
    // 2, 3, 5 = 5 segments
    // 0, 6, 9 = 6 segments
    String segments;
    int digit = -1;
    List<Integer> possibleDigit = new ArrayList<>();

    Digit(String segments) {
        this.segments = segments;
        switch (segments.length()) {
            case 2:
                possibleDigit.add(1);
                digit = 1;
                break;
            case 3:
                possibleDigit.add(7);
                digit = 7;
                break;
            case 4:
                possibleDigit.add(4);
                digit = 4;
                break;
            case 7:
                possibleDigit.add(8);
                digit = 8;
                break;
            case 5:
                possibleDigit.add(2);
                possibleDigit.add(3);
                possibleDigit.add(5);
                break;
            case 6:
                possibleDigit.add(0);
                possibleDigit.add(6);
                possibleDigit.add(9);
                break;
        }
    }
}