package senla.course.csv;

import senla.course.exceptions.InvalidValueFileCsv;
import senla.course.model.Request;
import java.time.format.DateTimeParseException;

public class ImportRequestCsv extends AbstractImportEntityCsv<Request> {

    public ImportRequestCsv(String nameFile) {
        super(nameFile);
    }

    @Override
    public Request convertStringCsvInEntity(String entity) throws InvalidValueFileCsv {
        try {
            String[] entitysString = entity.split(",");
            int requestId = Integer.parseInt(entitysString[0]);
            int bookId = Integer.parseInt(entitysString[1]);
            String nameBook = entitysString[2];
            int countRequest = Integer.parseInt(entitysString[3]);
            boolean isClosed = Boolean.parseBoolean(entitysString[4]);
            return new Request(requestId, bookId, nameBook, countRequest, isClosed);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new InvalidValueFileCsv("Некорректные значения в файле");
        }
    }
}
