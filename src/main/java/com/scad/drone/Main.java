package com.scad.drone;

import com.scad.drone.service.DroneManager;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            log.error("The input directory is required");
            System.exit(0);
        }
        DroneManager droneManager = new DroneManager();
        String outputDirectory = null;
        if (args.length >= 2) {
            outputDirectory = args[1];
        }
        droneManager.execute(args[0], outputDirectory);

    }
}
