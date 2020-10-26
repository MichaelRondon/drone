package com.scad.drone;

import com.scad.drone.model.Movement;
import com.scad.drone.model.Orientation;
import com.scad.drone.model.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PositionTest {

    @Test
    public void testMovement() {
        Position position = new Position(0, 0, Orientation.N);
        position = position.move(Movement.D).move(Movement.D).move(Movement.D).move(Movement.I).move(Movement.I);

        Assertions.assertEquals(0, position.getXCoordinate());
        Assertions.assertEquals(0, position.getYCoordinate());
        Assertions.assertEquals(Orientation.E, position.getOrientation());
    }
}