package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class App {
    static List<Integer> input;

    public static Long getSolutionPart1(int days) {
        Ecosystem system = new Ecosystem(input);
        system.print();
        return system.calculateDays(days);
    }

    public static Long getSolutionPart2(int days) {
        Ecosystem system = new Ecosystem(input);
        system.print();
        return system.calculateDays(days);
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2(256));} else {System.out.println(getSolutionPart1(80));}
    }

    private static List<Integer> parseInput(String filename) throws IOException {
        var lines = Files.lines(Path.of(filename)).collect(Collectors.toList());
        var fish = Arrays.asList(lines.get(0).split(","));
        var ages = fish.stream().map(Integer::parseInt).collect(Collectors.toList());

        return ages;
    }
}

class Fish {
    int timer;

    Fish(int timer) {
        this.timer = timer;
    }

    Optional<Fish> addDay() {
        Optional<Fish> fish = Optional.empty();

        if (timer-- == 0) {
            timer = 6;
            Fish newFish = new Fish(8);
            fish = Optional.of(newFish);
        }

        return fish;
    }

    public String toString() {
        return timer + ",";
    }
}

class Ecosystem {
    List<Fish> fishList = new ArrayList<>();

    Ecosystem(List<Integer> initialFishList) {
        for (Integer timer : initialFishList) {
            fishList.add(new Fish(timer));
        }
    }

    long calculateDays(int days) {
        for (int day=0;day<days;day++) {
            List<Fish> newFishList = new ArrayList<>();

            for (Fish fish : fishList) {
                var newFish = fish.addDay();
                if (newFish.isPresent()) {
                    newFishList.add(newFish.get());
                }
            }
            fishList.addAll(newFishList);
            //print();
        }
        return fishList.size();
    }

    void print() {
        fishList.stream().forEach(System.out::print);
        System.out.println("");
    }
}