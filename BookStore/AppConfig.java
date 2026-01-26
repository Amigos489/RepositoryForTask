public class AppConfig {

    @ConfigProperty
    private int cntMountStale;

    @ConfigProperty
    private boolean possibilityMarkComplected;

    @ConfigProperty
    private String URL;

    @ConfigProperty
    private String user;

    @ConfigProperty
    private String password;

    public int getCntMountStale() {
        return cntMountStale;
    }

    public boolean isPossibilityMarkComplected() {
        return possibilityMarkComplected;
    }

    public String getURL() {
        return this.URL;
    }

    public String getUser() {
        return this.user;
    }

    public String getPassword() {
        return this.password;
    }
}
