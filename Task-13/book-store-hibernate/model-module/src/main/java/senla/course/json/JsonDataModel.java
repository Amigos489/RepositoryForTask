package json;

import model.Book;
import model.ModelStoreBook;
import model.Order;
import model.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class JsonDataModel {
    private ObjectMapper objectMapper = new ObjectMapper();
    private String nameFile;
    private List<Book> books;
    private List<Order> orders;
    private List<Request> requests;

    public JsonDataModel(@Value("${jsonDataModel.nameFile}") String nameFile) {
        this.nameFile = nameFile;
    }

    public void initData(List<Book> books, List<Order> orders, List<Request> requests) {
        this.books = books;
        this.orders =  orders;
        this.requests = requests;
    }

    public void saveDataFromModel() {
        try (FileWriter writer = new FileWriter(nameFile, false)) {
            String listBook = objectMapper.writeValueAsString(books);
            String listOrder = objectMapper.writeValueAsString(orders);
            String listRequest = objectMapper.writeValueAsString(requests);

            writer.write(listBook);
            writer.append('\n');
            writer.write(listOrder);
            writer.append('\n');
            writer.write(listRequest);
            System.out.println("Сущности сохранены в json-файл.");
        } catch (IOException e){
            System.out.println("Ошибка при работе с файлом.");
        }
    }

    public void loadDataFromModel() {
        try (BufferedReader reader = new BufferedReader(new FileReader(nameFile))) {
            String bookString = reader.readLine();
            this.books = objectMapper.readValue(bookString, new TypeReference<List<Book>>() {});
            String orderString = reader.readLine();
            this.orders = objectMapper.readValue(orderString, new TypeReference<List<Order>>() {});
            String requestString = reader.readLine();
            this.requests = objectMapper.readValue(requestString, new TypeReference<List<Request>>() {});
            System.out.println("Сущности загружены из json-файла.");
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом.");
        }
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Request> getRequests() {
        return requests;
    }
}
