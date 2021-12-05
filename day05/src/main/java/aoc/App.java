package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static Integer getSolutionPart1(final Map map) {
        map.process(false);
        return map.getOverlaps();
    }

    public static Integer getSolutionPart2(final Map map) {
        map.process(true);
        return map.getOverlaps();
    }

    public static void main(String[] args) throws IOException {
        Map input = parseInput("input.txt", 1000); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2(input));} else {System.out.println(getSolutionPart1(input));}
    }

    static Map parseInput(String filename, int size) throws IOException {
        Map map = new Map(size);

        List<String> lines = Files.lines(Path.of(filename)).collect(Collectors.toList());

        for (String line : lines) {
            var coordinates = line.split("->");
            Point point = new Point(coordinates[0], coordinates[1]);
            map.points.add(point);

            System.out.println(point.toString());
            System.out.println("Ortogonal: " + point.isOrogonal() + " - Diagonal: " + point.isDiagonal());
        }

        return map;
    }
}

class Point {
    int startX, startY;
    int endX, endY;

    Point(String start, String end) {
        var point = start.trim().split(",");
        startX = Integer.parseInt(point[0]);
        startY = Integer.parseInt(point[1]);
        var point2 = end.trim().split(",");
        endX = Integer.parseInt(point2[0]);
        endY = Integer.parseInt(point2[1]);
    }

    boolean isOrogonal() {
        return startX == endX || startY == endY;
    }

    boolean isDiagonal() {
        return Math.abs(endX-startX) == Math.abs(endY-startY);
    }

    void normalize() {
        if (startX > endX || startY > endY) {
            int tmpX = endX;
            endX = startX;
            startX = tmpX;
            int tmpY = endY;
            endY = startY;
            startY = tmpY;
        }
    }

    public String toString() {
        return "("+startX+","+startY+"->"+endX+","+endY+")";
    }
}

class Map {
    int size;
    List<Point> points = new ArrayList<>();
    int[][] coordinates;

    Map(int size) {
        this.size = size;
        coordinates = new int[size][size];
    }

    void process(boolean useDiagonals) {
        coordinates = new int[size][size];
        for (Point point : points) {
            if (point.isOrogonal()) {
                point.normalize();
                plot(point);
            }

            if (useDiagonals && point.isDiagonal()) {
                plotDiagonal(point);
            }
        }
    }

    void plot(Point point){
        for (int y = point.startY; y <= point.endY; y++) {
            for (int x = point.startX; x <= point.endX; x++) {
                coordinates[x][y] = coordinates[x][y] + 1;
            }
        }
    }

    void plotDiagonal(Point point){
        int x = point.startX;
        int y = point.startY;
        int steps = Math.abs(point.endX-point.startX);

        for(int s=0;s<steps;s++) {
            coordinates[x][y] = coordinates[x][y] + 1;

            if (point.startX > point.endX) {
                x--;
            } else {
                x++;
            }
            if (point.startY > point.endY) {
                y--;
            } else {
                y++;
            }
        }
    }

    int getOverlaps() {
        int overlaps=0;
        for (int x=0;x<coordinates.length;x++) {
            for (int y=0;y<coordinates.length;y++) {
                if (coordinates[x][y] > 1) {
                    overlaps++;
                }
            }
        }
        return overlaps;
    }

    void showMap() {
        for (int y=0;y<coordinates.length;y++) {
            for (int x=0;x<coordinates.length;x++) {
                System.out.print(coordinates[x][y]);
            }
            System.out.println("");
        }
    }
}