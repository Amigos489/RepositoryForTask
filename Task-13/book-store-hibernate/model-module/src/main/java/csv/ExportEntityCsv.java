package csv;

import enums.StatusOperationCsv;
import model.GettingInfo;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportEntityCsv<T extends GettingInfo> {

    private String nameFile;

    public ExportEntityCsv(String nameFile) {
        this.nameFile = nameFile;
    }

    public StatusOperationCsv exportCsv(List<T> entitys) {
        try (FileWriter writer = new FileWriter(nameFile)) {
            if (!entitys.isEmpty()) {
                writer.write(entitys.get(0).getStringParameter());
                writer.append('\n');
            } else {
                return StatusOperationCsv.ERROR_ENTITY_EXPORT_CSV;
            }
            for (T entity : entitys) {
                writer.write(entity.getStringInfo());
                writer.append('\n');
            }
            return StatusOperationCsv.ENTITY_EXPORT_CSV;
        } catch (IOException e) {
            return StatusOperationCsv.ERROR_ENTITY_EXPORT_CSV;
        }
    }
}
