/*
MIT License

Copyright (c) 2024 Mitja Leino

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.mituuz.elaborate;

import java.util.List;
import java.util.Set;

import com.mituuz.elaborate.entities.AnalyzeContainer;
import com.mituuz.elaborate.entities.AnalyzeContainer.AnalyzeInstance;
import com.mituuz.elaborate.entities.AnalyzeMethod;
import com.mituuz.elaborate.entities.AnalyzeMethod.MethodConditional;
import com.mituuz.elaborate.html.HtmlGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.mituuz.elaborate.entities.AnalyzeMethod.ConditionType.INTEGER;
import static com.mituuz.elaborate.entities.AnalyzeMethod.ConditionValueConditional.GREATER_THAN;

public class Elaborate<T> {
    private static final Logger logger = LoggerFactory.getLogger(Elaborate.class);
    private final List<T> analyzeClasses = new ArrayList<>();
    private final AnalyzeContainer analyzeContainer = new AnalyzeContainer();
    private final Set<AnalyzeMethod> analyzeMethods = new LinkedHashSet<>();
    private boolean generateHtml = false;
    private boolean generateHtmlTable = false;
    private boolean printMethodNames = true;
    private boolean printToStdOut = true;
    private String titleMethod = "toString";

    public void analyze() {
        List<String> output = new ArrayList<>();
        analyzeContainer.setAnalyzeMethods(analyzeMethods);
        analyzeContainer.generateHtmlTable(generateHtmlTable);
        analyzeContainer.generateHtml(generateHtml);

        for (T instance : analyzeClasses) {
            var title = getTitle(instance);
            var analyzeInstance = new AnalyzeInstance(title);
            output.add(title + "\n");
            for (var analyzeMethod : analyzeMethods) {
                var method = new AnalyzeMethod(analyzeMethod);
                var result = runMethod(instance, method.getMethodName());
                method.addValue(result);
                analyzeInstance.addResult(method);
                output.add(method.toString(printMethodNames));
            }
            analyzeContainer.addInstance(analyzeInstance);
            output.add("\n");
        }
        printToStdOut(output);
        if (generateHtml) {
            var htmlGenerator = new HtmlGenerator(printMethodNames);
            htmlGenerator.generate(analyzeContainer);
        }
        if (generateHtmlTable) {
            var htmlGenerator = new HtmlGenerator(printMethodNames);
            htmlGenerator.generateTable(analyzeContainer);
        }
    }

    private void printToStdOut(List<String> output) {
        if (printToStdOut)
            for (var line : output) {
                System.out.print(line);
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
     *
     * @param instances Instances of the class to analyze
     */
    public void addInstances(List<T> instances) {
        this.analyzeClasses.addAll(instances);
    }

    public List<T> getAnalyzeClasses() {
        return this.analyzeClasses;
    }

    /**
     * Generate HTML output<br>
     * Defaults to <code>false</code>
     */
    public void generateHtml() {
        this.generateHtml = true;
    }

    /**
     * Add multiple methods to the list of methods to analyze<br>
     * These methods do not have any conditionals
     */
    public void addAnalyzeMethods(String... methodNames) {
        for (String methodName : methodNames) {
            analyzeMethods.add(new AnalyzeMethod(methodName));
        }
    }

    /**
     * Add multiple methods to the list of methods to analyze<br>
     * These methods support conditional rules that can be used to filter the results
     */
    public void addConditionalMethods(AnalyzeMethod... analyzeMethods) {
        Collections.addAll(this.analyzeMethods, analyzeMethods);
    }

    public void setTitleMethod(String titleMethod) {
        this.titleMethod = titleMethod;
    }

    /**
     * Setting this flag skips printing the method names to the output<br>
     * By default, method names are printed<br><br>
     * Method names are inserted before the result of the method<br>
     * <code>toString: Hello</code>
     */
    public void skipMethodNames() {
        this.printMethodNames = false;
    }

    /**
     * Setting this flag skips printing the output to the standard output<br>
     * By default, the output is printed to the standard output
     */
    public void skipPrint() {
        this.printToStdOut = false;
    }

    /**
     * Generate HTML table output<br>
     * Defaults to <code>false</code>
     */
    public void generateHtmlTable() {
        this.generateHtmlTable = true;
    }

    public static void main(String[] args) {
        Elaborate<String> elaborate = new Elaborate<>();
        elaborate.generateHtmlTable();
        elaborate.generateHtml();
        elaborate.addInstances(List.of("Hell", "Orld", "OF", "Hello there"));
        elaborate.addAnalyzeMethods("toLowerCase", "toString");

        AnalyzeMethod conditionalMethod = new AnalyzeMethod("length");
        var condition = new MethodConditional(INTEGER, 3, "", GREATER_THAN);
        conditionalMethod.setMethodConditional(condition);

        elaborate.addConditionalMethods(conditionalMethod);
        elaborate.setTitleMethod("hashCode");

        elaborate.analyze();
    }
}
