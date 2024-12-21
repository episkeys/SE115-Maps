import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class SE115Maps {
    public static void main(String[] args) {
        // Checking if correct number of arguments are provided
        if (args.length != 2) {
            System.out.println("Usage: java -jar SE115Maps.jar <input_file_name> <output_file_name>");
            return;
        }

        // Input and output file names provided as arguments
        String inputFileName = args[0];
        String outputFileName = args[1];

        try (Scanner reader = new Scanner(Paths.get(inputFileName));
             FileWriter writer = new FileWriter(outputFileName)) {

            // Creating array to store error messages
            String[] errors = new String[1000];
            int errorCount = 0;
            int lineNumber = 1; // Keeping track of line numbers for error reporting

            int numberOfCities = -1;

            try {
                // Reading the number of cities and validating it is positive
                numberOfCities = Integer.parseInt(reader.nextLine().trim());
                if (numberOfCities <= 0) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Invalid number of cities (must be positive)";
                }
            } catch(NumberFormatException e) {
                errors[errorCount++] = "Error Line: " + lineNumber + " Invalid number of cities";
            }
            lineNumber++;

            String[] cityLabels = null;
            try {
                // Reading city labels and validating their count matches the number of cities
                cityLabels = reader.nextLine().trim().split(" ");
                if (cityLabels.length != numberOfCities) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Mismatch between number of cities and labels";
                }
            } catch (Exception e) {
                errors[errorCount++] = "Error Line: " + lineNumber + " Invalid city labels";
            }
            lineNumber++;

            int numberOfRoutes = -1;
            try {
                // Reading the number of routes and validating it's non-negative
            numberOfRoutes = Integer.parseInt(reader.nextLine().trim());
            if (numberOfRoutes < 0) {
                errors[errorCount++] = "Error Line: " + lineNumber + " Invalid number of routes";
                }
            } catch(NumberFormatException e) {
                errors[errorCount++] = "Error Line: " + lineNumber + " Invalid number of routes";
            }
            lineNumber++;

            // Creating array to store route details
            String[][] routes = new String[numberOfRoutes][3];
            for (int i = 0; i < numberOfRoutes; i++) {
                try {
                    // Reading and validating each route format and values
                    String[] routeParts = reader.nextLine().trim().split(" ");
                    if (routeParts.length != 3) {
                        errors[errorCount++] = "Error Line: " + lineNumber + " Invalid route format";
                    } else {
                        int time = Integer.parseInt(routeParts[2]);
                        if (time <= 0) {
                            errors[errorCount++] = "Error Line: " + lineNumber + " Invalid route time (must be positive)";
                        }
                        // Verifying that both cities in the route exist
                        boolean city1Exists = false;
                        boolean city2Exists = false;

                        for (String city : cityLabels) {
                            if (city.equals(routeParts[0])) city1Exists = true;
                            if (city.equals(routeParts[1])) city2Exists = true;
                        }
                        if (!city1Exists || !city2Exists) {
                            errors[errorCount++] = "Error Line: " + lineNumber + " Route cities not found in city list";
                        }
                        routes[i] = routeParts;
                    }
                } catch (NumberFormatException e) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Invalid route time";
                }
                lineNumber++;
            }

            String[] startEnd = null;
            try {
                // Reading and validating the start and end cities
                startEnd = reader.nextLine().trim().split(" ");
                if (startEnd.length != 2) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Invalid start and end cities format";
                } else {
                    boolean startCityExists = false;
                    boolean endCityExists = false;

                    for (String city : cityLabels) {
                        if (city.equals(startEnd[0])) startCityExists = true;
                        if (city.equals(startEnd[1])) endCityExists = true;
                    }
                    if (!startCityExists || !endCityExists) {
                        errors[errorCount++] = "Error Line: " + lineNumber + " Start or end city not found in city list";
                    }
                }
            } catch (Exception e) {
                errors[errorCount++] = "Error Line: " + lineNumber + " Invalid start and end cities";
            }

            String startCity = startEnd[0];
            String endCity = startEnd[1];

            if (errorCount == 0) {
                // If there is no error continue with map creation and route calculation
                System.out.println("File read is successful!");


                CountryMap map = new CountryMap(numberOfCities, numberOfRoutes);
                for (int i = 0; i < cityLabels.length; i++) {
                    map.addCity(i, cityLabels[i]);
                }
                for (int i = 0; i < numberOfRoutes; i++) {
                    map.addRoute(i, routes[i][0], routes[i][1], routes[i][2]);
                }

                WayFinder wayFinder = new WayFinder(map);
                String fastestRoute = wayFinder.findFastestRoute(startCity, endCity);

                writer.write(fastestRoute); // Writing the fastest route to the output file
            } else {
                for (int i = 0; i < errorCount; i++) {
                    // If errors exist writing them to the output file
                    writer.write(errors[i] + "\n");
                }
            }

        } catch (IOException e) {
            // Handling exceptions related to file reading/writing
            System.out.println("An error occurred while reading or writing the file.");
            e.printStackTrace();
        } catch (Exception e) {
            // Handling any other exceptions
            System.out.println("Error: " + e.getMessage());
        }
    }
}
