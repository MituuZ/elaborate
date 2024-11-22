package com.mituuz.elaborate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElaborateTest {
    @Test
    void check() {
        Elaborate<String> elaborate = new Elaborate<>(String.class);
        String className = elaborate.getClassName();
        assertEquals("java.lang.String", className);
    }
}
