public class ImportExportMenuController extends MenuController {

    private ServiceStoreBook serviceStoreBook;

    ImportExportMenuController(ServiceStoreBook serviceStoreBook) {
        this.serviceStoreBook = serviceStoreBook;
    }

    public StatusExportImportMenu start() {

        InputValidation result = super.getChoiceUser();
        if (!result.getIsError()) {
            switch (result.getInputChoice()) {
                case 1: {
                    return StatusExportImportMenu.OPEN_IMPORT;
                }
                case 2: {
                    return StatusExportImportMenu.OPEN_EXPORT;
                }
            }
        }
        return StatusExportImportMenu.UNCORRECT_CHOICE;
    }

    public StatusImportExport importEssence() {

        InputValidation resultUserInput = super.getChoiceUser();
        if (!resultUserInput.getIsError()) {
            int choiceUser = resultUserInput.getInputChoice();
            return serviceStoreBook.importEssenceFromFileCSV(choiceUser);
        } else {
            return StatusImportExport.FAIL;
        }

    }

    public StatusImportExport exportEssence() {

        InputValidation resultUserInput = super.getChoiceUser();
        if (!resultUserInput.getIsError()) {
            int choiceUser = resultUserInput.getInputChoice();
            return serviceStoreBook.exportEssenceInFileCSV(choiceUser);
        } else {
            return StatusImportExport.FAIL;
        }
        
    }
    
}
