package com.mituuz.elaborate;

import com.mituuz.elaborate.html.HtmlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Elaborate<T> {
    private static final Logger logger = LoggerFactory.getLogger(Elaborate.class);
    private final List<T> analyzeClasses = new ArrayList<>();
    private boolean generateHtml = false;

    public void analyze() {
        List<String> output = new ArrayList<>();
        for (T instance : analyzeClasses) {
            output.add(instance.toString());
        }
        if (generateHtml) {
            var htmlGenerator = new HtmlGenerator();
            htmlGenerator.generate(output);
        }
    }

    public <S> S runMethod(String methodName, int index) {
        T instance = analyzeClasses.get(index);
        var analyzeClass = instance.getClass();
        try {
            var method = analyzeClass.getMethod(methodName);
            return (S) method.invoke(instance);
        } catch (NoSuchMethodException e) {
            logger.error("Invalid method name: {}", methodName, e);
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            logger.error("Invalid method: {} for target class: {}", methodName, instance.getClass().getName(), e);
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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

    public void generateHtml(boolean generateHtml) {
        this.generateHtml = generateHtml;
    }

    public static void main(String[] args) {
        var elaborate = new Elaborate<>();
        elaborate.generateHtml(true);
        elaborate.addInstances(List.of("Hell", "Orld"));
        elaborate.analyze();
    }
}
