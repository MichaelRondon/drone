package com.scad.drone.service;

import com.scad.drone.config.Constants;
import com.scad.drone.model.Drone;
import com.scad.drone.model.Request;
import com.scad.drone.model.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class DroneManager {

    private static final Pattern FILE_PATTERN = Pattern.compile(Constants.INPUT_FILE_REGEX);
    private static final Pattern FILE_IDENTIFIER_PATTERN = Pattern.compile(Constants.FILE_IDENTIFIER_REGEX);

    public void execute(String inputDirectory, String outputDirectory){
        Path directory = Paths.get(inputDirectory);
        if(!Files.exists(directory) || !Files.isDirectory(directory)){
            throw new IllegalStateException(String.format("Input directory must exists. Input: %s",
                    inputDirectory));
        }
        try {
            List<Path> pathList = Files.walk(directory, 1)
                    .filter(path -> FILE_PATTERN.matcher(path.getFileName().toString()).matches()).collect(Collectors.toList());
            log.info("{} input files found.\nFiles: {}", pathList.size(), pathList);
            pathList.parallelStream().forEach(path -> execute(path, getFileIdentifier(path),outputDirectory));
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error walking through directory. inputDirectory: %s",
                    inputDirectory));
        }
    }

    private String getFileIdentifier(Path path){
        Matcher matcher = FILE_IDENTIFIER_PATTERN.matcher(path.getFileName().toString());
        if(matcher.find()){
            String group = matcher.group();
            log.info("File identifier found: {}", group);
            return group;
        }
        throw new IllegalStateException(String.format("It could not happen"));
    }

    private void execute(final Path inputString, final String identifier, final String outputDirectory){
        InputFileManager inputFileManager = new InputFileManager(inputString);
        Request request = inputFileManager.build();

        PositionValidator positionValidator = new DistanceValidator(Constants.MAX_DISTANCE_ALLOWED,
                Constants.ABORT_ON_FAIL);
        Drone drone = new Drone(Constants.STARTED_POSITION, positionValidator, identifier);
        Response response = drone.process(request);

        String outputFile = new OutputFileManager(outputDirectory, identifier).process(response);
        log.info("For drone {} report file generated in: {}", identifier, outputFile);
    }
}
