import java.util.ArrayList;

public class StoreData {

    private ArrayList<Book> books;
    private ArrayList<Order> orders;
    private ArrayList<BookRequest> requests;

    public StoreData() {
        books = new ArrayList<>();
        orders = new ArrayList<>();
        requests = new ArrayList<>();
    }

    // Геттеры и сеттеры
    public ArrayList<Book> getBooks() { 
        return books; 
    }
    public void setBooks(ArrayList<Book> books) { 
        this.books = books; 
    }

    public ArrayList<Order> getOrders() { 
        return orders; 
    }
    public void setOrders(ArrayList<Order> orders) { 
        this.orders = orders; 
    }

    public ArrayList<BookRequest> getRequests() { 
        return requests; 
    }
    public void setRequests(ArrayList<BookRequest> requests) { 
        this.requests = requests; 
    }
}
