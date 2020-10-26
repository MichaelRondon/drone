package com.scad.drone.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Request {

    private final List<List<Movement>> deliveries = new LinkedList<>();
    private boolean close = false;

    public void addDelivery(List<Movement> delivery){
        if(close){
            throw new IllegalStateException("This request is close, please create a new one");
        }
        deliveries.add(delivery);
    }

    public List<List<Movement>> deliveries(){
        close = true;
        return Collections.unmodifiableList(deliveries);
    }
}
