package csv;

import exceptions.InvalidValueFileCsv;
import model.Book;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ImportBookCsv extends AbstractImportEntityCsv<Book> {

    public ImportBookCsv(String nameFile) {
        super(nameFile);
    }

    @Override
    public Book convertStringCsvInEntity(String entity) throws InvalidValueFileCsv {
        try {
            String[] entitysString = entity.split(",");
            int bookId = Integer.parseInt(entitysString[0]);
            String nameBook = entitysString[1];
            String authorBook = entitysString[2];
            LocalDate datePublication = LocalDate.parse(entitysString[3]);
            LocalDate dateAddWarehouse = LocalDate.parse(entitysString[4]);
            BigDecimal price = new BigDecimal(Long.parseLong(entitysString[5]));
            boolean availbility = Boolean.parseBoolean(entitysString[6]);
            return new Book(bookId, nameBook, authorBook, datePublication, dateAddWarehouse, price, availbility);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new InvalidValueFileCsv("Некорректные значения в файле");
        }
    }
}