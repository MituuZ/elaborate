package com.mituuz.elaborate.html;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class HtmlGenerator {
    private static final Logger logger = LoggerFactory.getLogger(HtmlGenerator.class);
    private final File outputFile = new File("build/result.html");

    public void generate() {
        createOutputFile();
        writeToFile();
    }

    private void writeToFile() {
        try {
            Files.write(outputFile.toPath(), "Hello World".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error("Failed to write to output file: {}", outputFile.getName(), e);
        }
    }

    private void createOutputFile() {
        if (!outputFile.exists()) {
            try {
                Files.createFile(outputFile.toPath());
            } catch (IOException e) {
                logger.error("Could not create file {}", outputFile.getName(), e);
            }
        }
    }

    public static void main(String[] args) {
        var htmlGenerator = new HtmlGenerator();
        htmlGenerator.generate();
    }
}
