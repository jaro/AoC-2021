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
        polymer.process(20);
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
        Polymer polymer = new Polymer();
        polymer.startPolymer = input.get(0);
        for (int i=2;i<input.size();i++) {
            var mapping = input.get(i).split("->");
            polymer.transform.put(mapping[0].trim(), mapping[1].trim());
        }

        return polymer;
    }
}

class Polymer {
    String startPolymer;
    String currentPolymer;
    Map<String, String> transform = new HashMap<>();

    String process(int steps) {
        currentPolymer = startPolymer;

        for (int i=0;i<steps;i++) {
            StringBuilder builder = new StringBuilder(currentPolymer);
            for(int pos=currentPolymer.length()-1;pos>0;pos--) {
                String sub = transform.get(currentPolymer.substring(pos-1, pos+1));
                builder.insert(pos, sub);
            }
            currentPolymer = builder.toString();
            System.out.println("Step: " + i);
        }
        return currentPolymer;
    }

    List<Long> getCount() {
        long b=0, n=0, h=0, c=0;

        for (char ch : currentPolymer.toCharArray()) {
            switch (ch) {
                case 'B': b++; break;
                case 'N': n++; break;
                case 'H': h++; break;
                case 'C': c++; break;
            }
        }

        return Arrays.asList(b,n,h,c);
    }

    long getScore() {
        var list = getCount();
        var sorted = list.stream().sorted().collect(Collectors.toList());

        return sorted.get(sorted.size()-1) - sorted.get(0);
    }
}