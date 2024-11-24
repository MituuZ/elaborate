package com.mituuz.elaborate.entities;

public class AnalyzeMethod {
    private final String methodName;
    private String stringValue;
    private Integer integerValue;
    // private Conditional condiditional = null;

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
