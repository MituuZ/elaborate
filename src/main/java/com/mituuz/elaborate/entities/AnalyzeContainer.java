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

import kotlin.collections.ArrayDeque;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnalyzeContainer {
    public final List<AnalyzeInstance> analyzeInstances = new ArrayList<>();
    public Set<String> analyzeMethods;
    private boolean generateHtml = false;
    private boolean generateHtmlTable = false;

    public boolean generateHtml() {
        return generateHtml;
    }

    public boolean generateHtmlTable() {
        return generateHtmlTable;
    }

    public void generateHtml(boolean generateHtml) {
        this.generateHtml = generateHtml;
    }

    public void generateHtmlTable(boolean generateHtmlTable) {
        this.generateHtmlTable = generateHtmlTable;
    }

    public void setAnalyzeMethods(Set<String> analyzeMethods) {
        this.analyzeMethods = analyzeMethods;
    }

    public Set<String> getAnalyzeMethods() {
        return analyzeMethods;
    }

    public void addInstance(AnalyzeInstance instance) {
        analyzeInstances.add(instance);
    }

    public List<AnalyzeInstance> getAnalyzeInstances() {
        return analyzeInstances;
    }

    public static class AnalyzeInstance {
        private final String title;

        public AnalyzeInstance(String title) {
            this.title = title;
        }

        private final List<AnalyzeMethod> analyzeMethods = new ArrayDeque<>();

        public void addResult(AnalyzeMethod analyzeMethod) {
            analyzeMethods.add(analyzeMethod);
        }

        public List<AnalyzeMethod> getAnalyzeMethods() {
            return analyzeMethods;
        }

        public String getTitle() {
            return title;
        }
    }

    public static class AnalyzeMethod {
        private final String methodName;
        private String stringValue;
        private Integer integerValue;

        public AnalyzeMethod(String methodName, Object value) {
            this.methodName = methodName;
            addValue(value);
        }

        private void addValue(Object value) {
            if (value instanceof String) {
                this.stringValue = (String) value;
            } else if (value instanceof Integer) {
                this.integerValue = (Integer) value;
            }
        }

        public String getHtml(boolean printMethodName) {
            var input = new StringBuilder();
            if (printMethodName)
                input.append("<h3>").append(getMethodName()).append("</h3>");

            if (getStringValue() != null) {
                input.append("<p>").append(getStringValue()).append("</p>");
            } else if (getIntegerValue() != null) {
                input.append("<p>").append(getIntegerValue()).append("</p>");
            } else {
                input.append("<p>Empty</p>");
            }
            return input.toString();
        }

        public String toString(boolean printMethodName) {
            if (printMethodName) {
                return getMethodName() + ": " + (getStringValue() != null ? getStringValue() : getIntegerValue()) + "\n";
            } else {
                return (getStringValue() != null ? getStringValue() : getIntegerValue()) + "\n";
            }
        }

        public String getMethodName() {
            return methodName;
        }

        public String getStringValue() {
            return stringValue;
        }

        public Integer getIntegerValue() {
            return integerValue;
        }
    }
}
