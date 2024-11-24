package com.mituuz.elaborate.entities;

public class AnalyzeMethod {
    private final String methodName;
    private String stringValue;
    private Integer integerValue;
    private MethodConditional methodConditional = null;

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
        if (methodConditional == null || methodConditional.isConditionMet()) {
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

        return "";
    }

    public String toString(boolean printMethodName) {
        if (methodConditional == null || methodConditional.isConditionMet()) {
            if (printMethodName) {
                return getMethodName() + ": " + (getStringValue() != null ? getStringValue() : getIntegerValue()) + "\n";
            } else {
                return (getStringValue() != null ? getStringValue() : getIntegerValue()) + "\n";
            }
        }

        return "";
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

    public class MethodConditional {
        private final ConditionType conditionType;
        private final Integer conditionIntValue;
        private final String conditionStringValue;
        private final ConditionValueConditional conditionValueConditional;

        public MethodConditional(ConditionType conditionType, int conditionIntValue, String conditionStringValue, ConditionValueConditional conditionValueConditional) {
            this.conditionType = conditionType;
            this.conditionIntValue = conditionIntValue;
            this.conditionStringValue = conditionStringValue;
            this.conditionValueConditional = conditionValueConditional;
        }

        public boolean isConditionMet() {
            switch (methodConditional.conditionType) {
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
                    return integerValue > conditionIntValue;
                case LESS_THAN:
                    return integerValue < conditionIntValue;
                case GREATER_THAN_OR_EQUALS:
                    return integerValue >= conditionIntValue;
                case LESS_THAN_OR_EQUALS:
                    return integerValue <= conditionIntValue;
                case EQUALS:
                    return integerValue.equals(conditionIntValue);
                case NOT_EQUALS:
                    return !integerValue.equals(conditionIntValue);
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
    }
}
