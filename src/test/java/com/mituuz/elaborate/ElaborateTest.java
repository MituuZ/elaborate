package com.mituuz.elaborate;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElaborateTest {
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
        assertEquals(5, elaborate.<Integer>runMethod("length", 0));
        assertEquals("hello", elaborate.<String>runMethod("toLowerCase", 0));
    }
}
