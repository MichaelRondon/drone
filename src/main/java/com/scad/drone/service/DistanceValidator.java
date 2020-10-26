package com.scad.drone.service;

import com.scad.drone.model.Movement;
import com.scad.drone.model.Position;
import com.scad.drone.model.ValidatorResponse;

public class DistanceValidator implements PositionValidator {

    private final int maxDistanceAllowed;
    private final boolean abortOnFail;

    public DistanceValidator(int maxDistanceAllowed, boolean abortOnFail) {
        this.maxDistanceAllowed = maxDistanceAllowed;
        this.abortOnFail = abortOnFail;
    }

    public DistanceValidator(int maxDistanceAllowed) {
        this.maxDistanceAllowed = maxDistanceAllowed;
        this.abortOnFail = false;
    }

    @Override
    public ValidatorResponse validate(Position currentPosition, Movement movement) {
        ValidatorResponse validatorResponse = new ValidatorResponse();
        if (currentPosition == null || movement == null) {
            validatorResponse.setValid(false);
            validatorResponse.setAbort(this.abortOnFail);
            validatorResponse.addErrorMessage("Position or movement null");
            return validatorResponse;
        }
        Position newPosition = currentPosition.move(movement);
        if ((Math.abs(newPosition.getXCoordinate()) > Math.abs(maxDistanceAllowed))
                || (Math.abs(newPosition.getYCoordinate()) > Math.abs(maxDistanceAllowed))) {
            validatorResponse.setValid(false);
            validatorResponse.setAbort(this.abortOnFail);
            validatorResponse.addErrorMessage(
                    String.format("Max allowed distance is: %s Current newPosition: %s Next Movement: %s",
                            maxDistanceAllowed, currentPosition, movement)
            );
            return validatorResponse;
        }
        validatorResponse.setValid(true);
        validatorResponse.setAbort(false);
        return validatorResponse;
    }
}
