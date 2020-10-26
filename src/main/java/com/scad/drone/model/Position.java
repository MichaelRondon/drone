package com.scad.drone.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Position {

    private final int xCoordinate;
    private final int yCoordinate;
    private final Orientation orientation;

    public Position move(Movement movement) {
        if (movement == null) {
            throw new IllegalStateException("The movement cannot be null");
        }
        switch (movement) {
            case A:
                return move();
            case D:
                return turnRight();
            default:
                return turnLeft();
        }
    }

    private Position move() {
        switch (orientation) {
            case N:
                return new Position(xCoordinate, yCoordinate + 1, orientation);
            case S:
                return new Position(xCoordinate, yCoordinate - 1, orientation);
            case E:
                return new Position(xCoordinate + 1, yCoordinate, orientation);
            default:
                return new Position(xCoordinate - 1, yCoordinate, orientation);
        }
    }

    private Position turnLeft() {
        switch (orientation) {
            case N:
                return new Position(xCoordinate, yCoordinate, orientation.W);
            case S:
                return new Position(xCoordinate, yCoordinate, orientation.E);
            case E:
                return new Position(xCoordinate, yCoordinate, orientation.N);
            default:
                return new Position(xCoordinate, yCoordinate, orientation.S);
        }
    }


    private Position turnRight() {
        switch (orientation) {
            case N:
                return new Position(xCoordinate, yCoordinate, orientation.E);
            case S:
                return new Position(xCoordinate, yCoordinate, orientation.W);
            case E:
                return new Position(xCoordinate, yCoordinate, orientation.S);
            default:
                return new Position(xCoordinate, yCoordinate, orientation.N);
        }
    }

    @Override
    public String toString() {
        return String.format("Position{x=%d, y=%d, orientation=%s}", xCoordinate, yCoordinate, orientation);
    }
}
