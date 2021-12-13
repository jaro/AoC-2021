package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    static Octopus[][] input;

    public static int getSolutionPart1(int steps) {
        int flashes = 0;

        for (int days = 0; days < steps;days++) {

            List<Octopus> affected = new ArrayList<>();

            for (int y = 0; y < input.length; y++) {
                for (int x = 0; x < input[0].length; x++) {
                    Octopus o = input[y][x];
                    affected.addAll(o.boost());
                    flashes += o.getFlashed();
                }
            }

            flashes += handleAffected(affected);

            for (int y = 0; y < input.length; y++) {
                for (int x = 0; x < input[0].length; x++) {
                    Octopus o = input[y][x];
                    o.resetFlashes();
                }
            }

            print();
        }

        return flashes;
    }

    static int handleAffected(List<Octopus> octopuses) {
        List<Octopus> affected = new ArrayList<>();
        int flashes = 0;
        for (Octopus o : octopuses) {
            affected.addAll(o.boost());
            flashes += o.getFlashed();
        }
        if (!affected.isEmpty()) {
            flashes += handleAffected(affected);
        }

        return flashes;
    }


    public static int getSolutionPart2(int steps) {
        int flashes = 0;
        for (int days = 0; days < steps;days++) {

            List<Octopus> affected = new ArrayList<>();

            for (int y = 0; y < input.length; y++) {
                for (int x = 0; x < input[0].length; x++) {
                    Octopus o = input[y][x];
                    affected.addAll(o.boost());
                    flashes += o.getFlashed();
                }
            }

            flashes += handleAffected(affected);

            int numbOfFlashed = 0;
            for (int y = 0; y < input.length; y++) {
                for (int x = 0; x < input[0].length; x++) {
                    Octopus o = input[y][x];
                    numbOfFlashed += o.resetFlashes();
                }
            }

            if (numbOfFlashed == 100) {
                return days+1;
            }
        }

        return -1;
    }

    static  void print() {
        for (int y = 0; y < input.length; y++) {
            for (int x = 0; x < input[0].length; x++) {
                Octopus o = input[y][x];
                System.out.print(o.energy + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2(500));} else {System.out.println(getSolutionPart1(100));}
    }

    static Octopus[][] parseInput(String filename) throws IOException {
        Octopus[][] grid;
        int lineNo = 0;
        var lines = Files.lines(Path.of(filename)).collect(Collectors.toList());
        grid = new Octopus[lines.size()][];
        for (String row : lines) {
            Octopus[] octopuses = new Octopus[row.length()];
            int index=0;
            for (char c : row.toCharArray()) {
                Octopus o = new Octopus(index ,lineNo, Character.getNumericValue(c));
                octopuses[index++] = o;
            }
            grid[lineNo++] = octopuses;
        }

        return grid;
    }
}

class Octopus {
    int x, y;
    int energy;
    boolean flashed = false;

    Octopus(int x, int y, int energy) {
        this.x = x;
        this.y = y;
        this.energy = energy;
    }

    int resetFlashes() {
        boolean hasFlashed = flashed;

        if (energy > 9) {
            flashed = false;
            energy = 0;
        }
        return hasFlashed ? 1 : 0;
    }

    List<Octopus> boost() {
        if (++energy > 9 && !flashed) {
            return getSurroundingOctopuses();
        } else {
            return Collections.emptyList();
        }
    }

    int getFlashed() {
        if (energy > 9 && !flashed) {
            flashed = true;
            return 1;
        }
        return 0;
    }

    List<Octopus> getSurroundingOctopuses() {
        int x = this.x;
        int y = this.y;
        List<Octopus> octopuses = new ArrayList<>();

        if (x>0) {
            octopuses.add(App.input[y][x-1]);
        }
        if (x<(App.input[0].length-1)) {
            octopuses.add(App.input[y][x+1]);
        }
        if(y>0) {
            octopuses.add(App.input[y-1][x]);
        }
        if(y<(App.input.length-1)) {
            octopuses.add(App.input[y+1][x]);
        }
        if (x>0 && y>0) {
            octopuses.add(App.input[y-1][x-1]);
        }
        if (x>0 && y<(App.input.length-1)) {
            octopuses.add(App.input[y+1][x-1]);
        }
        if (y>0 && x<(App.input.length-1)) {
            octopuses.add(App.input[y-1][x+1]);
        }
        if (x<(App.input.length-1) && y<(App.input.length-1)) {
            octopuses.add(App.input[y+1][x+1]);
        }

        return octopuses;
    }
}