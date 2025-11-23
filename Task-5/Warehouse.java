import java.util.ArrayList;

class Warehouse {

    private int currentCapacity; 
    private int maximumCapacity;
    private ArrayList<Book> listBook;

    /* Конструктор по умолчанию */
    Warehouse() {
        this.currentCapacity = 0;
        this.maximumCapacity = 100;
        listBook = new ArrayList<>();
    }

    /* Конструктор со всеми параметрами */
    Warehouse(int currentCapacity, int maximumCapacity) {
        this.currentCapacity = currentCapacity;
        this.maximumCapacity = maximumCapacity;
        listBook = new ArrayList<>();
    }

    /* Добавление книги на склад */
    public StatusAddBook addToWarehouse(Book book) {
        int availableSpace = this.maximumCapacity - this.currentCapacity;
        if (availableSpace <= 0) {
            return StatusAddBook.FAIL;
        }

        if (book.getNumberOfCopies() > availableSpace) {
            book.setNumberOfCopies(availableSpace);
            listBook.add(book);
            this.currentCapacity += availableSpace;
            book.setAvailability(true);
            return StatusAddBook.ONLYPART;
        }

        listBook.add(book);
        this.currentCapacity += book.getNumberOfCopies();
        book.setAvailability(true);
        return StatusAddBook.SUCCESSFULY;
    }

    /* Списание книги со склада */
    public boolean writeFromWarehouse(String nameBook) {
        for (Book book : listBook) {
            if (book.getNameBook().equalsIgnoreCase(nameBook.trim())) {
                this.currentCapacity -= book.getNumberOfCopies();
                book.setNumberOfCopies(0);
                book.setAvailability(false);
                return true; // книга успешно списана
            }
        }
        return false; // книга не найдена
    }

    /* Получить список залежавшихся книг */
    public ArrayList<Book> getStaleBooks() {
        ArrayList<Book> stale = new ArrayList<>();

        for (Book book : listBook) {
            // Проверяем, что книга есть на складе и не продана больше 6 месяцев
            if (book.isStale()) {
                stale.add(book);
            }
        }

        return stale;
    }

    /* Поиск книги по названию */
    public Book findBookByName(String nameBook) {
        for (Book book : listBook) {
            if (book.getNameBook().equalsIgnoreCase(nameBook.trim())) {
                return book;
            }
        }
        return null;
    }

    /* Поиск книги по названию */
    public Book findBookByID(int ID) {
        for (Book book : listBook) {
            if (book.getId() == ID) {
                return book;
            }
        }
        return null;
    }

    /* Геттеры и сеттеры */
    public int getCurrentCapacity() {
        return this.currentCapacity;
    }

    public int getMaximumCapacity() {
        return this.maximumCapacity;
    }

    public ArrayList<Book> getListBooks() {
        return this.listBook;
    }

    public boolean setCurrentCapacity(int newValue) {
        if (newValue <= this.maximumCapacity && newValue >= 0) {
            this.currentCapacity = newValue;
            return true;
        } else {
            return false;
        }
    }

}
