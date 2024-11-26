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
package com.mituuz.elaborate.csv;

import com.mituuz.elaborate.entities.AnalyzeContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.mituuz.elaborate.util.FileUtil.createNewOutputFile;
import static com.mituuz.elaborate.util.FileUtil.writeToFile;

public class CsvGenerator {
    private static final Logger logger = LoggerFactory.getLogger(CsvGenerator.class);
    private static final String DEFAULT_CSV_FILE_NAME = "report.csv";
    private boolean headers = true;

    public void generateCsvReport(AnalyzeContainer analyzeContainer) {
        logger.info("Generating CSV report");
        createNewOutputFile(false, DEFAULT_CSV_FILE_NAME);

        List<String> output = generateCsvLines(analyzeContainer);
        writeToFile(output, DEFAULT_CSV_FILE_NAME);
    }

    public List<String> generateCsvLines(AnalyzeContainer analyzeContainer) {
        List<String> output = new ArrayList<>();
        if (headers)
            writeHeaders(analyzeContainer, output);

        for (var analyzeInstance : analyzeContainer.getAnalyzeInstances()) {
            StringBuilder csvLine = new StringBuilder();
            int visitedMethods = 0;
            for (var method : analyzeInstance.getAnalyzeMethods()) {
                csvLine.append(method.toString(false));
                if (visitedMethods < analyzeContainer.getAnalyzeMethods().size() - 1)
                    csvLine.append(",");
                visitedMethods++;
            }
            output.add(csvLine.toString());
        }

        return output;
    }

    private void writeHeaders(AnalyzeContainer analyzeContainer, List<String> output) {
        int visitedMethods = 0;
        StringBuilder headerLine = new StringBuilder();
        for (var method : analyzeContainer.getAnalyzeMethods()) {
            headerLine.append(method.getMethodName());
            if (visitedMethods < analyzeContainer.getAnalyzeMethods().size() - 1)
                headerLine.append(",");
            visitedMethods++;
        }
        output.add(headerLine.toString());
    }

    /**
     * Skip writing headers to the CSV file.<br>
     * Defaults to <code>false</code>
     */
    public void skipHeaders() {
        this.headers = false;
    }
}
