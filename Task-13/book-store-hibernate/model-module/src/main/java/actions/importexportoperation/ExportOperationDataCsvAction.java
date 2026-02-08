package actions.importexportoperation;

import controller.Controller;
import enums.StatusOperationCsv;

public class ExportOperationDataCsvAction extends AbstractOperationDataCsvAction {

    public ExportOperationDataCsvAction(Controller controller, String[] entitys, String messageOperation) {
        super(controller, entitys, messageOperation);
    }

    @Override
    public StatusOperationCsv operationCsv(int choiceUser) {
        return controller.exportCsv(choiceUser);
    }
}
