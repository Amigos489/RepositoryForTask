public class AppConfig {

    @ConfigProperty
    private int cntMountStale;

    @ConfigProperty
    private boolean possibilityMarkComplected;

    public int getCntMountStale() {
        return cntMountStale;
    }

    public boolean isPossibilityMarkComplected() {
        return possibilityMarkComplected;
    }
}
