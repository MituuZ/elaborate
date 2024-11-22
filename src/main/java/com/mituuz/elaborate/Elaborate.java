package com.mituuz.elaborate;

public class Elaborate<T> {
    private final Class<T> analyzeClass;

    public Elaborate(Class<T> analyzeClass) {
        this.analyzeClass = analyzeClass;
    }

    public String getClassName() {
        return analyzeClass.getName();
    }
}
