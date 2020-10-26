package com.scad.drone.service;

import com.scad.drone.model.Movement;
import com.scad.drone.model.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InputFileManager implements RequestBuilder {

    private final Path inputPath;

    public InputFileManager(Path inputPath) {
        this.inputPath = inputPath;
        if (!Files.exists(inputPath)) {
            throw new IllegalStateException("The input file must not be null");
        }
    }

    @Override
    public Request build() {
        Request request = new Request();
        try (BufferedReader reader = Files.newBufferedReader(inputPath)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                request.addDelivery(
                        Arrays.stream(line.split(""))
                                .map(Movement::movement)
                                .collect(Collectors.toList())
                );
            }
            return request;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error reading the input file: %s", e));
        }
    }
}
