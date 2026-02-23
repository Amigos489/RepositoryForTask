package senla.course.controller;

import org.springframework.web.bind.annotation.*;
import senla.course.dto.BookDto;
import senla.course.service.ServiceManager;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController extends Controller {

    public BookController(ServiceManager serviceManager) {
        super(serviceManager);
    }

    @PostMapping("/add/{id}")
    public void addBookToWarehouse(@PathVariable("id") int id) {
        serviceManager.addBookToWarehouse(id);
    }

    @PostMapping("/write/{id}")
    public void writeBookToWarehouse(@PathVariable("id") int id) {
        serviceManager.writeBookToWarehouse(id);
    }

    @GetMapping("/{id}")
    public BookDto getInfoBook(@PathVariable("id") int id) {
        return serviceManager.getInfoBook(id);
    }

    @GetMapping("/all/{criteria}")
    public List<BookDto> getInfoAllBooks(@PathVariable("criteria") String criteria) {
        return serviceManager.getAllBook(criteria);
    }

    @GetMapping("/stale/{criteria}")
    public List<BookDto> getInfoStaleBooks(@PathVariable("criteria") String criteria) {
        return serviceManager.getStaleBook(criteria);
    }
}
