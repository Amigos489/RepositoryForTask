package controller;

import status.StatusMainMenu;

public class MainMenuController extends MenuController {

    public StatusMainMenu start() {
        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {

            switch (result.getInputChoice()) {
                case 1:
                    return StatusMainMenu.CREATE_CLIENT_MENU;
                case 2:
                    return StatusMainMenu.CREATE_LIBRARIAN_MENU;
                case 3:
                    return StatusMainMenu.IMPORT_EXPORT;
                case 4:
                    return StatusMainMenu.EXIT;
            }
        }
        return StatusMainMenu.UNCORRECT_CHOICE;
    }
}
