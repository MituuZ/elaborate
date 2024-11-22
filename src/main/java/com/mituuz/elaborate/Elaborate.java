package com.mituuz.elaborate;

import java.util.ArrayList;
import java.util.List;

public class Elaborate<T> {
    private final Class<T> analyzeClass;
    private final List<T> analyzeClasses = new ArrayList<>();

    public void analyze() {
        for (T instance : analyzeClasses) {
            System.out.println(instance);
        }
    }

    public Elaborate(Class<T> analyzeClass) {
        this.analyzeClass = analyzeClass;
    }

    public String getClassName() {
        return this.analyzeClass.getName();
    }

    public void addInstance(T instance) {
        this.analyzeClasses.add(instance);
    }

    public void addInstances(List<T> instances) {
        this.analyzeClasses.addAll(instances);
    }

    public List<T> getAnalyzeClasses() {
        return this.analyzeClasses;
    }
}
