package senla.course.controller;

import senla.course.service.ServiceManager;

public abstract class Controller {

    protected ServiceManager serviceManager;

    public Controller(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }
}
