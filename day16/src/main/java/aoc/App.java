package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    static List<String> input;

    public static long getSolutionPart1() {
        Stream stream = new Stream(input.get(0));
        stream.parse();

        return stream.countVersions();
    }

    public static long getSolutionPart2() {
        Stream stream = new Stream(input.get(0));
        stream.parse();

        return stream.getValue();
    }

    public static void main(String[] args) throws IOException {
        input = parseInput("input.txt");
        String part = System.getenv("part") == null ? "part1" : System.getenv("part");
        if (part.equals("part2")) {
            System.out.println(getSolutionPart2());
        } else {
            System.out.println(getSolutionPart1());
        }
    }

    static List<String> parseInput(String filename) throws IOException {
        return Files.lines(Path.of(filename)).collect(Collectors.toList());
    }
}

class Stream {
    String original;
    String binary;
    List<Packet> packets = new ArrayList<>();

    Stream(String representation) {
        original = convertToBinary(representation);
        binary = original;
    }

    void parse() {
        while (!binary.isEmpty() && (binary.length() > 10 || Long.parseLong(binary,2) != 0)) {
            Packet p = new Packet(binary);
            binary = p.binary;
            packets.add(p);
        }
    }

    String convertToBinary(String representation) {
        StringBuilder builder = new StringBuilder();
        for (char c : representation.toCharArray()) {
            var hex = Integer.parseInt(""+c, 16);
            builder.append(String.format("%4s", Integer.toBinaryString(hex)).replace(' ', '0'));
        }

        return builder.toString();
    }

    int countVersions() {
        int sumVersions = 0;

        for (Packet p : packets) {
            sumVersions += p.countVersions();
        }

        return sumVersions;
    }

    long getValue() {
        return packets.stream().map(Packet::getValue).mapToLong(Long::longValue).sum();
    }

    void print() {
        for (var p : packets){
            p.print();
        }
    }
}

class Packet {
    enum Type {LITERAL, SUM, PRODUCT, MINIMUM, MAXIMUM, GREATER_THAN, LESS_THAN, EQUAL_TO;}
    enum LengthType {LENGTH, PACKETS;}

    String binary;
    int version;
    Type typeId;
    long literal;
    LengthType lengthType;
    List<Packet> subPackets = new ArrayList<>();

    Packet(String binary) {
        this.binary = binary;
        parse();
    }

    void parse() {
        version = parseVersion();
        typeId = parseType();

        switch (typeId) {
            case LITERAL -> literal = parseLiteral();
            case SUM, PRODUCT, MINIMUM, MAXIMUM, GREATER_THAN, LESS_THAN, EQUAL_TO -> {
                lengthType = parseOperatorType();
                parseOperator();
            }
        }
    }

    int parseVersion() {
        var version = Integer.parseInt(binary.substring(0, 3), 2);
        binary = binary.substring(3);
        return version;
    }

    Type parseType() {
        var type = Integer.parseInt(binary.substring(0, 3), 2);
        binary = binary.substring(3);

        switch (type) {
            case 0 -> { return Type.SUM; }
            case 1 -> { return Type.PRODUCT; }
            case 2 -> { return Type.MINIMUM; }
            case 3 -> { return Type.MAXIMUM; }
            case 4 -> { return Type.LITERAL; }
            case 5 -> { return Type.GREATER_THAN; }
            case 6 -> { return Type.LESS_THAN; }
            case 7 -> { return Type.EQUAL_TO; }
        }
        return null;
    }

    long parseLiteral() {
        String literal = "";
        boolean reachEnd = false;
        do {
            if(binary.charAt(0) == '0') //0 indicate last packet
                reachEnd = true;

            var literalPak = binary.substring(1, 5);
            literal += literalPak;
            binary = binary.substring(5);
        } while (!reachEnd);

        return Long.parseLong(literal, 2);
    }

    void parseOperator() {
        switch (lengthType) {
            case LENGTH -> {
                var length = Integer.parseInt(binary.substring(0, 15), 2);
                binary = binary.substring(15);
                String packetsBin = binary.substring(0, length);
                binary = binary.substring(length);
                while (!packetsBin.isEmpty()) {
                    Packet p = new Packet(packetsBin);
                    packetsBin = p.binary;
                    subPackets.add(p);
                }
            }
            case PACKETS -> {
                var numbPackets = Integer.parseInt(binary.substring(0, 11), 2);
                binary = binary.substring(11);
                for (int packet=0;packet<numbPackets;packet++) {
                    Packet p = new Packet(binary);
                    binary = p.binary;
                    subPackets.add(p);
                }
            }
        }
    }

    LengthType parseOperatorType() {
        LengthType type = LengthType.PACKETS;

        if (binary.charAt(0) == '0')
            type = LengthType.LENGTH;

        binary = binary.substring(1);
        return type;
    }

    int countVersions() {
        int sumVersions = version;

        for (Packet p : subPackets) {
            sumVersions += p.countVersions();
        }

        return sumVersions;
    }

    long getValue() {
        switch (typeId) {
            case SUM -> { return subPackets.stream().map(Packet::getValue).mapToLong(Long::longValue).sum(); }
            case PRODUCT -> { return subPackets.stream().map(Packet::getValue).mapToLong(Long::longValue).reduce(1, (a, b) -> a * b); }
            case MINIMUM -> { return subPackets.stream().map(Packet::getValue).mapToLong(Long::longValue).min().orElseThrow(NoSuchElementException::new); }
            case MAXIMUM -> { return subPackets.stream().map(Packet::getValue).mapToLong(Long::longValue).max().orElseThrow(NoSuchElementException::new); }
            case GREATER_THAN -> { return subPackets.get(0).getValue() > subPackets.get(1).getValue()?1:0; }
            case LESS_THAN -> { return subPackets.get(0).getValue() < subPackets.get(1).getValue()?1:0; }
            case EQUAL_TO -> { return subPackets.get(0).getValue() == subPackets.get(1).getValue()?1:0; }
            case LITERAL -> { return literal; }
        }

        return 0l;
    }

    void print() {
        System.out.println("Packet");
        System.out.println("\tVersion: " + version);
        System.out.println("\tType: " + typeId.name());
        System.out.println("\tLiteral: " + literal);
        System.out.println("Subpackages: ");
        subPackets.stream().forEach(Packet::print);
        System.out.println("------------------------");
    }
}