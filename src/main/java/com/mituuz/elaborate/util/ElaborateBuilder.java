package com.mituuz.elaborate.util;

import com.mituuz.elaborate.Elaborate;
import com.mituuz.elaborate.entities.AnalyzeMethod;

import java.util.List;

public class ElaborateBuilder<T> {
    private final Elaborate<T> elaborate = new Elaborate<>();

    public ElaborateBuilder<T> generateHtml() {
        elaborate.generateHtml();
        return this;
    }

    public ElaborateBuilder<T> generateHtmlTable() {
        elaborate.generateHtmlTable();
        return this;
    }

    public ElaborateBuilder<T> generateCsvReport() {
        elaborate.generateCsvReport();
        return this;
    }

    public ElaborateBuilder<T> addInstances(List<T> instances) {
        elaborate.addInstances(instances);
        return this;
    }

    public ElaborateBuilder<T> addAnalyzeMethods(String... methodNames) {
        elaborate.addAnalyzeMethods(methodNames);
        return this;
    }

    public ElaborateBuilder<T> addConditionalMethods(AnalyzeMethod... analyzeMethods) {
        elaborate.addConditionalMethods(analyzeMethods);
        return this;
    }

    public ElaborateBuilder<T> setTitleMethod(String titleMethod) {
        elaborate.setTitleMethod(titleMethod);
        return this;
    }

    public Elaborate<T> build() {
        return this.elaborate;
    }
}
