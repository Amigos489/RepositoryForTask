package actions.importexportoperation;

import controller.Controller;
import enums.StatusOperationCsv;

public class ImportOperationDataCsvAction extends AbstractOperationDataCsvAction {

    public ImportOperationDataCsvAction(Controller controller, String[] entitys, String messageOperation) {
        super(controller, entitys, messageOperation);
    }

    @Override
    public StatusOperationCsv operationCsv(int choiceUser) {
        return controller.importCsv(choiceUser);
    }
}
