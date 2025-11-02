import java.util.LinkedList;

class Warehouse {

    private int currentCapacity; 
    private int maximumCapacity;
    public LinkedList<Book> listBook;

    /* Конструктор по умолчанию */
    Warehouse() {
        this.currentCapacity = 0;
        this.maximumCapacity = 100;
        listBook = new LinkedList<>();
    }

    /* Конструктор со всеми параметрами */
    Warehouse(int currentCapacity, int maximumCapacity) {
        this.currentCapacity = currentCapacity;
        this.maximumCapacity = maximumCapacity;
        listBook = new LinkedList<>();
    }

    /* Добавление книги на склад */
    public void addToWarehouse(Book book) {
        int availableSpace = this.maximumCapacity - this.currentCapacity;
        if (availableSpace <= 0) {
            System.out.println("На складе недостаточно места.");
            return;
        }

        if (book.getNumberOfCopies() > availableSpace) {
            System.out.println("На складе недостаточно места. Добавлено только " 
                    + availableSpace + " копий.");
            book.setNumberOfCopies(availableSpace);
        }

        listBook.add(book);
        this.currentCapacity += book.getNumberOfCopies();
        book.setAvailability(true);
        System.out.println("Книга добавлена на склад.");
    }

    /* Списание книги со склада */
    public void writeFromWarehouse(Book book) {
        if (listBook.contains(book)) {

            this.currentCapacity -= book.getNumberOfCopies();
            book.setNumberOfCopies(0);
            book.setAvailability(false);

            System.out.println("Книга списана со склада.");
        } else {
            System.out.println("Ошибка! данной книги нет на складе");
        }
    }

    /* Получить список залежавшихся книг */
    public LinkedList<Book> getStaleBooks() {
        LinkedList<Book> stale = new LinkedList<>();

        for (Book book : listBook) {
            // Проверяем, что книга есть на складе и не продана больше 6 месяцев
            if (book.isStale()) {
                stale.add(book);
            }
        }

        return stale;
    }


    /* Геттеры и сеттеры */
    public int getCurrentCapacity() {
        return this.currentCapacity;
    }

    public int getMaximumCapacity() {
        return this.maximumCapacity;
    }

    public void setCurrentCapacity(int newValue) {
        if (newValue <= this.maximumCapacity && newValue >= 0) {
            this.currentCapacity = newValue;
        } else {
            System.out.println("Ошибка! некорректное значение.");
        }
    }
}
