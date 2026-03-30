package senla.course.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import senla.course.dto.BookDto;
import senla.course.exception.BookNotFoundException;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test ServiceManager")
public class ServiceManagerTest {

    @Mock
    BookService bookService;

    @Mock
    OrderService orderService;

    @Mock
    RequestService requestService;

    @InjectMocks
    ServiceManager serviceManager;

    @Test
    @DisplayName("Given create order When correct book id Then not throw exception")
    void createOrder_correctBookId_notThrowException() {

        int id = 1;

        when(bookService.findBookById(1)).thenReturn(new BookDto());

        Assertions.assertDoesNotThrow(() -> serviceManager.createOrder(id, "emailTest"));
    }

    @Test
    @DisplayName("Given create order When book with specified id not found Then throw exception BookNotFoundException")
    void createOrder_bookWithSpecifiedIdNotFound_throwBookNotFoundException() {

        int id = 999;

        when(bookService.findBookById(999)).thenThrow(BookNotFoundException.class);

        Assertions.assertThrows(BookNotFoundException.class, () -> serviceManager.createOrder(id, "emailTest"));
    }
}