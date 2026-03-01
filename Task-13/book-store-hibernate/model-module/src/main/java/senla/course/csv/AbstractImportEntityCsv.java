package senla.course.csv;

import senla.course.exceptions.InvalidValueFileCsv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractImportEntityCsv<T> {

    private String nameFile;

    public AbstractImportEntityCsv(String nameFile) {
        this.nameFile = nameFile;
    }

    public List<T> importCsv() throws InvalidValueFileCsv{
        List<T> entitys = new ArrayList<T>();
        try (BufferedReader reader = new BufferedReader(new FileReader(nameFile))) {
            reader.readLine();
            String entityString;
            while((entityString = reader.readLine()) != null) {
                try {
                    T entity = convertStringCsvInEntity(entityString);
                    entitys.add(entity);
                } catch (InvalidValueFileCsv e) {
                    System.out.println("Ошибка! некорректные значения в файле.");
                    throw new IOException();
                }
            }
        } catch (IOException e) {
            throw new InvalidValueFileCsv("Ошибка при работе с файлом");
        }
        return entitys;
    }

    public abstract T convertStringCsvInEntity(String entity) throws InvalidValueFileCsv;
}
