package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App {
    static List<Integer> input;

    public static Long getSolutionPart1(int days) {
        var app = new App();
        var list = Arrays.asList(3,4,3,1,2);
        //var list = Arrays.asList(4,2,4,1,5,1,2,2,4,1,1,2,2,2,4,4,1,2,1,1,4,1,2,1,2,2,2,2,5,2,2,3,1,4,4,4,1,2,3,4,4,5,4,3,5,1,2,5,1,1,5,5,1,4,4,5,1,3,1,4,5,5,5,4,1,2,3,4,2,1,2,1,2,2,1,5,5,1,1,1,1,5,2,2,2,4,2,4,2,4,2,1,2,1,2,4,2,4,1,3,5,5,2,4,4,2,2,2,2,3,3,2,1,1,1,1,4,3,2,5,4,3,5,3,1,5,5,2,4,1,1,2,1,3,5,1,5,3,1,3,1,4,5,1,1,3,2,1,1,1,5,2,1,2,4,2,3,3,2,3,5,1,5,1,2,1,5,2,4,1,2,4,4,1,5,1,1,5,2,2,5,5,3,1,2,2,1,1,4,1,5,4,5,5,2,2,1,1,2,5,4,3,2,2,5,4,2,5,4,4,2,3,1,1,1,5,5,4,5,3,2,5,3,4,5,1,4,1,1,3,4,4,1,1,5,1,4,1,2,1,4,1,1,3,1,5,2,5,1,5,2,5,2,5,4,1,1,4,4,2,3,1,5,2,5,1,5,2,1,1,1,2,1,1,1,4,4,5,4,4,1,4,2,2,2,5,3,2,4,4,5,5,1,1,1,1,3,1,2,1);
        long count = 0;
        for(int i : list) {
            System.out.println("Fish " + i);
            int[] p = new int[2];
            p[0] = i;
            p[1] = 256;
            count += app.countDays(p);
        }

        System.out.println(count);
        return count;
    }

    public static long run() {
        final List<Long> lanternfishLifecycle = Arrays.asList(0L,0L,0L,0L,0L,0L,0L,0L,0L);
        var list = Arrays.asList(4,2,4,1,5,1,2,2,4,1,1,2,2,2,4,4,1,2,1,1,4,1,2,1,2,2,2,2,5,2,2,3,1,4,4,4,1,2,3,4,4,5,4,3,5,1,2,5,1,1,5,5,1,4,4,5,1,3,1,4,5,5,5,4,1,2,3,4,2,1,2,1,2,2,1,5,5,1,1,1,1,5,2,2,2,4,2,4,2,4,2,1,2,1,2,4,2,4,1,3,5,5,2,4,4,2,2,2,2,3,3,2,1,1,1,1,4,3,2,5,4,3,5,3,1,5,5,2,4,1,1,2,1,3,5,1,5,3,1,3,1,4,5,1,1,3,2,1,1,1,5,2,1,2,4,2,3,3,2,3,5,1,5,1,2,1,5,2,4,1,2,4,4,1,5,1,1,5,2,2,5,5,3,1,2,2,1,1,4,1,5,4,5,5,2,2,1,1,2,5,4,3,2,2,5,4,2,5,4,4,2,3,1,1,1,5,5,4,5,3,2,5,3,4,5,1,4,1,1,3,4,4,1,1,5,1,4,1,2,1,4,1,1,3,1,5,2,5,1,5,2,5,2,5,4,1,1,4,4,2,3,1,5,2,5,1,5,2,1,1,1,2,1,1,1,4,4,5,4,4,1,4,2,2,2,5,3,2,4,4,5,5,1,1,1,1,3,1,2,1);

        list.forEach(stage -> lanternfishLifecycle.set(stage, lanternfishLifecycle.get(stage) + 1 ));

        long count = new App().simulate(lanternfishLifecycle, 256);
        System.out.println(count);

        return count;
    }

    public long simulate(final List<Long> lanternfishLifecycle, final int numberOfDays) {
        IntStream.rangeClosed(1, numberOfDays).forEach(day -> {
            Collections.rotate(lanternfishLifecycle, -1);
            lanternfishLifecycle.set(6, lanternfishLifecycle.get(6) + lanternfishLifecycle.get(8));
        });
        return lanternfishLifecycle.stream().reduce(Long::sum).orElseThrow();
    }

    public static Long getSolutionPart2(int days) {
        Ecosystem system = new Ecosystem(input);
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

    public long countDays(int[] params) {
        //System.out.println("day: " + params[1] + " - life: " + params[0]);
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
}

class Fish {
    int timer;

    Fish(int timer) {
        this.timer = timer;
    }

    long countDays(int days) {
        if (days == 0)
            return 1;
        else
            if (timer-- > 0) {
                return countDays(days - 1);
            } else {
                timer = 6;
                return new Fish(8).countDays(days -1) + countDays(days - 1);
            }
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
        long count = 0;

        for (Fish fish : fishList) {
           count += fish.countDays(days);
        }

        return count;
    }
}