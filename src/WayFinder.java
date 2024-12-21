public class WayFinder {
        private CountryMap map;

        // Constructor to initialize the WayFinder with a CountryMap
        public WayFinder(CountryMap map) {
            this.map = map;
        }

        // Method to find the fastest route between two cities
        public String findFastestRoute(String startCity, String endCity) {
            int numberOfCities = map.getCities().length;
            int[][] timeMatrix = new int[numberOfCities][numberOfCities];

            // Building the time matrix from the routes
            String[][] routes = map.getRoutes();
            for (int i = 0; i < routes.length; i++) {
                int city1Index = map.findCityIndex(routes[i][0]);
                int city2Index = map.findCityIndex(routes[i][1]);
                int time = Integer.parseInt(routes[i][2]);
                timeMatrix[city1Index][city2Index] = time;
                timeMatrix[city2Index][city1Index] = time;
            }

            int startCityIndex = map.findCityIndex(startCity);
            int endCityIndex = map.findCityIndex(endCity);


            int[] distances = new int[numberOfCities];
            boolean[] visited = new boolean[numberOfCities];
            int[] previous = new int[numberOfCities];

            for (int i = 0; i < numberOfCities; i++) {
                distances[i] = Integer.MAX_VALUE;
                previous[i] = -1;
            }
            distances[startCityIndex] = 0;

            // Dijkstra algorithm to find the shortest path
            for (int i = 0; i < numberOfCities; i++) {
                int minDistance = Integer.MAX_VALUE;
                int currentCity = -1;

                // Finding the unvisited city with the smallest distance
                for (int j = 0; j < numberOfCities; j++) {
                    if (!visited[j] && distances[j] < minDistance) {
                        minDistance = distances[j];
                        currentCity = j;
                    }
                }

                // If no more cities left to visit break out of the loop
                if (currentCity == -1) break;
                visited[currentCity] = true;

                // Updating distances for neighboring cities
                for (int j = 0; j < numberOfCities; j++) {
                    if (timeMatrix[currentCity][j] > 0 && !visited[j]) {
                        int newDistance = distances[currentCity] + timeMatrix[currentCity][j];
                        if (newDistance < distances[j]) {
                            distances[j] = newDistance;
                            previous[j] = currentCity;
                        }
                    }
                }
            }

            // Building the path from the end city to the start city using the previous array
            if (distances[endCityIndex] == Integer.MAX_VALUE) {
                return "No route exists between " + startCity + " and " + endCity;
            } else {
                StringBuilder pathBuilder = new StringBuilder();
                int at = endCityIndex;
                while (at != -1) {
                    pathBuilder.insert(0, map.getCities()[at].getLabel());
                    at = previous[at];
                    if (at != -1) {
                        pathBuilder.insert(0, " -> ");
                    }
                }
                return "Fastest Way: " + pathBuilder.toString() + "\nTotal Time: " + distances[endCityIndex] + " min";
            }
        }
    }
