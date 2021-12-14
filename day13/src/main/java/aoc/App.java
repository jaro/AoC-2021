package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    static Paper input;

    public static int getSolutionPart1() {
        Instruction instruction = input.instructions.get(0);
        input.doInstruction(instruction);

        return input.count();
    }


    public static int getSolutionPart2() {
        for (Instruction instruction : input.instructions) {
            input.doInstruction(instruction);
        }

        input.print();

        return input.count();
    }

    public static void main(String[] args) throws IOException {
        var lines = Files.lines(Path.of("input.txt")).collect(Collectors.toList());
        input = parseInput(lines); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2());} else {System.out.println(getSolutionPart1());}
    }

    static Paper parseInput(List<String> allRows) throws IOException {
        Paper paper = new Paper();
        List<Integer[]> pointList = new ArrayList<>();

        for (String row : allRows) {
            if (row.startsWith("fold along")) {
                String[] cmd = row.replace("fold along", "").trim().split("=");
                Instruction instruction = new Instruction(cmd[0].trim(), Integer.parseInt(cmd[1].trim()));
                paper.instructions.add(instruction);
            } else if(!row.trim().isBlank()) {
                String[] pointStr = row.trim().split(",");
                Integer[] point = new Integer[2];
                point[0] = Integer.parseInt(pointStr[0].trim());
                point[1] = Integer.parseInt(pointStr[1].trim());
                pointList.add(point);
            }
        }

        paper.width = pointList.stream().mapToInt(p -> p[0]).max().orElseThrow(NoSuchElementException::new) + 1;
        paper.height = pointList.stream().mapToInt(p -> p[1]).max().orElseThrow(NoSuchElementException::new) + 1;

        char[][] points = new char[paper.width][paper.height];
        for (int y=0;y<paper.height;y++) {
            for (int x=0;x<paper.width;x++) {
                points[x][y] = '.';
            }
        }
        for (Integer[] point : pointList) {
            points[point[0]][point[1]] = '#';
        }

        paper.grid = points;

        return paper;
    }
}

class Paper {
    List<Instruction> instructions = new ArrayList<>();
    char[][] grid;
    int height;
    int width;

    void print() {
        for (int y=0;y<height;y++) {
            for (int x=0;x<width;x++) {
                System.out.print(grid[x][y]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public void doInstruction(Instruction instruction) {
        if (instruction.direction == Instruction.Direction.X) {
            for(int leftX=instruction.value-1, rightX=instruction.value+1; leftX>=0 && rightX<width;leftX--, rightX++) {
                for (int y=0;y<height;y++) {
                    grid[leftX][y] = merge(grid[leftX][y], grid[rightX][y]);
                }
            }
            width = instruction.value;
        }

        if (instruction.direction == Instruction.Direction.Y) {
            for(int upY=instruction.value-1, downY=instruction.value+1; upY>=0 && downY<height;upY--, downY++) {
                for (int x=0;x<width;x++) {
                    grid[x][upY] = merge(grid[x][upY], grid[x][downY]);
                }
            }
            height = instruction.value;
        }
    }

    char merge(char c1, char c2) {
        if (c1 == '#' || c2 == '#')
            return '#';

        return '.';
    }

    int count() {
        int dots = 0;

        for (int y=0;y<height;y++) {
            for (int x=0;x<width;x++) {
                if (grid[x][y] == '#') dots++;
            }
        }

        return dots;
    }
}

class Instruction {
    enum Direction{X,Y;}
    Direction direction;
    int value;

    Instruction(String dirStr, int value) {
        this.value = value;
        if (dirStr.trim().equals("x")) {
            direction = Direction.X;
        }
        if (dirStr.trim().equals("y")) {
            direction = Direction.Y;
        }
    }
}