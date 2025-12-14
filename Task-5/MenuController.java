import java.util.Scanner; 

public class MenuController implements Controller{ 
    private MainMenu mainMenu; 
    private MenuClientController clientMenuController; 
    private MenuLibrarianController librarianMenuController; 
    private Scanner input = new Scanner(System.in); 
    
    /* Конструктор */ 
    MenuController(MainMenu mainMenu, MenuClientController clientMenuController, MenuLibrarianController librarianMenuController) { 
        this.mainMenu = mainMenu; 
        this.clientMenuController = clientMenuController; 
        this.librarianMenuController = librarianMenuController; 
    } 
    
    @Override
    public void startMenu() { 
        boolean running = true; 
        while (running) { 
            mainMenu.showMenu();

            switch (getChoiceUser()) { 
                case 1: { 
                    clientMenuController.startMenu(); 
                    break; 
                } case 2: { 
                    librarianMenuController.startMenu(); 
                    break; 
                } case 3: { 
                    running = false; 
                    mainMenu.printMessage("Выход из программы"); 
                    break; 
                } default: 
                    mainMenu.printMessage("Ошибка! некорректный ввод."); 
                    break; 
            } 
        } 
    } 

    public int getChoiceUser() {
        while (true) {
            if (input.hasNextInt()) {
                int choice = input.nextInt();
                input.nextLine();
                return choice;
            } else {
                input.nextLine();
                mainMenu.printMessage("Некорректный ввод. Введите число.");
            }
        }
    }

    public String getLineUser() {
        return input.nextLine();
    }
} 
  