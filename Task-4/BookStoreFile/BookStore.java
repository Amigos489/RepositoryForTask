import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class BookStore {
    public static void main(String[] args) {

        Book book1 = new Book("Чистый код", "Роберт Мартин",
                LocalDate.of(2008, 1, 1), 100, 464, 1500,
                        LocalDate.now().minusMonths(7));

        Book book2 = new Book("Язык программирования C++", "Бьерн Страуструп",
                LocalDate.of(2013, 1, 1), 50, 1368, 2200,
                        LocalDate.now().minusMonths(3));

        Book book3 = new Book("Преступление и наказание", "Фёдор Достоевский",
                LocalDate.of(1866, 1, 1), 30, 672, 800,
                        LocalDate.now().minusMonths(8));

        Book book4 = new Book("С++ для прожженных самоваров.", "Крутой чел",
                LocalDate.of(2025, 11, 2), 30, 300, 500,
                        LocalDate.now().minusMonths(8));

        Warehouse wh = new Warehouse(0, 250);
        wh.addToWarehouse(book1);
        wh.addToWarehouse(book2);
        wh.addToWarehouse(book3);
        wh.addToWarehouse(book4);
        wh.writeFromWarehouse(book1);

        LinkedList<Book> allBooks = wh.listBook;


        OrderManagement om = new OrderManagement();

        om.createOrderOnBook(book2, "Dimon", "Nagibator@gmail.com");
        om.createOrderOnBook(book3, "DrugoyDimon", "NeNagibator@gmail.com");
        om.createOrderOnBook(book1, "Ivan", "Ne@gmail.com");
        om.createOrderOnBook(book1, "Maks", "Neon@gmail.com");

        System.out.println("\nСортировка книг");

        System.out.println("\nСортировка по алфавиту");
        System.out.println("\nДо сортировки:");

        for (Book book: allBooks) {
            System.out.println(book.getNameBook());
        }

        Collections.sort(allBooks, new SortedBookByName());

        System.out.println("\nПосле сортировки:");

        for (Book book: allBooks) {
            System.out.println(book.getNameBook());
        }

        System.out.println("\nСортировка по дате издания");
        System.out.println("\nДо сортировки:");

        for (Book book: allBooks) {
            System.out.println(book.getDateOfPublication());
        }

        Collections.sort(allBooks, new SortedBookByDateOfPublication());

        System.out.println("\nПосле сортировки:");

        for (Book book: allBooks) {
            System.out.println(book.getDateOfPublication());
        }

        System.out.println("\nСортировка по наличию");
        System.out.println("\nДо сортировки:");

        for (Book book: allBooks) {
            System.out.println(book.getAvailability());
        }

        Collections.sort(allBooks, new SortedBookByAvailability());

        System.out.println("\nПосле сортировки:");

        for (Book book: allBooks) {
            System.out.println(book.getAvailability());
        }

        System.out.println("\nСортировка заказов");

        LinkedList<Order> allOrder = om.listOrder;

        System.out.println("\nСортировка по дате исполнения");
        System.out.println("\nДо сортировки:");

        for (Order order: allOrder) {
            System.out.println(order.getDateOfExecution());
        }

        Collections.sort(allOrder, new SortedOrderByDateOfExecution());

        System.out.println("\nПосле сортировки:");

        for (Order order: allOrder) {
            System.out.println(order.getDateOfExecution());
        }

        System.out.println("\nСортировка по цене");
        System.out.println("\nДо сортировки:");

        for (Order order: allOrder) {
            System.out.println(order.getPriceOrder());
        }

        Collections.sort(allOrder, new SortedOrderByPriceOrder());

        System.out.println("\nПосле сортировки:");

        for (Order order: allOrder) {
            System.out.println(order.getPriceOrder());
        }


        System.out.println("\nСортировка по статусу заказа");
        System.out.println("\nДо сортировки:");

        for (Order order: allOrder) {
            System.out.println(order.getOrderStatus());
        }

        Collections.sort(allOrder, new SortedOrderByOrderStatus());

        System.out.println("\nПосле сортировки:");

        for (Order order: allOrder) {
            System.out.println(order.getOrderStatus());
        }



        
    }
}
