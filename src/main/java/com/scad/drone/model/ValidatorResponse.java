package com.scad.drone.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class ValidatorResponse {

    private List<String> errorMessages = new LinkedList<>();
    private boolean valid;
    private boolean abort;

    public void addErrorMessage(String errorMessage){
        errorMessages.add(errorMessage);
    }

}
