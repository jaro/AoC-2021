package aoc;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    static int[][] input;

    public static int getSolutionPart1() {
        List<Point> lowestPoints = new ArrayList<>();

        for (int y=0; y<input.length;y++) {
            for (int x=0;x<input[0].length;x++) {
                Point[] points = getSurroundingPoints(x,y);
                boolean lowest = true;
                for (Point point : points) {
                    if (input[y][x] >= point.value) {
                        lowest = false;
                        break;
                    }
                }
                if (lowest) {
                    lowestPoints.add(new Point(x, y, input[y][x]));
                }
            }
        }

        int sum = 0;
        for (Point point:lowestPoints) {
            sum += point.value+1;
        }
        return sum;
    }


    public static int getSolutionPart2() {
        List<Point> lowestPoints = new ArrayList<>();

        for (int y=0; y<input.length;y++) {
            for (int x=0;x<input[0].length;x++) {
                Point[] points = getSurroundingPoints(x,y);
                boolean lowest = true;
                for (Point point : points) {
                    if (input[y][x] >= point.value) {
                        lowest = false;
                        break;
                    }
                }
                if (lowest) {
                    lowestPoints.add(new Point(x,y,input[y][x]));
                }
            }
        }

        List<Basin> basins = new ArrayList<>();
        for (Point point : lowestPoints) {
            basins.add(getBasin(point));
        }

        var sortedBasins = basins.stream().sorted(Comparator.comparingInt(Basin::size).reversed()).collect(Collectors.toList());

        return sortedBasins.get(0).size() * sortedBasins.get(1).size() * sortedBasins.get(2).size();
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2());} else {System.out.println(getSolutionPart1());}
    }

    private static int[][] parseInput(String filename) throws IOException {
        int[][] result;
        int lineNo = 0;
        var lines = Files.lines(Path.of(filename)).collect(Collectors.toList());
        result = new int[lines.size()][];
        for (String row : lines) {
            int[] numbers = new int[row.length()];
            int index=0;
            for (char c : row.toCharArray()) {
                numbers[index++] = Character.getNumericValue(c);
            }
            result[lineNo++] = numbers;
        }

        return result;
    }

    static Basin getBasin(Point point) {
        Basin basin = new Basin();

        List<Point> pointsToCheck = new ArrayList<>();
        pointsToCheck.add(point);

        while (!pointsToCheck.isEmpty()) {
            basin.add(pointsToCheck.get(0));
            var surroundings = getSurroundingPoints(pointsToCheck.remove(0));
            for (Point p : surroundings) {
                if (p.value < 9 && !basin.contains(p)) {
                    pointsToCheck.add(p);
                }
            }
        }

        return basin;
    }

    static Point[] getSurroundingPoints(Point point) {
        return getSurroundingPoints(point.x, point.y);
    }

    static Point[] getSurroundingPoints(int x, int y) {
        List<Point> points = new ArrayList<>();

        if (x>0) {
            points.add(new Point(x-1, y, input[y][x-1]));
        }
        if (x<(input[0].length-1)) {
            points.add(new Point(x+1, y, input[y][x+1]));
        }
        if(y>0) {
            points.add(new Point(x, y-1, input[y-1][x]));
        }
        if(y<(input.length-1)) {
            points.add(new Point(x, y+1, input[y+1][x]));
        }

        return points.toArray(new Point[points.size()]);
    }
}

class Point {
    int x, y;
    int value;

    Point(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}

class Basin {
    List<Point> points = new ArrayList<>();

    void add(Point p) {
        if(!contains(p)) {
            points.add(p);
        }
    }
    int size() {
        return points.size();
    }
    boolean contains(Point p) {
        for (Point point : points) {
            if (p.x == point.x && p.y == point.y) {
                return true;
            }
        }
        return false;
    }
}