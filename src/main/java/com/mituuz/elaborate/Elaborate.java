package com.mituuz.elaborate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Elaborate<T> {
    private static final Logger logger = LoggerFactory.getLogger(Elaborate.class);
    private final Class<T> analyzeClass;
    private final List<T> analyzeClasses = new ArrayList<>();

    public void analyze() {
        for (T instance : analyzeClasses) {
            logger.info(instance.toString());
        }
    }

    public int runMethod(String methodName, int index) {
        T instance = analyzeClasses.get(index);
        try {
            var method = analyzeClass.getMethod(methodName);
            return (int) method.invoke(instance);
        } catch (NoSuchMethodException e) {
            logger.error("Invalid method name: {}", methodName, e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            logger.error("Invalid method: {} target: {}", methodName, instance.getClass().getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
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
