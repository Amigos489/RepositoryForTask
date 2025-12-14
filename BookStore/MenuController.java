/* Абстрактный класс контроллера */

import java.util.Scanner;

public abstract class MenuController {

    Scanner input = new Scanner(System.in);

    /* Считать выбор пользователя */
    public InputValidation getChoiceUser() {
        if (input.hasNextInt()) {
            int choice = input.nextInt();
            input.nextLine();
            return new InputValidation(choice, false);
        } else {
            input.nextLine();
            return new InputValidation(-1, true);
        }

    }

    /* Считать строку пользователя */
    public InputValidation getUserLine() {
        String line = input.nextLine().trim();
        if (line.length() == 0) {
            return new InputValidation(line, true);
        } else {
            return new InputValidation(line, false);
        }
    }

}