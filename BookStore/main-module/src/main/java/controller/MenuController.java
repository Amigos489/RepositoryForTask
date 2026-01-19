package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Scanner;

public abstract class MenuController {

    private static final Logger logger = LogManager.getLogger(MenuController.class);

    Scanner input = new Scanner(System.in);

    /* Считать выбор пользователя */
    public InputValidation getChoiceUser() {
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            input.nextLine();
            return new InputValidation(choice, false);
        } else {
            input.nextLine();
            logger.error("Некорректное число.", input);
            return new InputValidation(-1, true);
        }
    }

    /* Считать строку пользователя */
    public InputValidation getUserLine() {
        String line = input.nextLine().trim();
        if (line.length() == 0) {
            logger.error("Некорректная строка.");
            return new InputValidation(line, true);
        } else {
            return new InputValidation(line, false);
        }
    }
}