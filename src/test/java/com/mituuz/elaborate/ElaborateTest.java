/*
MIT License

Copyright (c) 2024 Mitja Leino

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
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
        elaborate.createAnalyzeMethods("toString", "intValue");
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
        elaborate.createAnalyzeMethods("toString", "intValue");
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
