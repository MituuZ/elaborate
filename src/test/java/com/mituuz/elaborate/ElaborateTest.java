package com.mituuz.elaborate;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElaborateTest {
    @Test
    void addInstances() {
        var elaborate = new Elaborate<>();
        elaborate.addInstance("Hello");
        elaborate.addInstance("World");
        assertEquals(2, elaborate.getAnalyzeClasses().size());
        elaborate.addInstances(List.of("Hello", "World"));
        assertEquals(4, elaborate.getAnalyzeClasses().size());
    }

    @Test
    void callMethod() {
        var elaborate = new Elaborate<>();
        elaborate.addInstance("Hello");
        var instance = elaborate.getAnalyzeClasses().getFirst();
        assertEquals(5, elaborate.<Integer>runMethod(instance, "length"));
        assertEquals("hello", elaborate.<String>runMethod(instance, "toLowerCase"));
    }

    @Test
    void processInstances_printMethodNames() {
        Elaborate<Integer> elaborate = new Elaborate<>();
        elaborate.addInstances(List.of(1, 2, 3));
        elaborate.addAnalyzeMethods("toString", "intValue");
        List<String> output = elaborate.processInstances();
        assertEquals(12, output.size());
        assertEquals("1", output.get(0));
        assertEquals("toString: 1", output.get(1));
        assertEquals("intValue: 1", output.get(2));
        assertEquals("2", output.get(4));
        assertEquals("toString: 2", output.get(5));
        assertEquals("intValue: 2", output.get(6));
    }

    @Test
    void processInstances_skipMethodNames() {
        Elaborate<Integer> elaborate = new Elaborate<>();
        elaborate.addInstances(List.of(1, 2, 3));
        elaborate.addAnalyzeMethods("toString", "intValue");
        elaborate.skipMethodNames();
        List<String> output = elaborate.processInstances();
        assertEquals(12, output.size());
        assertEquals("1", output.get(0));
        assertEquals("1", output.get(1));
        assertEquals("1", output.get(2));
        assertEquals("2", output.get(4));
        assertEquals("2", output.get(5));
        assertEquals("2", output.get(6));
    }
}
