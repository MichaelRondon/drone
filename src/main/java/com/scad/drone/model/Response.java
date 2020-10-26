package com.scad.drone.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Response {

    private final List<Position> deliveryPositions = new LinkedList<>();
    private final List<ValidatorResponse> validationErrors = new LinkedList<>();

    public void addPosition(Position position) {
        deliveryPositions.add(position);
    }

    public void addValidationErrors(ValidatorResponse validatorResponse) {
        validationErrors.add(validatorResponse);
    }

    public List<Position> getDeliveryPositions() {
        return Collections.unmodifiableList(deliveryPositions);
    }

    public List<ValidatorResponse> getValidationErrors() {
        return Collections.unmodifiableList(validationErrors);
    }

    @Override
    public String toString() {
        return String.format("Response{deliveryPositions=%s, validationErrors=%s}",
                deliveryPositions, validationErrors);
    }
}
