public class CountryMap {
    private City[] cities;
    private String[][] routes;

    public CountryMap(int numberOfCities, int numberOfRoutes) {
        cities = new City[numberOfCities];
        routes = new String[numberOfRoutes][3];
    }

    public void addCity(int index, String label) {
        cities[index] = new City(label);
    }

    public void addRoute(int index, String city1, String city2, String time) {
        routes[index][0] = city1;
        routes[index][1] = city2;
        routes[index][2] = time;
    }

    public City[] getCities() {
        return cities;
    }

    public String[][] getRoutes() {
        return routes;
    }

    public int findCityIndex(String label) {
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getLabel().equals(label)) {
                return i;
            }
        }
        return -1; // Couldn't find the city
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cities: ");
        for (City city : cities) {
            sb.append(city).append(" ");
        }
        sb.append("\nRoutes:\n");
        for (String[] route : routes) {
            sb.append(route[0]).append(" -> ").append(route[1]).append(" : ").append(route[2]).append(" min\n");
        }
        return sb.toString();
    }
}
