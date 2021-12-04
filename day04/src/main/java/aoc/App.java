package aoc;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    public static Integer getSolutionPart1(final Game game) {
        return game.play();
    }

    public static Integer getSolutionPart2(final Game game) {
        return game.playAll();
    }

    public static void main(String[] args) throws IOException {
        Game input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2(input));} else {System.out.println(getSolutionPart1(input));}
    }

    static Game parseInput(String filename) throws IOException {
        Game game = new Game();

        List<String> lines = Files.lines(Path.of(filename)).collect(Collectors.toList());

        game.numbers = Arrays.stream(lines.get(0).split(",")).map(Integer::parseInt).collect(Collectors.toList());

        for (int row = 2; row < lines.size(); row+=6) {
            Board board = new Board();
            board.board[0] = createGameRow(lines.get(row));
            board.board[1] = createGameRow(lines.get(row+1));
            board.board[2] = createGameRow(lines.get(row+2));
            board.board[3] = createGameRow(lines.get(row+3));
            board.board[4] = createGameRow(lines.get(row+4));

            game.boards.add(board);
        }

        return game;
    }

    static int[] createGameRow(String row) {
        int[] array = new int[5];
        var numbers = row.trim().split("\s+");

        for (int i = 0; i < numbers.length; i++) {
            array[i] = Integer.parseInt(numbers[i]);
        }

        return array;
    }
}

class Game {
    List<Integer> numbers = new ArrayList<>();
    List<Board> boards = new ArrayList<>();

    int playAll() {
        List<Integer> winners = new ArrayList<>();

        for (Integer number : numbers) {
            for (Board board : boards) {
                if (board.done == false) {
                    board.mark(number);
                    if (board.isWinner()) {
                        board.done = true;
                        int sum = board.sumOfUnmarked();
                        winners.add(number * sum);
                    }
                }
            }
        }

        return winners.get(winners.size()-1);
    }

    int play() {
        for (Integer number : numbers) {
            for (Board board : boards) {
                board.mark(number);
                if (board.isWinner()) {
                    int sum = board.sumOfUnmarked();
                    return number * sum;
                }
            }
        }

        return 0;
    }
}

class Board {
    int[][] board = new int[5][5];
    boolean[][] marked = new boolean[5][5];
    boolean done = false;

    int sumOfUnmarked() {
        int sum=0;
        for (int row = 0; row < marked.length; row++) {
            for (int col = 0; col <marked[row].length; col++) {
                if (marked[row][col] == false) {
                    sum += board[row][col];
                }
            }
        }

        return sum;
    }

    void mark(int number) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col <board[row].length; col++) {
                if (board[row][col] == number) {
                    marked[row][col] = true;
                }
            }
        }
    }

    boolean isWinner() {
        //Check rows for winners
        for (int row = 0; row <marked.length; row++) {
            int numberInRow = 0;
            for (int col = 0; col <marked[row].length; col++) {
                if (marked[row][col] == true) {
                    numberInRow++;
                }
            }
            if(numberInRow == 5) return true;
            numberInRow = 0;
        }

        //Check columns for winners
        for (int row = 0; row <marked[0].length; row++) {
            int numberInRow = 0;
            for (int col = 0; col <marked[row].length; col++) {
                if (marked[col][row] == true) {
                    numberInRow++;
                }
            }
            if(numberInRow == 5) return true;
            numberInRow = 0;
        }

        return false;
    }
}