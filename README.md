# Drone
Simple command line program that represents a drone that is programmed using flat files.

## Execution command

```mvn clean install```

```mvn compile exec:java -Dexec.args="<input file directory> <output file directory>"```

ex.
```mvn compile exec:java -Dexec.args="/home/michael.rondon/Downloads /home/michael.rondon/Downloads"```
  
### Input file directory (Mandatory input)
Directory with the files that are processed for the instantiation and control of the drones.
The name of the files *must* be in the format in01.txt, in02.txt ... in20.txt

*Important:*
The number of drones that are instantiated will depend on the number of files with the described format, therefore the restriction of 20 drones will depend on the customer's criteria.

*Important:*
The number of deliveries a drone can make depends on the number of records in the files, therefore the restriction of 3 deliveires will depend on the customer's discretion.


### Output file directory (Optional input)
Directory where the drone report files will be created

## Distance validation
The drone will detect when a movement goes outside the configured limits and will abort the execution. The error will be reported in the output file.

See: [public static final int MAX_DISTANCE_ALLOWED = 10;](https://github.com/MichaelRondon/drone/blob/6a4bb151c018305918c5f99976cac31ee8cf7c46/src/main/java/com/scad/drone/config/Constants.java#L8)

Injection of the Position Validator was implemented considering future requirements. 

See: [public Drone\(Position startPosition, PositionValidator positionValidator, String droneIdentifier\)](https://github.com/MichaelRondon/drone/blob/6a4bb151c018305918c5f99976cac31ee8cf7c46/src/main/java/com/scad/drone/model/Drone.java#L18)

Additionally, the option to prevent abortion of deliveries is enabled in case the configured limit is exceeded. Either way the error will be recorded.

See: [public DistanceValidator\(int maxDistanceAllowed, boolean abortOnFail\)](https://github.com/MichaelRondon/drone/blob/6a4bb151c018305918c5f99976cac31ee8cf7c46/src/main/java/com/scad/drone/service/DistanceValidator.java#L12)

## Configuraciones
See constants file: [Constants](https://github.com/MichaelRondon/drone/blob/6a4bb151c018305918c5f99976cac31ee8cf7c46/src/main/java/com/scad/drone/config/Constants.java#L7-L16)

## Immutable Object
To avoid [race conditions](https://github.com/MichaelRondon/drone/blob/6a4bb151c018305918c5f99976cac31ee8cf7c46/src/main/java/com/scad/drone/service/DroneManager.java#L34), the [Position](https://github.com/MichaelRondon/drone/blob/6a4bb151c018305918c5f99976cac31ee8cf7c46/src/main/java/com/scad/drone/model/Position.java#L10-L12) object was created as immutable
