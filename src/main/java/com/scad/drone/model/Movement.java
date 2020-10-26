package com.scad.drone.model;

import java.util.Arrays;
import java.util.Optional;

public enum Movement {

    A,I,D;

    public static Movement movement(final String input){
        if(input == null || input.isEmpty() || input.trim().length() > 1){
            throw new IllegalStateException(String.format("Invalid movement input: %s", input));
        }
        Optional<Movement> first = Arrays.stream(Movement.values()).filter(movement -> movement.name().equalsIgnoreCase(input.trim())).findFirst();
        if(first.isPresent()){
            return first.get();
        }
        throw new IllegalStateException(String.format("Invalid movement input: %s", input));
    }
}
