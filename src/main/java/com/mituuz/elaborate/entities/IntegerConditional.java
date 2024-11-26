package com.mituuz.elaborate.entities;

import static com.mituuz.elaborate.entities.MethodConditional.ConditionType.INTEGER;

public class IntegerConditional extends MethodConditional{
    public IntegerConditional(int conditionIntValue, ConditionValueConditional conditionValueConditional) {
        super(INTEGER, conditionIntValue, null, conditionValueConditional);
    }
}
