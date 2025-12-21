import java.util.ArrayList;

public class BookView {

    /* Отобразить список книг */
    public static void printBooks(ArrayList<Book> books) {

        for (Book book : books) {
            System.out.println("\n");
            printBookInfo(book);
            System.out.println("\n");
        }

    }

    /* Отобразить информацию о книге */
    public static void printBookInfo(Book book) {

        System.out.println("Название книги: " + book.getNameBook());
        System.out.println("Автор книги: " + book.getAuthorBook());
        System.out.println("Количество копий: " + book.getNumberOfCopies());
        System.out.println("Количество страниц:" + book.getNumberPages());
        System.out.println("Цена: " + book.getPrice());
        System.out.println("Дата публикации: " + book.getDateOfPublication());
        System.out.println("Дата добавления на склад: " + book.getDateAddedToWarehouse());
        
    }

}