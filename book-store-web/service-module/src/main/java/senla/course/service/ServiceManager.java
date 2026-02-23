package senla.course.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import senla.course.dto.BookDto;
import senla.course.dto.OrderDto;
import senla.course.dto.RequestDto;
import senla.course.entitys.BookEntity;
import senla.course.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ServiceManager {

    private final BookService bookService;
    private final OrderService orderService;
    private final RequestService requestService;

    public ServiceManager(BookService bookService, OrderService orderService, RequestService requestService) {
        this.bookService = bookService;
        this.orderService = orderService;
        this.requestService = requestService;
    }

    public void addBookToWarehouse(Integer id) {
        bookService.addBookOnWarehouse(id);
    }

    public void writeBookToWarehouse(Integer id) {
        bookService.writeBookOnWarehouse(id);
    }

    @Transactional(readOnly = true)
    public BookDto getInfoBook(Integer id) {
        return bookService.findBookById(id);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBook(String criteria) {
        return bookService.getAllBook(criteria);
    }

    @Transactional(readOnly = true)
    public List<BookDto> getStaleBook(String criteria) {
        return bookService.getStaleBook(criteria);
    }

    public void createOrder(int bookId, String emailUser) {

        BookDto book = bookService.findBookById(bookId);
        if (book.getAvailability()) {
            orderService.createOrder(bookId, emailUser, OrderStatus.NEW);
            return;
        }

        orderService.createOrder(bookId, emailUser, OrderStatus.WAITING);
        requestService.createRequest(bookId);
    }

    public void closedOrder(int id) {
        orderService.closedOrder(id);
    }

    public void complectedOrder(Integer id) {
        orderService.complectedOrder(id);
    }

    @Transactional(readOnly = true)
    public OrderDto getInfoOrder(int id) {
        return orderService.findOrderById(id);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrder(String criteria) {
        return orderService.getAllOrder(criteria);
    }

    @Transactional(readOnly = true)
    public List<OrderDto> getComplectedOrder(LocalDate startDate, LocalDate endDate, String criteria) {
        return orderService.getComplectedOrder(startDate, endDate, criteria);
    }

    @Transactional(readOnly = true)
    public Long getCountComplectedOrder(LocalDate startDate, LocalDate endDate) {
        return orderService.getCountComplectedOrder(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public BigDecimal getProfit(LocalDate startDate, LocalDate endDate) {
        return orderService.getProfit(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<RequestDto> getAllRequest(String criteria) {
        return requestService.getAllRequest(criteria);
    }
}
