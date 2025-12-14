import java.util.ArrayList;

/* Класс клиента */
public class Client extends Human {

    private static int clientIdCounter = 1;

    private int clientId;
    private String email;
    private ArrayList<Order> orders;


    /* Конструктор */
    public Client(String fullName, String password, int age, String email) {
        super(fullName, password, age);
        this.email = email;
        this.orders = new ArrayList<>();
        this.clientId = clientIdCounter++;
    } 

    /* Геттеры */
    public String getEmail() {
        return email;
    }

    public int getId() {
        return this.clientId;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    /* Сеттеры */
    public void setId(int ID) {
        if (ID > 0) {
            this.clientId = ID;
        } else {
            throw new IllegalArgumentException();
        }
    }
    
}