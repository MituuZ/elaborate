package com.mituuz.elaborate.html;

import com.mituuz.elaborate.entities.AnalyzeContainer;
import com.mituuz.elaborate.entities.AnalyzeContainer.AnalyzeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class HtmlGenerator {
    private static final Logger logger = LoggerFactory.getLogger(HtmlGenerator.class);
    private final File outputFile = new File("build/result.html");

    private boolean printMethodNames = false;

    public HtmlGenerator(boolean printMethodNames) {
        this.printMethodNames = printMethodNames;
    }

    public HtmlGenerator() { }

    public void generate(AnalyzeContainer analyzeContainer) {
        logger.info("Generating HTML report");
        createNewOutputFile(false);
        var input = new ArrayList<String>();
        for (AnalyzeContainer.AnalyzeInstance analyzeInstance : analyzeContainer.getAnalyzeInstances()) {
            input.add("<div class=\"instance\">");
            input.add("<h2>" + analyzeInstance.getTitle() + "</h2>");
            for (AnalyzeMethod analyzeMethod : analyzeInstance.getAnalyzeMethods()) {
                input.add("<div class=\"method\">");
                input.add(analyzeMethod.getHtml(printMethodNames));
                input.add("</div>");
            }
            input.add("</div>");
        }
        writeToFile(input);
    }

    private void writeToFile(List<String> input) {
        try {
            Files.write(outputFile.toPath(), input, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Failed to write to output file: {}", outputFile.getName(), e);
        }
    }

    private void createNewOutputFile(boolean visited) {
        if (!outputFile.exists()) {
            try {
                Files.createFile(outputFile.toPath());
            } catch (IOException e) {
                logger.error("Could not create file {}", outputFile.getName(), e);
            }
        } else if (!visited) {
            try {
                Files.delete(outputFile.toPath());
                createNewOutputFile(true);
            } catch (IOException e) {
                logger.error("Could not delete file {}", outputFile.getName(), e);
            }
        }
    }
}
