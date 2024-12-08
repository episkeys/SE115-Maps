public class City {
    private String Label;

    public City(String Label) {
        this.Label = Label;
    }
    public String getLabel() {
        return Label;
    }

    @Override
    public String toString() {
        return Label;
    }
}
