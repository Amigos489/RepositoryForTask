import java.util.Random;

public class EmployeesFirma {
    public static void main(String[] args) {
        System.out.println("Фирма N специализируется на производстве программного обеспечения");
        Employee[] arrayEmployee = new Employee[5];
        System.out.println("На данный момент в фирме N числится " + arrayEmployee.length + " соотрудников");

        arrayEmployee[0] = new Programmer(22, "Иванов Иван Иванович", 35_000, "Java", "Джун");
        arrayEmployee[1] = new Programmer(20, "Сергеев Сергей Сергеевич", 35_000, "Java", "Джун");
        arrayEmployee[2] = new QaEngineer(20, "Николаев Николай Николаевич", 30_000, "Ручное тестирование", "Bugzilla");
        arrayEmployee[3] = new QaEngineer(26, "Михайлов Михаил Михайлович", 40_000, "Автоматическое тестирование", "Bugzilla");
        arrayEmployee[4] = new WebDeveloper(28, "Константинов Константин Константинович", 45_000, "JavaScript", "Мидл", "HTML");

        int totalSalary = 0;
        for (Employee i: arrayEmployee) {
            totalSalary += i.salary;
        }
        System.out.println("Общий ежемесячный оклад составляет: " + totalSalary);

    }
}


abstract class Employee {
    protected int age; //Возраст
    protected String FIO; //ФИО
    protected int salary; //Зарплата
    protected int workExperience; //Опыт работы
    protected boolean knowledgeEnglish; //Знание английского языка
    protected boolean beingOnVacation; //Нахождение в отпуске
    protected boolean beOnSickLeave; //Нахождение на больничном


    //Конструктор по умолчанию
    Employee() {
        this.age = 18;
        this.FIO = "Фамилия Имя Отчество";
        this.salary = 22_440;
        this.workExperience = 0;
        this.knowledgeEnglish = false;
        this.beingOnVacation = false;
        this.beOnSickLeave = false;
    }

    //Конструктор со всеми параметрами
    Employee(int age, String FIO, int salary, int workExperience, boolean knowledgeEnglish, boolean beingOnVacation, boolean beOnSickLeave) {
        this.age = age;
        this.FIO = FIO;
        this.salary = salary;
        this.workExperience = workExperience;
        this.knowledgeEnglish = knowledgeEnglish;
        this.beingOnVacation = beingOnVacation;
        this.beOnSickLeave = beOnSickLeave;
    }

    //Конструктор с 3-мя параметрами
    Employee(int age, String FIO, int salary) {
        this.age = age;
        this.FIO = FIO;
        this.salary = salary;
        this.workExperience = 0;
        this.knowledgeEnglish = false;
        this.beingOnVacation = false;
        this.beOnSickLeave = false;
    }

    //геттеры и сеттеры

    int getAge() {
        return this.age;
    }

    void setAge(int newValue) {
        if (newValue >= 18 && newValue >= 65) {
            this.age = newValue;
            return;
        }
        System.out.println("Введено некорректное значение!");
    }

    String getFIO() {
        return this.FIO;
    }

    int getSalary() {
        return this.salary;
    }

    void setSalary(int newValue) {
        if (newValue < 22_440) {
            this.salary = newValue;
            return;
        }
        System.out.println("Введено некорректное значение!");
    }

    int getWorkExperience() {
        return this.workExperience;
    }

    void setWorkExperience(int newValue) {
        if (newValue < 0) {
            this.workExperience = newValue;
            return;
        }
        System.out.println("Введено некорректное значение!");
    }

    boolean getKnowledgeEnglish() {
        return this.knowledgeEnglish;
    }

    void setKnowledgeEnglish(boolean newValue) {
        this.knowledgeEnglish = newValue;
    }

    //Уйти в отпуск
    void goOnVacation() {
        this.beingOnVacation = true;
    }

    //Вернуться из отпуска
    void ReturnFromVacation() {
        this.beingOnVacation = false;
    }

    //Уйти на больничный
    void goOnSickLeave() {
        this.beOnSickLeave = true;
    }

    //Вернуться с больничного
    void ReturnFromSickLeave() {
        this.beOnSickLeave = false;
    }

}

class Programmer extends Employee{
    protected String programmingLanguage;
    protected String classificationLevel;


    //Конструктор по умолчанию
    Programmer() {
        super();
        this.programmingLanguage = "Язык программирования";
        this.classificationLevel = "Уровень квалификации";
    }

    //Конструктор со всеми параметрами
    Programmer(int age, String FIO, int salary, int workExperience, boolean knowledgeEnglish, boolean beingOnVacation, boolean beOnSickLeave, String programmingLanguage, String classificationLevel) {
        super(age, FIO, salary, workExperience, knowledgeEnglish, beingOnVacation, beOnSickLeave);
        this.classificationLevel = classificationLevel;
        this.programmingLanguage = programmingLanguage;
    }

    //Конструктор со 5-ю параметрами
    Programmer(int age, String FIO, int salary, String programmingLanguage, String classificationLevel) {
        super(age, FIO, salary);
        this.classificationLevel = classificationLevel;
        this.programmingLanguage = programmingLanguage;
    }

    //сеттеры и геттеры
    String getProgrammingLanguage() {
        return this.programmingLanguage;
    }

    void setProgrammingLanguage(String newValue) {
        this.programmingLanguage = newValue;
    }

    String getClassificationLevel() {
        return this.classificationLevel;
    }

    void setClassificationLevel(String newValue) {
        this.classificationLevel = newValue;
    }
}

class WebDeveloper extends Programmer {
    protected String hypertextMarkupLanguage;

    //Конструктор по умолчанию
    WebDeveloper() {
        super();
        this.hypertextMarkupLanguage = "Язык гипертекстовой разметки";
    }

    //Конструктор со всеми параметрами
    WebDeveloper(int age, String FIO, int salary, int workExperience, boolean knowledgeEnglish, boolean beingOnVacation, boolean beOnSickLeave, String programmingLanguage, String classificationLevel, String hypertextMarkupLanguage) {
        super(age, FIO, salary, workExperience, knowledgeEnglish, beingOnVacation, beOnSickLeave, programmingLanguage, classificationLevel);
        this.hypertextMarkupLanguage = hypertextMarkupLanguage;
    }

    //Конструктор с 6-ю параметрами
    WebDeveloper(int age, String FIO, int salary, String programmingLanguage, String classificationLevel, String hypertextMarkupLanguage) {
        super(age, FIO, salary, programmingLanguage, classificationLevel);
        this.hypertextMarkupLanguage = hypertextMarkupLanguage;
    }

    //Сеттры и геттеры

    String getHypertextMarkupLanguage() {
        return this.hypertextMarkupLanguage;
    }

    void setHypertextMarkupLanguage(String newValue) {
        this.hypertextMarkupLanguage = newValue;
    }
}

class QaEngineer extends Employee {
    String typeTesting;
    String bugTrackingSystem;

    //Конструктор по умолчанию
    QaEngineer() {
        super();
        this.typeTesting = "Вид тестирования";
        this.bugTrackingSystem = "Система отслеживания ошибок";
    }

    //Конструктор с 5-ю параметрами
    QaEngineer(int age, String FIO, int salary, String typeTesting, String bugTrackingSystem) {
        super(age, FIO, salary);
        this.typeTesting = typeTesting;
        this.bugTrackingSystem = bugTrackingSystem;
    }

    //Конструктор со всеми параметрами
    QaEngineer(int age, String FIO, int salary, int workExperience, boolean knowledgeEnglish, boolean beingOnVacation, boolean beOnSickLeave, String typeTesting, String bugTrackingSystem) {
        super(age, FIO, salary, workExperience, knowledgeEnglish, beingOnVacation, beOnSickLeave);
        this.typeTesting = typeTesting;
        this.bugTrackingSystem = bugTrackingSystem;
    }

    //геттеры и сеттеры
    String getTypeTesting() {
        return this.typeTesting;
    }

    void setTypeTesting(String typeTesting) {
        this.typeTesting = typeTesting;
    }

    String getBugTrackingSystem() {
        return this.bugTrackingSystem;
    }

    void setBugTrackingSystem(String typeTesting) {
        this.bugTrackingSystem = bugTrackingSystem;
    }





}
