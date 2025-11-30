import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {

    private static int cntMountStale;
    private static boolean possibilityMarkComplected;

    public static void loadPropertyFile(String propertyFilePath) {
        try (FileInputStream file = new FileInputStream(propertyFilePath)) {
            Properties propert = new Properties();
            propert.load(file);
            cntMountStale = Integer.parseInt(propert.getProperty("cntMountStale"));
            possibilityMarkComplected = Boolean.parseBoolean(propert.getProperty("possibilityMarkComplected"));

        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public static int getCntMountStale() {
        return cntMountStale;
    }

    public static boolean getPossibilityMarkComplected() {
        return possibilityMarkComplected;
    }
}