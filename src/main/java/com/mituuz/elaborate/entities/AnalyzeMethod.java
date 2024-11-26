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
            input.append("<p></p>");
        }
        return input.toString();
    }

    public String toString(boolean printMethodName) {
        if (printMethodName) {
            if (getStringValue() != null || getIntegerValue() != null)
                return getMethodName() + ": " + (getStringValue() != null ? getStringValue() : getIntegerValue());

        } else {
            if (getStringValue() != null || getIntegerValue() != null)
                return (getStringValue() != null ? getStringValue() : getIntegerValue().toString());
        }
        return "no value";
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
}
