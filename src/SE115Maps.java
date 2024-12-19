import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class SE115Maps {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java SE115Maps <input_file_name> <output_file_name>");
            return;
        }

        String inputFileName = args[0];
        String outputFileName = args[1];

        try (Scanner reader = new Scanner(Paths.get(inputFileName));
             FileWriter writer = new FileWriter(outputFileName)) {


            int numberOfCities = Integer.parseInt(reader.nextLine().trim());


            String[] cityLabels = reader.nextLine().trim().split(" ");
            if (cityLabels.length != numberOfCities) {
                throw new IllegalArgumentException("Şehir sayısı ile etiket sayısı uyuşmuyor!");
            }

            int numberOfRoutes = Integer.parseInt(reader.nextLine().trim());

            CountryMap countryMap = new CountryMap(numberOfCities, numberOfRoutes);
            for (int i = 0; i < cityLabels.length; i++) {
                countryMap.addCity(i, cityLabels[i]);
            }

            for (int i = 0; i < numberOfRoutes; i++) {
                String[] routeParts = reader.nextLine().trim().split(" ");
                if (routeParts.length != 3) {
                    throw new IllegalArgumentException("Rota formatı hatalı: " + String.join(" ", routeParts));
                }
                countryMap.addRoute(i, routeParts[0], routeParts[1], routeParts[2]);
            }

            String[] startEnd = reader.nextLine().trim().split(" ");
            if (startEnd.length != 2) {
                throw new IllegalArgumentException("Başlangıç ve bitiş şehirleri hatalı formatta!");
            }
            String startCity = startEnd[0];
            String endCity = startEnd[1];

            WayFinder wayFinder = new WayFinder(countryMap);
            String fastestRoute = wayFinder.findFastestRoute(startCity, endCity);

            writer.write(fastestRoute);

        } catch (IOException e) {
            System.out.println("An error occurred while reading or writing the file.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
