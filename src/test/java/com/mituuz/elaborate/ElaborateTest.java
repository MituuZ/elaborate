package com.mituuz.elaborate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElaborateTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void checkClassName() {
        var elaborate = new Elaborate<>(String.class);
        String className = elaborate.getClassName();
        assertEquals("java.lang.String", className);
    }

    @Test
    void addInstances() {
        var elaborate = new Elaborate<>(String.class);
        elaborate.addInstance("Hello");
        elaborate.addInstance("World");
        assertEquals(2, elaborate.getAnalyzeClasses().size());
        elaborate.addInstances(List.of("Hello", "World"));
        assertEquals(4, elaborate.getAnalyzeClasses().size());
    }

    @Test
    void callMethod() {
        var elaborate = new Elaborate<>(String.class);
        elaborate.addInstance("Hello");
        assertEquals(5, elaborate.runMethod("length", 0));
    }
}
