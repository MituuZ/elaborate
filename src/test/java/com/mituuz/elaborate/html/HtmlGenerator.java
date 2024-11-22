package com.mituuz.elaborate.html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class HtmlGenerator {
    private static final Logger logger = LoggerFactory.getLogger(HtmlGenerator.class);
    private final File outputFile = new File("build/result.html");

    public void generate(List<String> input) {
        createOutputFile(false);
        writeToFile(input);
    }

    private void writeToFile(List<String> input) {
        try {
            Files.write(outputFile.toPath(), input, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error("Failed to write to output file: {}", outputFile.getName(), e);
        }
    }

    private void createOutputFile(boolean visited) {
        if (!outputFile.exists()) {
            try {
                Files.createFile(outputFile.toPath());
            } catch (IOException e) {
                logger.error("Could not create file {}", outputFile.getName(), e);
            }
        } else if (!visited) {
            try {
                Files.delete(outputFile.toPath());
                createOutputFile(true);
            } catch (IOException e) {
                logger.error("Could not delete file {}", outputFile.getName(), e);
            }
        }
    }

    public static void main(String[] args) {
        var htmlGenerator = new HtmlGenerator();
        htmlGenerator.generate(List.of("Hello, World", "Hello Moon"));
    }
}
