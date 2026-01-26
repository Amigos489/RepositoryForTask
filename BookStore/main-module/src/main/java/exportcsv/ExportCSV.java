package exportcsv;

import status.StatusImportExport;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ExportCSV {
    public static StatusImportExport exportInFile(String path, ArrayList<? extends Exportable> essences) {
        if (essences.isEmpty()) {
            return StatusImportExport.FAIL;
        }

        try (BufferedWriter file = new BufferedWriter(new FileWriter(path))) {
            file.write((essences.get(0).generateStringHeader()) + System.lineSeparator());
            for (Exportable essence : essences) {
                file.write(essence.generateStringInfo() + System.lineSeparator());
            }
            return StatusImportExport.CORRECTLY;
        } catch (IOException | NullPointerException e) {
            return StatusImportExport.FAIL;
        }
    }
}
