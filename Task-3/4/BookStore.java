public class BookStore {
    public static void main(String[] args) {
        System.out.println("Электронный магазин книг.");

        //Создание книг
        Book b1984 = new Book("Книга", true, 20, "1984", "Д.Оруэлл", 340);
        Book bMasterAndMargaret = new Book("Книга", true, 50, "Мастер и Маргарита", "М.Булгаков", 480);
        Book bAllQuiet = new Book("Книга", true, 40, "На западном фронте без перемен", "Э.М.Ремарк", 360);


        //Создание склада
        Warehouse wh = new Warehouse(0, 100);

        //Помещаем книги на склад
        wh.addWarehouse(b1984);
        wh.addWarehouse(bMasterAndMargaret);
        wh.addWarehouse(bAllQuiet);

        //Списываем со склада
        wh.writeFromWarehouse(bMasterAndMargaret);

        //Повторная попытка добавить на склад
        wh.addWarehouse(bAllQuiet);

        //Создаём заказ
        Order orderAllQuiet = new Order(bAllQuiet);
        orderAllQuiet.createOrder();

        //Создаём заказ на книгу, которой нет в наличии
        Order orderMasterAndMargaret = new Order(bMasterAndMargaret);
        orderMasterAndMargaret.createOrder();

        //Отмена заказа
        Order order1984 = new Order(b1984);
        order1984.createOrder();
        order1984.cancelOrder();


    }
}

//Абстрактный класс продукта
abstract class Product {
    protected String nameProduct;
    protected boolean Availability;
    protected int numperProduct;

    //Конструктор по умолчанию
    public Product() {
        this.nameProduct = "Продукт";
        this.Availability = false;
        this.numperProduct = 0;
    }

    //Конструтор со всеми параметрами
    public Product(String nameProduct, boolean Availability, int numperProduct) {
        this.nameProduct = nameProduct;
        this.Availability = Availability;
        this.numperProduct = numperProduct;
    }
    
    //Геттеры и сеттеры
    public String getNameProduct() {
        return this.nameProduct;
    }

    public boolean getAvailability() {
        return this.Availability;
    }

    public void setAvailability(boolean availability) {
        Availability = availability;
    }

    public int getNumberProduct() {
        return this.numperProduct;
    }

    public void setNumberProduct(int newValue) {
        if (newValue >= 0) {
            this.numperProduct = newValue;
        }
        else {
            System.out.println("Ошибка! некорректное значение");
        }
    }
}

//Книга
class Book extends Product{
    private String nameBook;
    private String authorBook;
    private int numberPages;

    // Конструктор по умолчанию
    public Book() {
        super();
        this.nameBook = "Неизвестно";
        this.authorBook = "Неизвестен";
        this.numberPages = 0;
    }

    // Конструктор со всеми параметрами
    public Book(String nameProduct, boolean availability, int numperProduct, String nameBook, String authorBook, int numberPages) {
        super(nameProduct, availability, numperProduct);
        this.nameBook = nameBook;
        this.authorBook = authorBook;
        this.numberPages = numberPages;
    }

    //Геттеры и сеттеры
    public String getNameBook() {
        return this.nameBook;
    }

    public String getAuthorBook() {
        return this.authorBook;
    }

    public int getNumberPages() {
        return this.numberPages;
    }

}



//Интерфейс для работы со складом
public interface IWorkingWithWarehouse {
    public void addWarehouse(Product product);
    public void writeFromWarehouse(Product product);
}

//Склад
class Warehouse implements IWorkingWithWarehouse {
    private int numberAllProduct;
    private int maxNumberProduct;

    Warehouse() {
        this.numberAllProduct = 0;
        this.maxNumberProduct = 100;
    }                      

    Warehouse(int numberAllProduct, int maxNumberProduct) {
        if (numberAllProduct >= 0 && maxNumberProduct > 0) {
            this.numberAllProduct = numberAllProduct;
            this.maxNumberProduct = maxNumberProduct;
        }
        else {
            System.out.println("Ошибка! введено некорректное значение");
        }
    }

    //Добавить предмет на склад
    public void addWarehouse(Product product) {
        this.setNumberAllProduct(product.getNumberProduct());
    }

    //Списать предмет со склада
    public void writeFromWarehouse(Product product) {
        this.numberAllProduct -= product.getNumberProduct();
        product.setNumberProduct(0);
        product.setAvailability(false);
        System.out.println("Продукт списан со склада");
    }


    //Геттеры и сеттеры
    public int getNumberAllProduct() {
        return this.numberAllProduct;
    }

    public void setNumberAllProduct(int newValue) {
        if (this.numberAllProduct + newValue <= this.maxNumberProduct) {
            this.numberAllProduct += newValue;
            System.out.println("Продукт добавлен на склад");
        }
        else {
            System.out.println("Ошибка! на складе недостаточно места");
        }
    }

    public int getMaxNumberBook() {
        return this.maxNumberBook;
    }
}

//Интерфейс для работы с заказами 
public interface IWorkingWithOrder {
    public void createOrder();
    public void cancelOrder();
    public void changeOrder(String newValue);
    public void pickOrder();
}

//Заказ на книгу
class Order implements IWorkingWithOrder{
    private int OrderID;
    private String statusOrder;
    private Product product;

    //Конструктор по умолчанию (отношение композиция, так как Заказ не может существовать без продукта)
    Order(Product product) {
        this.OrderID = 0;
        this.statusOrder = "отменён";
        this.product = product;
    }

    //Создание заказа
    public void createOrder() {
        if (this.product.Availability) {
            this.statusOrder = "новый";
            System.out.println("Создаётся заказ на продукт " + product.nameProduct);
            product.setNumberProduct(product.getNumberProduct() - 1);
            System.out.println("Теперь на складе: " + product.nameProduct  + " осталось " + product.numperProduct);
        }
        else if (!this.product.Availability) {
            this.statusOrder = "отменен";
            System.out.println("Продукта нет в наличии");
            IWorkingWithRequest request = new Request(this);
            request.createRequest();

            //Условность, спустя время книга появляется в наличии. В дальнейшем будет исправлено
            product.setNumberProduct(5);
            product.setAvailability(true);
            
            request.cancelRequest();

        }
    }

    //Отмена заказа
    public void cancelOrder() {
        if (this.statusOrder == "новый") {
            this.statusOrder = "отменен";
            product.setNumberProduct(product.getNumberProduct() + 1);
            System.out.println("Заказ на продукт отменён");
            System.out.println("Теперь на складе: " + this.product.getNumberProduct());
            
        }
        else {
            System.out.println("Данный заказ нельзя отменить, потому что он уже выполнен или отменен");
        }
    }

    //Изменение статуса заказа
    public void changeOrder(String newValue) {
        this.setStatusOrder(newValue);
    }

    //Получение заказа
    public void pickOrder() {
        System.out.println("Заказ получен и завершен");
        this.statusOrder = "выполнен";
    }

    //Геттеры и сеттеры
    public int getOrderID() {
        return OrderID;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String newValue) {
        if (newValue == "новый" || newValue == "отменен" || newValue == "выполнен") {
            this.statusOrder = newValue;
        }
        else {
            System.out.println("Ошибка! задано некорректное значение");
        }
    }

    public Product getProduct() {
        return product;
    }

}

//Интерфейс для работы с запросами 
public interface IWorkingWithRequest {
    public void createRequest();
    public void cancelRequest();
}

//Запрос на книгу
class Request implements IWorkingWithRequest {
    private int RequestsID;
    private String statusRequest;
    private IWorkingWithOrder order;
    
    //Конструктор по умолчанию
    Request(IWorkingWithOrder order) {
        this.RequestsID = new java.util.Random().nextInt(100);
        this.statusRequest = "новый";
    }

    //Создание запроса
    public void createRequest() {
            System.out.println("Создан запрос на продукт.");
            System.out.println("Ожидайте его появления");
    }

    //завершение запроса
    public void cancelRequest() {
        this.statusRequest = "завершен";
        System.out.println("Продукт появился в наличии");
        System.out.println("Запрос на продукт завершен.");
    }

    //Геттеры и сеттеры
    public int getRequestsID() {
        return RequestsID;
    }

    public String getStatusRequest() {
        return statusRequest;
    }

    public void setStatusRequest(String newValue) {
        if (newValue == "новый" || newValue == "завершен") {
            this.statusRequest = newValue;
        }
        else {
            System.out.println("Ошибка! задано некорректное значение");
        }
    }

    public IWorkingWithOrder getOrder() {
        return order;
    }

}
