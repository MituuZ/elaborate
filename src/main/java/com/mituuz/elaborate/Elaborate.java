package com.mituuz.elaborate;

import com.mituuz.elaborate.entities.AnalyzeContainer;
import com.mituuz.elaborate.entities.AnalyzeContainer.AnalyzeInstance;
import com.mituuz.elaborate.entities.AnalyzeContainer.AnalyzeMethod;
import com.mituuz.elaborate.html.HtmlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Elaborate<T> {
    private static final Logger logger = LoggerFactory.getLogger(Elaborate.class);
    private final List<T> analyzeClasses = new ArrayList<>();
    private boolean generateHtml = false;
    private boolean generateHtmlTable = false;
    private boolean printMethodNames = true;
    private Set<String> analyzeMethods = new LinkedHashSet<>();
    private String titleMethod = "toString";

    public void analyze() {
        List<String> output = new ArrayList<>();
        var analyzeContainer = new AnalyzeContainer(analyzeMethods);

        for (T instance : analyzeClasses) {
            var title = getTitle(instance);
            var analyzeInstance = new AnalyzeInstance(title);
            output.add(title + "\n");
            for (var method : analyzeMethods) {
                var result = runMethod(instance, method);
                var analyzeMethod = new AnalyzeMethod(method, result);
                analyzeInstance.addResult(analyzeMethod);
                output.add(analyzeMethod.toString(printMethodNames));
            }
            analyzeContainer.addInstance(analyzeInstance);
            output.add("\n");
        }
        for (var line : output) {
            System.out.print(line);
        }
        if (generateHtml) {
            var htmlGenerator = new HtmlGenerator(printMethodNames);
            htmlGenerator.generate(analyzeContainer);
        }
        if (generateHtmlTable) {
            var htmlGenerator = new HtmlGenerator(printMethodNames);
            htmlGenerator.generateTable(analyzeContainer);
        }
    }

    public String getTitle(T instance) {
        return runMethod(instance, titleMethod).toString();
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

    /**
     * Add multiple instances to the list of class instances to analyze
     * @param instances Instances of the class to analyze
     */
    public void addInstances(List<T> instances) {
        this.analyzeClasses.addAll(instances);
    }

    public List<T> getAnalyzeClasses() {
        return this.analyzeClasses;
    }

    public void generateHtml(boolean generateHtml) {
        this.generateHtml = generateHtml;
    }

    /**
     * Set the methods to analyze
     */
    public void setAnalyzeMethods(String... methodNames) {
        analyzeMethods = Set.of(methodNames);
    }

    /**
     * Add multiple methods to the list of methods to analyze
     */
    public void addAnalyzeMethods(String... methodNames) {
        Collections.addAll(analyzeMethods, methodNames);
    }

    public void setTitleMethod(String titleMethod) {
        this.titleMethod = titleMethod;
    }

    /**
     * Print method names in the output<br>
     * Defaults to <code>true</code><br><br>
     * Prints the method name before the result of the method<br>
     * <code>toString: Hello</code>
     */
    public void printMethodNames(boolean printMethodNames) {
        this.printMethodNames = printMethodNames;
    }

    public void generateHtmlTable(boolean generateHtmlTable) {
        this.generateHtmlTable = generateHtmlTable;
    }

    public static void main(String[] args) {
        Elaborate<String> elaborate = new Elaborate<>();
        elaborate.generateHtmlTable(true);
        elaborate.addInstances(List.of("Hell", "Orld"));
        elaborate.addAnalyzeMethods("toLowerCase", "length");
        elaborate.setTitleMethod("toString");
        elaborate.printMethodNames(false);
        elaborate.analyze();
    }
}
