package com.scad.drone.service;

import com.scad.drone.model.Movement;
import com.scad.drone.model.Position;
import com.scad.drone.model.ValidatorResponse;

public interface PositionValidator {

    ValidatorResponse validate(Position position, Movement movement);
}
