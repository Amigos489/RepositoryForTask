public class Librarian extends Human {

    private static int librarianIdCounter = 1;

    private int librarianId;
    private String phone;

    /* Конструктор */
    public Librarian(String fullName, String password, int age, String phone) {
        super(fullName, password, age);
        this.phone = phone;
        this.librarianId = librarianIdCounter++;
    }

    /* Геттеры */
    public String getPhone() {
        return phone;
    }

    public int getId() {
        return this.librarianId;
    }

    /* Сеттеры */
    public void setId(int ID) {
        if (ID > 0) {
            this.librarianId = ID;
        } else {
            throw new IllegalArgumentException();
        }
    }

}