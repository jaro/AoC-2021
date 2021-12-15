package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    static int[][] input;

    public static int getSolutionPart1() {
        Graph graph = createGraph();
        var result = calculateShortestPathFromSource(graph, graph.firstNode);

        Node finalNode = null;
        for (Node n : graph.nodes) {
            if (n.posX == input.length-1 && n.posY == input[0].length-1) {
                for (Node path : n.shortestPath) {
                    finalNode = n;
                }
            }
        }
        return finalNode.distance;
    }

    public static int getSolutionPart2() {
        expandInput();
        Graph graph = createGraph();
        var result = calculateShortestPathFromSource(graph, graph.firstNode);

        Node finalNode = null;
        for (Node n : graph.nodes) {
            if (n.posX == input.length-1 && n.posY == input[0].length-1) {
                for (Node path : n.shortestPath) {
                    finalNode = n;
                }
            }
        }
        return finalNode.distance;
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt"); String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {System.out.println(getSolutionPart2());} else {System.out.println(getSolutionPart1());}
    }

    static int[][] parseInput(String filename) throws IOException {
        int[][] grid;
        int lineNo = 0;
        var lines = Files.lines(Path.of(filename)).collect(Collectors.toList());
        grid = new int[lines.size()][];
        for (String row : lines) {
            int[] points = new int[row.length()];
            int index=0;
            for (char c : row.toCharArray()) {
                points[index++] = Character.getNumericValue(c);
            }
            grid[lineNo++] = points;
        }

        return grid;
    }

    static int fixInput(int value) {
        if (value > 9)
            return value%9;

        return value;
    }

    static void expandInput() {
        int width = input.length;
        int height = input[0].length;
        int[][] grid = new int[width*5][height*5];

        for (int x=0;x<input.length;x++) {
            for (int y = 0; y < input[0].length; y++) {
                for (int vTile=0;vTile<5;vTile++) {
                    grid[x][y + (vTile*height)] = fixInput(input[x][y] + vTile);
                    grid[x + width][y + (vTile*height)] = fixInput(input[x][y] + 1 + vTile);
                    grid[x + (2 * width)][y + (vTile*height)] = fixInput(input[x][y] + 2 + vTile);
                    grid[x + (3 * width)][y + (vTile*height)] = fixInput(input[x][y] + 3 + vTile);
                    grid[x + (4 * width)][y + (vTile*height)] = fixInput(input[x][y] + 4 + vTile);
                }
            }
        }


        input = grid;
    }

    static Graph createGraph() {
        Map<String,Node> allNodes = new HashMap<>();
        for (int x=0;x<input.length;x++) {
            for (int y=0;y<input[0].length;y++) {
                Node n = new Node(x,y, input[x][y]);
                allNodes.put(x+"-"+y, n);
            }
        }

        for (Node n : allNodes.values()) {
            n.adjacentNodes = getSurroundingNode(n, allNodes);
        }

        Graph graph = new Graph();

        var first = new Node(0,0, input[0][0]);
        first.adjacentNodes = getSurroundingNode(first, allNodes);
        graph.firstNode = first;
        graph.nodes = new HashSet<>(allNodes.values());

        return graph;
    }

    static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.distance;
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.distance;
        if (sourceDistance + edgeWeigh < evaluationNode.distance) {
            evaluationNode.distance = (sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.shortestPath);
            shortestPath.add(sourceNode);
            evaluationNode.shortestPath = shortestPath;
        }
    }

    static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.distance = 0;

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node,Integer> adjacencyPair:
                    currentNode.adjacentNodes.entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    static Map<Node, Integer> getSurroundingNode(Node n, Map<String,Node> allNodes) {
        Map<Node, Integer> nodes = new HashMap<>();
        int x = n.posX;
        int y = n.posY;

        if (x>0) {
            Node o = allNodes.get((x-1)+"-"+y);
            nodes.put(o, o.risk);
        }
        if (x<(input[0].length-1)) {
            Node o = allNodes.get((x+1)+"-"+y);
            nodes.put(o, o.risk);
        }
        if(y>0) {
            Node o = allNodes.get(x+"-"+(y-1));
            nodes.put(o, o.risk);
        }
        if(y<(input.length-1)) {
            Node o = allNodes.get(x+"-"+(y+1));
            nodes.put(o, o.risk);
        }

        return nodes;
    }
}

class Graph {
    Node firstNode;
    Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }
}

class Node {
    int posX, posY;
    int risk;

    List<Node> shortestPath = new LinkedList<>();

    Integer distance = Integer.MAX_VALUE;

    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public Node(int posX, int posY, int risk) {
        this.posX = posX;
        this.posY = posY;
        this.risk = risk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return posX == node.posX && posY == node.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }

    String printName() {
        return "[" + posX + "," + posY +"]";
    }
}

