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

    /* Геттеры и сеттеры */
    public String getEmail() {
        return email;
    }

    public int getId() {
        return this.clientId;
    }

    public void setId(int ID) {
        this.clientId = ID;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
    
}