package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Stack;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    static List<String> input;

    public static int getSolutionPart1() {
        return input.stream().map(App::getErrorScore).reduce(0, (a, b) -> a + b);
    }

    public static long getSolutionPart2() {
        var incompleteLines = input.stream().filter(Predicate.not(App::isCorrupted)).collect(Collectors.toList());
        var scores = incompleteLines.stream().map(App::getCompletionScore).sorted().collect(Collectors.toList());

        return scores.get(scores.size()/2);
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2());} else {System.out.println(getSolutionPart1());}
    }

    static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename)).collect(Collectors.toList());
    }

    static boolean isCorrupted(String line) {
        return getErrorScore(line) != 0;
    }
    static int getErrorScore(String line) {
        Stack<Character> stack = new Stack<>();

        for (char c : line.toCharArray()) {
            if (c == '(' || c == '[' || c == '{' || c == '<') {
                stack.push(c);
            } else if (c == ')' || c == ']' || c == '}' || c == '>') {
                char matching = stack.pop();
                switch (c) {
                    case ')': if (matching != '(') return 3; break;
                    case ']': if (matching != '[') return 57; break;
                    case '}': if (matching != '{') return 1197; break;
                    case '>': if (matching != '<') return 25137; break;
                }
            }
        }

        return 0;
    }

    static long getCompletionScore(String line) {
        Stack<Character> stack = new Stack<>();
        long score = 0;

        for (char c : line.toCharArray()) {
            if (c == '(' || c == '[' || c == '{' || c == '<')
                stack.push(c);
            if (c == ')' || c == ']' || c == '}' || c == '>')
                stack.pop();
        }

        while (!stack.empty()) {
            char c = stack.pop();
            switch (c) {
                case '(': score = score*5 + 1; break;
                case '[': score = score*5 + 2; break;
                case '{': score = score*5 + 3; break;
                case '<': score = score*5 + 4; break;
            }
        }

        return score;
    }
}