package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class AppTest {

    @Test void testLiteral() {
        Stream stream = new Stream("D2FE28");
        stream.parse();
        stream.print();
        assertEquals(2021, stream.getValue());
    }

    @Test void testMultipleInstructions() {
        Stream stream = new Stream("8A004A801A8002F478");
        stream.parse();
        assertEquals(16, stream.countVersions());

        stream = new Stream("620080001611562C8802118E34");
        stream.parse();
        assertEquals(12, stream.countVersions());

        stream = new Stream("C0015000016115A2E0802F182340");
        stream.parse();
        assertEquals(23, stream.countVersions());

        stream = new Stream("A0016C880162017C3686B18A3D4780");
        stream.parse();
        assertEquals(31, stream.countVersions());
    }

    @Test void testSum() {
        Stream stream = new Stream("C200B40A82");
        stream.parse();
        assertEquals(3, stream.getValue());
    }

    @Test void testProduct() {
        Stream stream = new Stream("04005AC33890");
        stream.parse();
        assertEquals(54, stream.getValue());
    }

    @Test void testMinimum() {
        Stream stream = new Stream("880086C3E88112");
        stream.parse();
        assertEquals(7, stream.getValue());
    }

    @Test void testMaximum() {
        Stream stream = new Stream("CE00C43D881120");
        stream.parse();
        assertEquals(9, stream.getValue());
    }

    @Test void testGreaterThan() {
        Stream stream = new Stream("D8005AC2A8F0");
        stream.parse();
        assertEquals(1, stream.getValue());
    }

    @Test void testLessThan() {
        Stream stream = new Stream("F600BC2D8F");
        stream.parse();
        assertEquals(0, stream.getValue());
    }

    @Test void testEqualsTo() {
        Stream stream = new Stream("9C005AC2F8F0");
        stream.parse();
        assertEquals(0, stream.getValue());
    }

    @Test void testMultipleOperations() {
        Stream stream = new Stream("9C0141080250320F1802104A08");
        stream.parse();
        assertEquals(1, stream.getValue());
    }
}
