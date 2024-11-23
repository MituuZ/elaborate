package com.mituuz.elaborate;

import com.mituuz.elaborate.html.HtmlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Elaborate<T> {
    private static final Logger logger = LoggerFactory.getLogger(Elaborate.class);
    private final List<T> analyzeClasses = new ArrayList<>();
    private boolean generateHtml = false;
    private boolean printMethodNames = true;
    private Set<String> analyzeMethods = new HashSet<>();

    public void analyze() {
        List<String> output = new ArrayList<>();
        for (T instance : analyzeClasses) {
            for (var method : analyzeMethods) {
                var sb = new StringBuilder();
                if (printMethodNames) {
                    sb.append(method).append(": ");
                }
                var result = runMethod(instance, method);
                switch (result) {
                    case String stringResult -> sb.append(stringResult);
                    case Integer integerResult -> {
                        // check if method has limits
                        sb.append(integerResult);
                    }
                    default -> sb.append(result);
                }
                sb.append("\n");
                output.add(sb.toString());
            }
        }
        if (generateHtml) {
            var htmlGenerator = new HtmlGenerator();
            htmlGenerator.generate(output);
        }
    }

    public <S> S runMethod(T instance, String methodName) {
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

    public void setAnalyzeMethods(String... methodNames) {
        analyzeMethods = Set.of(methodNames);
    }

    public void addAnalyzeMethods(String... methodNames) {
        Collections.addAll(analyzeMethods, methodNames);
    }

    public void printMethodNames(boolean printMethodNames) {
        this.printMethodNames = printMethodNames;
    }

    public static void main(String[] args) {
        Elaborate<String> elaborate = new Elaborate<>();
        elaborate.generateHtml(true);
        elaborate.addInstances(List.of("Hell", "Orld"));
        elaborate.addAnalyzeMethods("toString", "toLowerCase");
        elaborate.printMethodNames(true);
        elaborate.analyze();
    }
}
