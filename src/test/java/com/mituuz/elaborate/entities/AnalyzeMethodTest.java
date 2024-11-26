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
package com.mituuz.elaborate.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyzeMethodTest {
    @Test
    void generateHtml_stringValue() {
        AnalyzeMethod analyzeMethod = new AnalyzeMethod("testMethod");
        analyzeMethod.addValue("test");
        var result = analyzeMethod.getHtml(true);
        assertEquals("<h3>testMethod</h3><p>test</p>", result);
    }

    @Test
    void generateHtml_integerValue() {
        AnalyzeMethod analyzeMethod = new AnalyzeMethod("testMethod");
        analyzeMethod.addValue(1);
        var result = analyzeMethod.getHtml(true);
        assertEquals("<h3>testMethod</h3><p>1</p>", result);
    }

    @Test
    void generateHtml_empty() {
        AnalyzeMethod analyzeMethod = new AnalyzeMethod("testMethod");
        var result = analyzeMethod.getHtml(true);
        assertEquals("<h3>testMethod</h3><p></p>", result);
    }

    @Test
    void generateHtml_noMethodName_stringValue() {
        AnalyzeMethod analyzeMethod = new AnalyzeMethod("testMethod");
        analyzeMethod.addValue("test");
        var result = analyzeMethod.getHtml(false);
        assertEquals("<p>test</p>", result);
    }

    @Test
    void generateHtml_noMethodName_integerValue() {
        AnalyzeMethod analyzeMethod = new AnalyzeMethod("testMethod");
        analyzeMethod.addValue(1);
        var result = analyzeMethod.getHtml(false);
        assertEquals("<p>1</p>", result);
    }

    @Test
    void generateHtml_noMethodName_empty() {
        AnalyzeMethod analyzeMethod = new AnalyzeMethod("testMethod");
        var result = analyzeMethod.getHtml(false);
        assertEquals("<p></p>", result);
    }

    @Test
    void notSupportedValueClass() {
        AnalyzeMethod analyzeMethod = new AnalyzeMethod("testMethod");
        analyzeMethod.addValue(new AnalyzeMethod("Hello"));
        var result = analyzeMethod.getHtml(false);
        assertEquals("<p></p>", result);
    }
}
