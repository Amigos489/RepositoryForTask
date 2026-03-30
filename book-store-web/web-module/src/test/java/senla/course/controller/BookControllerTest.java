package senla.course.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import senla.course.service.BookService;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    BookService bookService;

    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @Test
    public void addBookToWarehouse() {

    }

    @Test
    public void writeBookToWarehouse() {

    }

    @Test
    public void getInfoBook() {

    }

    @Test
    public void getInfoAllBooks() {

    }

    @Test
    public void getInfoStaleBooks() {

    }
}
