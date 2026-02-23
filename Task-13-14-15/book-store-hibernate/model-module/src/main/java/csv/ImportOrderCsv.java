package csv;

import enums.StatusOrder;
import exceptions.InvalidValueFileCsv;
import model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ImportOrderCsv extends AbstractImportEntityCsv<Order> {

    public ImportOrderCsv(String nameFile) {
        super(nameFile);
    }

    @Override
    public Order convertStringCsvInEntity(String entity) throws InvalidValueFileCsv {
        try {
            String[] entitysString = entity.split(",");
            int orderId = Integer.parseInt(entitysString[0]);
            LocalDate dateComplection = LocalDate.parse(entitysString[1]);
            int bookId = Integer.parseInt(entitysString[2]);
            String emailUser = entitysString[3];
            BigDecimal orderPrice = new BigDecimal(Long.parseLong(entitysString[4]));
            StatusOrder statusOrder = StatusOrder.valueOf(entitysString[5]);
            return new Order(orderId, dateComplection, bookId, emailUser, orderPrice, statusOrder);
        } catch (NumberFormatException | DateTimeParseException e) {
            throw new InvalidValueFileCsv("Некорректные значения в файле");
        }
    }
}
