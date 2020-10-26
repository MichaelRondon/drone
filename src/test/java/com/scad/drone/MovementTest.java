package com.scad.drone;

import com.scad.drone.model.Movement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MovementTest {

    @Test
    public void testMovement() {
        assertEquals(Movement.A, Movement.movement("a"));
        assertEquals(Movement.D, Movement.movement("D"));
        assertEquals(Movement.I, Movement.movement(" i "));
        try {
            Movement.movement("iu");
        } catch (IllegalStateException ise) {
            assertNotNull(ise.getMessage());
        }
    }

}
