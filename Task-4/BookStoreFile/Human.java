/* Абстрактный класс человек */

public abstract class Human {

    private String fullName;
    private String password;
    private int age;

    /* Конструктор */
    public Human(String fullName, String password, int age) {
        this.fullName = fullName;
        this.password = password;
        setAge(age);
    }

    /* Геттеры и сеттеры */
    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 18) {
            this.age = age;
        } else {
            throw new IllegalArgumentException("Ошибка! Задан некорректный возраст.");
        }

    }

}