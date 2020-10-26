package com.scad.drone.service;

import com.scad.drone.model.Response;

public interface ResponseProcessor<R> {

    R process(Response response);
}
