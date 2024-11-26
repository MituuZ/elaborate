package com.mituuz.elaborate.entities;

public class MethodConditional {
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
}
