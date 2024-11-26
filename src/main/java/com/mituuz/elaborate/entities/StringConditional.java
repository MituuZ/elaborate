package com.mituuz.elaborate.entities;

import static com.mituuz.elaborate.entities.MethodConditional.ConditionType.STRING;

public class StringConditional extends MethodConditional {
    public StringConditional(String stringValue, ConditionValueConditional conditionValueConditional) {
        super(STRING, null, stringValue, conditionValueConditional);
    }
}
