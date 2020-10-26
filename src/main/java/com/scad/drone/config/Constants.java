package com.scad.drone.config;

import com.scad.drone.model.Orientation;
import com.scad.drone.model.Position;

public class Constants {
    public static final Position STARTED_POSITION = new Position(0,0, Orientation.N);
    public static final int MAX_DISTANCE_ALLOWED = 10;
    public static final boolean ABORT_ON_FAIL = true;
    public static final String OUTPUT_FILE_NAME = "out";
    public static final String OUTPUT_FILE_EXTENSION = ".txt";
    public static final String REPORT_TITLE = "== Reporte de entregas ==";
    public static final String ERROR_REPORT = "Se encontraron errores en la entrega:";
    public static final String ABORT_REPORT = "La entrega se tuvo que abortar";
    public static final String INPUT_FILE_REGEX = "in[0-9]+\\.txt";
    public static final String FILE_IDENTIFIER_REGEX = "[0-9]+";
}
