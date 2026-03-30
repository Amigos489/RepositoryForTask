package senla.course.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import senla.course.dto.OrderDto;
import senla.course.service.ServiceManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends Controller {

    public OrderController(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @PostMapping("/create/{bookId}/{emailUser}")
    @PreAuthorize("hasRole('ADMIN')")
    public void createOrder(@PathVariable("bookId") int bookId, @PathVariable("emailUser") String emailUser) {
        serviceManager.createOrder(bookId, emailUser);
    }

    @PostMapping("/closed/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void closedOrder(@PathVariable("id") int id) {
        serviceManager.closedOrder(id);
    }

    @PostMapping("/complected/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void complectedOrder(@PathVariable("id") int id) {
        serviceManager.complectedOrder(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public OrderDto getInfoOrder(@PathVariable("id") int id) {
        return serviceManager.getInfoOrder(id);
    }

    @GetMapping("/all/{criteria}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<OrderDto> getInfoAllOrders(@PathVariable("criteria") String criteria) {
        return serviceManager.getAllOrder(criteria);
    }

    @GetMapping("/complected-all/{startDate}/{endDate}/{criteria}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDto> getComplectedOrder(@PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                             @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                             @PathVariable("criteria") String criteria) {
        return serviceManager.getComplectedOrder(startDate, endDate, criteria);
    }

    @GetMapping("/complected-count/{startDate}/{endDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public Long getCountComplectedOrder(@PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return serviceManager.getCountComplectedOrder(startDate, endDate);
    }

    @GetMapping("/profit/{startDate}/{endDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public BigDecimal getProfit(@PathVariable("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @PathVariable("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return serviceManager.getProfit(startDate, endDate);
    }
}
