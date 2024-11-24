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

public class AnalyzeMethod {
    private final String methodName;
    private String stringValue;
    private Integer integerValue;
    private MethodConditional methodConditional = null;

    public AnalyzeMethod(String methodName) {
        this.methodName = methodName;
    }

    public AnalyzeMethod(AnalyzeMethod analyzeMethod) {
        this.methodName = analyzeMethod.getMethodName();
        this.stringValue = analyzeMethod.getStringValue();
        this.integerValue = analyzeMethod.getIntegerValue();
        if (analyzeMethod.methodConditional != null)
            this.methodConditional = new MethodConditional(analyzeMethod.methodConditional);
    }

    public void addValue(Object value) {
        if (value instanceof String) {
            this.stringValue = (String) value;
        } else if (value instanceof Integer) {
            this.integerValue = (Integer) value;
        }
        if (methodConditional != null)
            methodConditional.setValues(stringValue, integerValue);
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

    public void setMethodConditional(MethodConditional methodConditional) {
        this.methodConditional = methodConditional;
    }

    public boolean isConditionMet() {
        return methodConditional == null || methodConditional.isConditionMet();
    }

    public enum ConditionType {
        STRING,
        INTEGER,
        BOOLEAN
    }

    public enum ConditionValueConditional {
        EMPTY,
        NOT_EMPTY,
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        LESS_THAN,
        GREATER_THAN_OR_EQUALS,
        LESS_THAN_OR_EQUALS
    }

    public static class MethodConditional {
        private final ConditionType conditionType;
        private final Integer conditionIntValue;
        private final String conditionStringValue;
        private final ConditionValueConditional conditionValueConditional;

        private String stringValue;
        private Integer integerValue;

        public void setValues(String stringValue, Integer integerValue) {
            this.stringValue = stringValue;
            this.integerValue = integerValue;
        }

        public MethodConditional(ConditionType conditionType, int conditionIntValue, String conditionStringValue, ConditionValueConditional conditionValueConditional) {
            this.conditionType = conditionType;
            this.conditionIntValue = conditionIntValue;
            this.conditionStringValue = conditionStringValue;
            this.conditionValueConditional = conditionValueConditional;
        }

        public boolean isConditionMet() {
            switch (conditionType) {
                case STRING:
                    return isConditionMet(stringValue);
                case INTEGER:
                    return isConditionMet(integerValue);
                case BOOLEAN:
                    throw new IllegalArgumentException("Boolean condition is not supported yet");
                default:
                    throw new IllegalArgumentException("Invalid condition type");
            }
        }

        private boolean isConditionMet(Integer intValue) {
            switch (conditionValueConditional) {
                case GREATER_THAN:
                    return intValue > conditionIntValue;
                case LESS_THAN:
                    return intValue < conditionIntValue;
                case GREATER_THAN_OR_EQUALS:
                    return intValue >= conditionIntValue;
                case LESS_THAN_OR_EQUALS:
                    return intValue <= conditionIntValue;
                case EQUALS:
                    return intValue.equals(conditionIntValue);
                case NOT_EQUALS:
                    return !intValue.equals(conditionIntValue);
                default:
                    throw new IllegalArgumentException("Invalid condition value conditional");
            }
        }

        private boolean isConditionMet(String stringValue) {
            switch (conditionValueConditional) {
                case EMPTY:
                    return stringValue == null || stringValue.isEmpty();
                case NOT_EMPTY:
                    return stringValue != null && !stringValue.isEmpty();
                case EQUALS:
                    return stringValue.equals(conditionStringValue);
                case NOT_EQUALS:
                    return !stringValue.equals(conditionStringValue);
                default:
                    throw new IllegalArgumentException("Invalid condition value conditional");
            }
        }

        public MethodConditional(MethodConditional methodConditional) {
            this.conditionType = methodConditional.conditionType;
            this.conditionIntValue = methodConditional.conditionIntValue;
            this.conditionStringValue = methodConditional.conditionStringValue;
            this.conditionValueConditional = methodConditional.conditionValueConditional;
        }
    }
}
