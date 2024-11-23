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
}
