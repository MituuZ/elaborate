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
package com.mituuz.elaborate.csv;

import com.mituuz.elaborate.entities.AnalyzeContainer;
import com.mituuz.elaborate.entities.AnalyzeMethod;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CsvGeneratorTest {
    @Test
    void generateCsvLinesWithHeaders() {
        var csvGenerator = new CsvGenerator();
        AnalyzeContainer analyzeContainer = setUpAnalyzeContainer();
        List<String> result = csvGenerator.generateCsvLines(analyzeContainer);
        assertEquals(3, result.size());
        assertEquals("method1,method2", result.get(0));
        assertEquals("Hello,2", result.get(1));
        assertEquals("Hello,2", result.get(2));
    }

    @Test
    void generateCsvLinesWithoutHeaders() {
        var csvGenerator = new CsvGenerator();
        csvGenerator.skipHeaders();
        AnalyzeContainer analyzeContainer = setUpAnalyzeContainer();
        List<String> result = csvGenerator.generateCsvLines(analyzeContainer);
        assertEquals(2, result.size());
        assertEquals("Hello,2", result.get(0));
        assertEquals("Hello,2", result.get(1));
    }

    private AnalyzeContainer setUpAnalyzeContainer() {
        var analyzeContainer = new AnalyzeContainer();
        AnalyzeMethod analyzeMethod = new AnalyzeMethod("method1");
        analyzeMethod.addValue("Hello");
        AnalyzeMethod analyzeMethod2 = new AnalyzeMethod("method2");
        analyzeMethod2.addValue(2);
        analyzeContainer.setAnalyzeMethods(List.of(analyzeMethod, analyzeMethod2));

        var analyzeInstance1 = new AnalyzeContainer.AnalyzeInstance("instance1");
        analyzeInstance1.addResult(analyzeMethod);
        analyzeInstance1.addResult(analyzeMethod2);

        var analyzeInstance2 = new AnalyzeContainer.AnalyzeInstance("instance2");
        analyzeInstance2.addResult(analyzeMethod);
        analyzeInstance2.addResult(analyzeMethod2);

        analyzeContainer.addInstance(analyzeInstance1);
        analyzeContainer.addInstance(analyzeInstance2);

        return analyzeContainer;
    }
}
