package senla.course.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.course.dto.RequestDto;
import senla.course.service.ServiceManager;

import java.util.List;

@RestController
@RequestMapping("/request")
public class RequestController extends Controller {

    public RequestController(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @GetMapping("/all/{criteria}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RequestDto> getInfoAllRequest(@PathVariable("criteria") String criteria) {
        return serviceManager.getAllRequest(criteria);
    }
}
