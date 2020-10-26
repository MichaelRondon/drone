package com.scad.drone.model;


import com.scad.drone.service.PositionValidator;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Drone {

    private Position currentPosition;
    private final Position startPosition;
    private final PositionValidator positionValidator;
    private boolean aborting;
    private String droneIdentifier;

    public Drone(Position startPosition, PositionValidator positionValidator, String droneIdentifier) {
        this.startPosition = startPosition;
        this.currentPosition = startPosition;
        this.positionValidator = positionValidator;
        this.droneIdentifier = droneIdentifier;
    }

    public void abort() {
        aborting = true;
        log.warn("The drone {} has aborted its delivery", droneIdentifier);
    }

    public void comeBack() {
        log.info("The drone {} returns to its initial position", droneIdentifier);
        //We could think in the movements to return...
        currentPosition = startPosition;
    }

    private void deliver(int orderCounter) {
        log.info("The drone {} delivers the order #{}", droneIdentifier, orderCounter);
    }

    public Response process(Request request) {
        aborting = false;
        log.info("The drone {} started its deliveries", droneIdentifier);
        if (request == null || request.deliveries() == null) {
            throw new NullPointerException("Drone needs a request to work");
        }
        Response response = new Response();
        int orderCounter = 0;
        for (List<Movement> movements : request.deliveries()) {
            orderCounter++;
            process(movements, response, orderCounter);
            if (aborting) {
                break;
            }
        }
        comeBack();
        return response;
    }

    private void process(List<Movement> movements, Response response, int orderCounter) {
        if (movements == null) {
            throw new NullPointerException("Drone needs a request to work");
        }
        for (Movement movement : movements) {
            //Validate the next position from a snapshot of the current position:
            ValidatorResponse validatorResponse = positionValidator.validate(currentPosition, movement);
            //If the validator decides to abort the operation, the drone does not change its current position:
            if (!validatorResponse.isValid()) {
                response.addValidationErrors(validatorResponse);
            }
            if (validatorResponse.isAbort()) {
                this.abort();
                return;
            }
            //If the validator accepts the next position the drone changes its current position:
            currentPosition = currentPosition.move(movement);
        }
        deliver(orderCounter);
        response.addPosition(currentPosition);
    }
}

