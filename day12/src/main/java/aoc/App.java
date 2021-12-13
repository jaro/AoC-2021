package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    static List<String> input;

    public static int getSolutionPart1() {
        var graph = buildGraph();

        var paths = dfs(graph.start, graph.end);

        paths.stream().forEach(System.out::println);
        return paths.size();
    }

    public static long getSolutionPart2() {
        return 0;
    }

    static Graph buildGraph() {
        Graph graph = new Graph();
        Map<String, Node> nodes = new HashMap<>();

        for (String row : input) {
            var parts = row.split("-");
            String node1Name = parts[0].trim();
            String node2Name = parts[1].trim();
            Node node1 = nodes.get(node1Name);
            Node node2 = nodes.get(node2Name);
            if (node1 == null) {
                node1 = new Node(node1Name);
                nodes.put(node1Name, node1);
            }
            if (node2 == null) {
                node2 = new Node(node2Name);
                nodes.put(node2Name, node2);
            }
            node1.adjacent.add(node2);
            node2.adjacent.add(node1);
        }

        for (Node node : nodes.values()) {
            if ("start".equals(node.name)) {
                graph.start = node;
            }
            if ("end".equals(node.name)) {
                graph.end = node;
            }
        }

        return graph;
    }

    static List<String> dfs(Node startNode, Node target) {
        List<String> result = new ArrayList<>();
        startNode.setVisited();

        var adjacent = startNode.getUnvisitedAdjacent();

        if (startNode == target || adjacent.isEmpty()) {
            var path = new ArrayList<String>();
            path.add("->" + startNode.name);
            return path;
        }

        for (Node next : adjacent) {
            if (!next.isVisited()) {
                var path = dfs(next, target);
                result.addAll(path.stream().map(l -> new String(l + "->" +startNode.name)).collect(Collectors.toList()));
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2());} else {System.out.println(getSolutionPart1());}
    }

    static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename)).collect(Collectors.toList());
    }
}

class Node {
    List<Node> adjacent = new ArrayList<>();
    String name;
    boolean bigCave = false;
    private boolean isVisited = false;

    Node(String name) {
        this.name = name;
        bigCave = name.equals(name.toUpperCase());
    }

    List<Node> getUnvisitedAdjacent() {
        return adjacent.stream().filter(n -> !n.isVisited).collect(Collectors.toList());
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited() {
        if(bigCave || name.equals("end"))
            return;
        isVisited = true;
    }
}

class Graph {
    Node start;
    Node end;
}