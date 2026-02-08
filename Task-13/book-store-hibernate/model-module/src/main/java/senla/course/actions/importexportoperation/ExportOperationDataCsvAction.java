package senla.course.actions.importexportoperation;

import senla.course.controller.Controller;
import senla.course.enums.StatusOperationCsv;

public class ExportOperationDataCsvAction extends AbstractOperationDataCsvAction {

    public ExportOperationDataCsvAction(Controller controller, String[] entitys, String messageOperation) {
        super(controller, entitys, messageOperation);
    }

    @Override
    public StatusOperationCsv operationCsv(int choiceUser) {
        return controller.exportCsv(choiceUser);
    }
}
