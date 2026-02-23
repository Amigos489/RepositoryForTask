package configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {

    private Properties propertyFile = new Properties();
    private String pathFileProperties;
    private boolean possibilityClosedRequest;
    private int countMonthDefineStaleBook;

    public PropertyFile(String pathFileProperties) {
        this.pathFileProperties = pathFileProperties;
    }

    public void loadPropertyFile() {
        try  (FileInputStream fileInputStream = new FileInputStream(pathFileProperties)) {
            propertyFile.load(fileInputStream);
            String possibilityClosedRequestString = propertyFile.getProperty("OrderManagement.possibilityClosedRequest");
            String countMonthDefineStaleBook = propertyFile.getProperty("Warehouse.countMonthDefineStaleBook");

            this.countMonthDefineStaleBook = Integer.parseInt(countMonthDefineStaleBook);
            this.possibilityClosedRequest = Boolean.parseBoolean(possibilityClosedRequestString);

        } catch (IOException e) {
            System.out.println("Ошибка! не удалось загрузить файл.");
        } catch (NumberFormatException e) {
            System.out.println("Ошибка! не удалось считать информацию из файла.");
        }
    }
    public void printProperty() {
        System.out.println("Параметры файла-properties:");
        System.out.println("countMonthDefineStaleBook: " + this.countMonthDefineStaleBook);
        System.out.println("possibilityClosedRequest: " + this.possibilityClosedRequest);
    }

    public boolean getPossibilityClosedRequest() {
        return possibilityClosedRequest;
    }

    public int getCountMonthDefineStaleBook() {
        return countMonthDefineStaleBook;
    }
}
