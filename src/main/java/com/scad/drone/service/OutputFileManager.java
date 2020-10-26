package com.scad.drone.service;

import com.scad.drone.config.Constants;
import com.scad.drone.model.Response;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OutputFileManager implements ResponseProcessor<String> {

    private final Path outputpath;

    public OutputFileManager(String outputDirectory, String fileIdentifier){
        String fileName = outputFileName(fileIdentifier);
        if(outputDirectory != null){
            outputpath = directoryPath(outputDirectory).resolve(fileName);
        } else {
            outputpath = Paths.get(fileName);
        }
    }

    private final String outputFileName(String fileIdentifier){
        if(fileIdentifier != null){
            return String.format("%s%s%s", Constants.OUTPUT_FILE_NAME, fileIdentifier, Constants.OUTPUT_FILE_EXTENSION);
        }
        return String.format("%s%s", Constants.OUTPUT_FILE_NAME, Constants.OUTPUT_FILE_EXTENSION);
    }

    private final Path directoryPath(String outputDirectory){
        Path directory = Paths.get(outputDirectory);
        if(!Files.isDirectory(directory)){
            if(!Files.exists(directory)){
                try {
                    directory = Files.createDirectories(directory);
                } catch (IOException e) {
                    throw new IllegalStateException(String.format("Error creating the directory: %s, %s",
                            outputDirectory, e));
                }
            } else {
                throw new IllegalStateException(String.format("It's not a valid directory: %s",
                        outputDirectory));
            }
        }
        return directory;
    }

    @Override
    public String process(Response response) {
        try(BufferedWriter writer = Files.newBufferedWriter(outputpath, Charset.forName("UTF-8"))){
            writer.write(processString(response));
        }catch(IOException ex){
            throw new IllegalStateException(String.format("Error writing the output file: %s", ex));
        }
        return outputpath.toString();
    }

    private String processString(Response response){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.REPORT_TITLE);
        stringBuilder.append(System.lineSeparator());
        response.getDeliveryPositions().stream()
                .map(position ->
                        String.format("(%s,%s) %s",
                                position.getXCoordinate(),
                                position.getYCoordinate(),
                                position.getOrientation().getDescription()))
                .forEach(s -> stringBuilder.append(s).append(System.lineSeparator()));


        if(!response.getValidationErrors().isEmpty()){
            stringBuilder.append(Constants.ERROR_REPORT);
            stringBuilder.append(System.lineSeparator());
            response.getValidationErrors().stream().forEach(validatorResponse -> {
                if(validatorResponse.isAbort()){
                    stringBuilder
                            .append(Constants.ABORT_REPORT)
                            .append(System.lineSeparator());
                }
                validatorResponse.getErrorMessages().forEach(errorMessage ->
                        stringBuilder
                                .append(errorMessage)
                                .append(System.lineSeparator()));
            });
        }
        return stringBuilder.toString();
    }
}
