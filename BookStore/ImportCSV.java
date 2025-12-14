import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public abstract class ImportCSV<T> {

    public ArrayList<T> importFromFile(String filePath) throws IOException {

        ArrayList<T> essences = new ArrayList<>();

        try(BufferedReader file = new BufferedReader(new FileReader(filePath))) {
            file.readLine();

            String line;
            while ((line = file.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                T obj = conversionInEssence(line);
                essences.add(obj);
            }
        }
        return essences;

    }

    public abstract T conversionInEssence(String line);
    
}
