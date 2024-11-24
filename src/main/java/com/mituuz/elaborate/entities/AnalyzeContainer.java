package com.mituuz.elaborate.entities;

import kotlin.collections.ArrayDeque;

import java.util.ArrayList;
import java.util.List;

public class AnalyzeContainer {
    public final List<AnalyzeInstance> analyzeInstances = new ArrayList<>();

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
            if (value instanceof String sVal) {
                this.stringValue = sVal;
            } else if (value instanceof Integer iVal) {
                this.integerValue = iVal;
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
