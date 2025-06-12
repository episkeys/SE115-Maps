import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WayFinderTest {
    @Test
    public void testFindFastestRoute() {
        CountryMap map = new CountryMap(3, 3);
        map.addCity(0, "A");
        map.addCity(1, "B");
        map.addCity(2, "C");

        map.addRoute(0, "A", "B", "10");
        map.addRoute(1, "B", "C", "20");
        map.addRoute(2, "A", "C", "15");

        WayFinder wayFinder = new WayFinder(map);
        String result = wayFinder.findFastestRoute("A", "C");

        String expected = "Fastest Way: A -> C\nTotal Time: 15 min";
        assertEquals(expected, result);
    }
}
