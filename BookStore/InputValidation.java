/* 
 * Класс результата ввода после валидации
 * Данный класс предназначен для передачи пользовательского ввода между компонентами приложения. 
 * Конструкторы определены таким образом, что в одном поле находится, 
 * пользовательский ввод (число, строка или дата). 
 * Во втором поле находится boolean, отвечающее за проверку корректности ввода. 
 */

import java.time.LocalDate;

public class InputValidation {
    private String inputLine;
    private boolean isError;
    private int inputChoise;
    private LocalDate date;

    /* Конструкторы */

    InputValidation(int inputChoise, boolean isError) {
        this.inputChoise = inputChoise;
        this.isError = isError;
        this.date = null;
        this.inputLine = null;
    }

    InputValidation(String inputLine, boolean isError) {
        this.inputLine = inputLine;
        this.isError = isError;
        this.date = null;
        this.inputChoise = 0;
    }

    InputValidation(LocalDate date, boolean isError) {
        this.date = date;
        this.isError = isError;
        this.inputChoise = 0;
        this.inputLine = null;
    }

    /* Геттеры */

    public String getInputLine() {
        return inputLine;
    }

    public int getInputChoice() {
        return inputChoise;
    }

    public boolean getIsError() {
        return isError;
    }

    public LocalDate getDate() {
        return date;
    }
}