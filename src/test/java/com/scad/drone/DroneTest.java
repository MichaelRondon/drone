package com.scad.drone;

import com.scad.drone.model.Drone;
import com.scad.drone.model.Movement;
import com.scad.drone.model.Orientation;
import com.scad.drone.model.Position;
import com.scad.drone.model.Request;
import com.scad.drone.model.Response;
import com.scad.drone.model.ValidatorResponse;
import com.scad.drone.service.PositionValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class DroneTest {

    private Drone drone;
    private PositionValidator positionValidator = Mockito.mock(PositionValidator.class);

    @BeforeEach
    public void init(){
        drone = new Drone(new Position(0,0,Orientation.N), positionValidator, "80");
    }

    public List<Movement> movements(String input){
        return Arrays.stream(input.split("")).map(Movement::movement).collect(Collectors.toList());
    }

    @Test
    public void testValidProcess() {
        ValidatorResponse validatorResponse = new ValidatorResponse();
        validatorResponse.setValid(true);
        validatorResponse.setAbort(false);
        Mockito.when(positionValidator.validate(Mockito.any(), Mockito.any())).thenReturn(validatorResponse);

        Request request = new Request();
        request.addDelivery(movements("AAAAIAA"));
        Response response = drone.process(request);

        //THEN:
        List<Position> deliveryPositions = response.getDeliveryPositions();
        Assertions.assertEquals(1, deliveryPositions.size());
        Assertions.assertEquals(Orientation.W, deliveryPositions.get(0).getOrientation());
        Assertions.assertEquals(4, deliveryPositions.get(0).getYCoordinate());
        Assertions.assertEquals(-2, deliveryPositions.get(0).getXCoordinate());

        List<ValidatorResponse> validationErrors = response.getValidationErrors();
        Assertions.assertEquals(0, validationErrors.size());
    }

    @Test
    public void testAbortingProcess() {
        ValidatorResponse validResponse = new ValidatorResponse();
        validResponse.setValid(true);
        validResponse.setAbort(false);
        ValidatorResponse abortResponse = new ValidatorResponse();
        abortResponse.setValid(false);
        abortResponse.setAbort(true);
        Mockito.when(positionValidator.validate(Mockito.any(), Mockito.eq(Movement.A))).thenReturn(validResponse);
        Mockito.when(positionValidator.validate(Mockito.any(), Mockito.eq(Movement.I))).thenReturn(abortResponse);

        Request request = new Request();
        request.addDelivery(movements("AAAAIAA"));
        Response response = drone.process(request);

        //THEN:
        List<Position> deliveryPositions = response.getDeliveryPositions();
        Assertions.assertEquals(1, deliveryPositions.size());
        Assertions.assertEquals(Orientation.N, deliveryPositions.get(0).getOrientation());
        Assertions.assertEquals(4, deliveryPositions.get(0).getYCoordinate());
        Assertions.assertEquals(0, deliveryPositions.get(0).getXCoordinate());

        List<ValidatorResponse> validationErrors = response.getValidationErrors();
        Assertions.assertEquals(1, validationErrors.size());
        Assertions.assertEquals(true, validationErrors.get(0).isAbort());
        Assertions.assertEquals(false, validationErrors.get(0).isValid());
    }

    @Test
    public void testWarningProcess() {
        ValidatorResponse validResponse = new ValidatorResponse();
        validResponse.setValid(true);
        validResponse.setAbort(false);
        ValidatorResponse warningResponse = new ValidatorResponse();
        warningResponse.setValid(false);
        warningResponse.setAbort(false);
        Mockito.when(positionValidator.validate(Mockito.any(), Mockito.eq(Movement.A))).thenReturn(validResponse);
        Mockito.when(positionValidator.validate(Mockito.any(), Mockito.eq(Movement.I))).thenReturn(warningResponse);

        Request request = new Request();
        request.addDelivery(movements("AAAAIAA"));
        Response response = drone.process(request);

        //THEN:
        List<Position> deliveryPositions = response.getDeliveryPositions();
        Assertions.assertEquals(1, deliveryPositions.size());
        Assertions.assertEquals(Orientation.W, deliveryPositions.get(0).getOrientation());
        Assertions.assertEquals(4, deliveryPositions.get(0).getYCoordinate());
        Assertions.assertEquals(-2, deliveryPositions.get(0).getXCoordinate());

        List<ValidatorResponse> validationErrors = response.getValidationErrors();
        Assertions.assertEquals(1, validationErrors.size());
        Assertions.assertEquals(false, validationErrors.get(0).isAbort());
        Assertions.assertEquals(false, validationErrors.get(0).isValid());
    }
}
