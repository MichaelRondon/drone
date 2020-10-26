package com.scad.drone.model;

public enum Orientation {
    N("dirección Norte"),
    S("dirección Sur"),
    E("dirección Oriente"),
    W("dirección Occidente");

    private final String description;

    Orientation(String description){
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
