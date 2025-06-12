import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class SE115Maps {
    public static void main(String[] args) {
        // Checking if correct number of arguments are provided
        if (args.length != 2) {
            System.out.println("Usage: java -jar SE115-Maps.jar <input_file_name> <output_file_name>");
            return;
        }

        // Input and output file names provided as arguments
        String inputFileName = args[0];
        String outputFileName = args[1];

        try (Scanner reader = new Scanner(Paths.get(inputFileName));
             FileWriter writer = new FileWriter(outputFileName)) {

            // Creating array to store error messages
            String[] errors = new String[50];
            int errorCount = 0;
            int lineNumber = 1; // Keeping track of line numbers for error reporting

            int numberOfCities = -1;

            try {
                // Reading the number of cities and validating it is positive
                numberOfCities = Integer.parseInt(reader.nextLine().trim());
                if (numberOfCities <= 0) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Invalid number of cities (must be positive)";
                }
            } catch (NumberFormatException e) {
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
                    errors[errorCount++] = "Error Line: " + lineNumber + " Invalid number of routes: " + numberOfRoutes + " (must be 0 or positive)";
                    for (int i = 0; i < errorCount; i++) {
                        writer.write(errors[i] + "\n");
                    }
                    writer.close();
                    return;
                }
            } catch (NumberFormatException e) {
                errors[errorCount++] = "Error Line: " + lineNumber + " Invalid number of routes";
                for (int i = 0; i < errorCount; i++) {
                    writer.write(errors[i] + "\n");
                }
                writer.close();
                return;
            }
            lineNumber++;

            // Creating array to store route details
            String[][] routes = new String[numberOfRoutes][3];
            int actualRoutes = 0;

            for (int i = 0; i < numberOfRoutes; i++) {
                try {
                    if (!reader.hasNextLine()) {
                        errors[errorCount++] = "Error Line: " + lineNumber + " Insufficient routes: Expected " + numberOfRoutes + ", Found " + i;
                        break;
                    }
                    String[] routeParts = reader.nextLine().trim().split(" ");
                    if (routeParts.length != 3) {
                        errors[errorCount++] = "Error Line: " + lineNumber + " Invalid route format";
                    } else {
                        int time = Integer.parseInt(routeParts[2]);
                        if (time <= 0) {
                            errors[errorCount++] = "Error Line: " + lineNumber + " Invalid route time (must be positive)";
                        }

                        boolean city1Exists = false;
                        boolean city2Exists = false;
                        for (String city : cityLabels) {
                            if (city.equals(routeParts[0])) city1Exists = true;
                            if (city.equals(routeParts[1])) city2Exists = true;
                        }

                        if (!city1Exists || !city2Exists) {
                            errors[errorCount++] = "Error Line: " + lineNumber + " City in route not found";
                        } else {
                            routes[actualRoutes++] = routeParts;
                        }
                    }
                } catch (Exception e) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Invalid route data";
                }
                lineNumber++;
            }

            if (actualRoutes < numberOfRoutes) {
                errors[errorCount++] = "Error Line: " + lineNumber + " Insufficient routes: Expected " + numberOfRoutes + ", Found " + actualRoutes;
            }

            String[] startEnd = null;
            try {
                if (!reader.hasNextLine()) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Missing start and end cities";
                    for (int i = 0; i < errorCount; i++) {
                        writer.write(errors[i] + "\n");
                    }
                    writer.close();
                    return;
                }
                startEnd = reader.nextLine().trim().split(" ");
                if (startEnd.length != 2) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Invalid start and end cities format";
                    for (int i = 0; i < errorCount; i++) {
                        writer.write(errors[i] + "\n");
                    }
                    writer.close();
                    return;
                }

                boolean startCityExists = false;
                boolean endCityExists = false;

                for (String city : cityLabels) {
                    if (city.equals(startEnd[0])) startCityExists = true;
                    if (city.equals(startEnd[1])) endCityExists = true;
                }

                if (!startCityExists || !endCityExists) {
                    errors[errorCount++] = "Error Line: " + lineNumber + " Start or end city not found in city list";
                    for (int i = 0; i < errorCount; i++) {
                        writer.write(errors[i] + "\n");
                    }
                    writer.close();
                    return;
                }

            } catch (Exception e) {
                errors[errorCount++] = "Error Line: " + lineNumber + " Invalid start and end cities";
                for (int i = 0; i < errorCount; i++) {
                    writer.write(errors[i] + "\n");
                }
                writer.close();
                return;
            }
            lineNumber++;

            if (errorCount == 0) {
                // If there is no error continue with map creation and route calculation
                System.out.println("File read is successful!");

                CountryMap map = new CountryMap(numberOfCities, actualRoutes);
                for (int i = 0; i < cityLabels.length; i++) {
                    map.addCity(i, cityLabels[i]);
                }
                for (int i = 0; i < actualRoutes; i++) {
                    map.addRoute(i, routes[i][0], routes[i][1], routes[i][2]);
                }

                WayFinder wayFinder = new WayFinder(map);
                String fastestRoute = wayFinder.findFastestRoute(startEnd[0], startEnd[1]);

                writer.write(fastestRoute); // Writing the fastest route to the output file
            } else {
                for (int i = 0; i < errorCount; i++) {
                    // If errors exist writing them to the output file
                    writer.write(errors[i] + "\n");
                }
                System.out.println("An error occurred. Please check the output file for details.");
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
