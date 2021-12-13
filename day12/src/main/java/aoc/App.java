package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {
    static List<String> input;

    public static int getSolutionPart1() {
        var graph = buildGraph(false);
        var visited = new HashSet<String>();
        visited.add("start");

        var fullPaths = dfs(graph.start, visited).stream().filter(s -> s.endsWith("end")).collect(Collectors.toList());
        return fullPaths.size();
    }

    public static long getSolutionPart2() {
        var graph = buildGraph(true);
        var visited = new HashSet<String>();
        var allPaths = new HashSet<String>();
        visited.add("start");

        do {
            allPaths.addAll(dfs(graph.start, visited).stream().filter(s -> s.endsWith("end")).collect(Collectors.toList()));
        } while(graph.nextSmallCave());

        return allPaths.size();
    }

    static Graph buildGraph(boolean visitSmalCaveTwice) {
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

        Graph graph = new Graph();
        graph.allNodes = new ArrayList<>(nodes.values());
        graph.allNodes.get(0).visitOneCaveTwice=true;
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

    static List<String> dfs(Node startNode, Set<String> visited) {
        List<String> result = new ArrayList<>();

        var adjacent = startNode.getUnvisitedAdjacent(visited);

        if (adjacent.isEmpty() || "end".equals(startNode.name)) {
            var path = new ArrayList<String>();
            path.add(startNode.name);
            return path;
        }

        for (Node next : adjacent) {
            var path = dfs(next, next.updateVisited(new HashSet<>(visited)));
            result.addAll(path.stream().map(l -> new String(startNode.name + "-" + l)).collect(Collectors.toList()));
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
    boolean visitOneCaveTwice=false;

    Node(String name) {
        this.name = name;
        bigCave = name.equals(name.toUpperCase());
    }

    List<Node> getUnvisitedAdjacent(Set<String> visited) {
        return adjacent.stream().filter(Predicate.not(n -> visited.contains(n.name))).collect(Collectors.toList());
    }

    public Set<String> updateVisited(Set<String> visited) {
        if (!bigCave) {
            if (!visitOneCaveTwice || visited.contains("smalcave")) {
                visited.add(name);
            } else {
                visited.add("smalcave");
            }
        }
        return visited;
    }
}

class Graph {
    int index=0;
    Node start;
    Node end;
    List<Node> allNodes;

    boolean nextSmallCave() {
        if (index >= allNodes.size()-1)
            return false;

        allNodes.get(index).visitOneCaveTwice = false;
        allNodes.get(++index).visitOneCaveTwice = true;

        return true;
    }
}