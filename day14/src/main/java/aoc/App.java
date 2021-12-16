package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    static List<String> input;

    public static long getSolutionPart1() {
        var polymer = createPolymer(input);

        polymer.process(10);

        return polymer.getScore();
    }

    public static long getSolutionPart2() {
        var polymer = createPolymer(input);

        polymer.process(40);

        return polymer.getScore();
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2());} else {System.out.println(getSolutionPart1());}
    }

    static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename)).collect(Collectors.toList());
    }

    static Polymer createPolymer(List<String> input) {
        Map<String, Long> count = new HashMap<>();
        Map<String, String[]> transform = new HashMap<>();
        String startPolymer = input.get(0);

        for (int i=2;i<input.size();i++) {
            var mapping = input.get(i).split("->");
            var polymer = mapping[0].trim();
            var insertion = mapping[1].trim();

            transform.put(polymer, new String[] {polymer.substring(0,1)+insertion, insertion + polymer.substring(1)});
            count.put(polymer, 0l);
        }

        for (int i=0;i<startPolymer.length()-1;i++) {
            var polymer = startPolymer.substring(i, i+2);
            Long value = count.get(polymer) + 1;
            count.put(polymer, value++);
        }

        Polymer polymer = new Polymer();
        polymer.transform = transform;
        polymer.count = count;

        return polymer;
    }
}

class Polymer {
    Map<String, String[]> transform = new HashMap<>();
    Map<String, Long> count = new HashMap<>();

    void process(int steps) {
        for (int i=0;i<steps;i++) {
            Map<String, Long> newCount = new HashMap<>();
            for (Map.Entry<String,Long> entry: count.entrySet()) {
                if (entry.getValue() > 0) {
                    var newPolymers = transform.get(entry.getKey());
                    var pol1Count = newCount.getOrDefault(newPolymers[0], 0l);
                    var pol2Count = newCount.getOrDefault(newPolymers[1], 0l);
                    newCount.put(newPolymers[0], entry.getValue() + pol1Count);
                    newCount.put(newPolymers[1], entry.getValue() + pol2Count);
                }
            }

            count = newCount;
        }
    }

    long getScore() {
        Map<Character, Long> score = new HashMap<>();
        for (Map.Entry<String, Long> entry : count.entrySet()) {
            for(char c : entry.getKey().toCharArray()) {
                var value = score.getOrDefault(c, 0l);
                value += entry.getValue();
                score.put(c, value);
            }
        }

        var sorted = score.values().stream().map(v -> Math.ceil(v/2.0)).sorted().collect(Collectors.toList());

        return sorted.get(sorted.size()-1).longValue() - sorted.get(0).longValue();
    }
}