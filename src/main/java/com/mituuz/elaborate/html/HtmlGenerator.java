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

package com.mituuz.elaborate.html;

import com.mituuz.elaborate.entities.AnalyzeContainer;
import com.mituuz.elaborate.entities.AnalyzeContainer.AnalyzeInstance;
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
        for (AnalyzeInstance analyzeInstance : analyzeContainer.getAnalyzeInstances()) {
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

    public void generateTable(AnalyzeContainer analyzeContainer) {
        logger.info("Generating HTML table report");
        createNewOutputFile(false);
        var input = new ArrayList<String>();
        input.add("<table>");
        input.add("<tr>");
        input.add("<th>Instance</th>");
        for (String analyzeMethod : analyzeContainer.getAnalyzeMethods()) {
            input.add("<th>" + analyzeMethod + "</th>");
        }
        input.add("</tr>");
        for (AnalyzeInstance analyzeInstance : analyzeContainer.getAnalyzeInstances()) {
            input.add("<tr>");
            input.add("<td>" + analyzeInstance.getTitle() + "</td>");
            for (AnalyzeMethod analyzeMethod : analyzeInstance.getAnalyzeMethods()) {
                input.add("<td>" + analyzeMethod.toString(false) + "</td>");
            }
            input.add("</tr>");
        }
        input.add("</table>");
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
