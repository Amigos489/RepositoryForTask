package senla.course.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import senla.course.dto.BookDto;
import senla.course.exception.BookNotFoundException;
import senla.course.exception.IncorrectIdException;
import senla.course.exception.ListBookEmptyException;
import senla.course.service.ServiceManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    ServiceManager serviceManager;

    @InjectMocks
    BookController bookController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).setControllerAdvice(GlobalExceptionHandler.class).build();
    }

    @Test
    @DisplayName("Given add book to warehouse When correct book id Then get status ok")
    public void addBookToWarehouse_correctBookId_getStatusOk() throws Exception {

        int id = 1;
        mockMvc.perform(post("/book/add/{id}", id)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given add book to warehouse When book with specify id not found Then get status not found")
    public void addBookToWarehouse_bookWithSpecifyIdNotFound_getStatusNotFound() throws Exception {

        int id = 999;

        doThrow(BookNotFoundException.class).when(serviceManager).addBookToWarehouse(999);
        mockMvc.perform(post("/book/add/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given add book to warehouse When incorrect book id Then get status bad request")
    public void addBookToWarehouse_incorrectBookId_getStatusBadRequest() throws Exception {

        Integer incorrectId = -3;

        doThrow(new IncorrectIdException(incorrectId)).when(serviceManager).addBookToWarehouse(argThat(arg -> arg <= 0));

        mockMvc.perform(post("/book/add/{id}", incorrectId)).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given write book from warehouse When correct book id Then get status ok")
    public void writeBookToWarehouse_correctBookId_getStatusOk() throws Exception {

        int id = 1;
        mockMvc.perform(post("/book/write/{id}", id)).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given write book from warehouse When book with specify id not found Then get status not found")
    public void writeBookToWarehouse_bookWithSpecifyIdNotFound_getStatusNotFound() throws Exception {

        int id = 999;

        doThrow(BookNotFoundException.class).when(serviceManager).writeBookToWarehouse(999);
        mockMvc.perform(post("/book/write/{id}", id)).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given write book from warehouse When incorrect book id Then get status bad request")
    public void writeBookToWarehouse_incorrectBookId_getStatusBadRequest() throws Exception {

        Integer id = -3;

        doThrow(new IncorrectIdException(id)).when(serviceManager).writeBookToWarehouse(argThat(arg -> arg <= 0));

        mockMvc.perform(post("/book/write/{id}", id)).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given get info book When correct book id Then get status ok and bookDto")
    public void getInfoBook_correctBookId_getStatusOkAndBookDto() throws Exception {

        int id = 1;

        BookDto book = new BookDto(1, "Название",
                "Автор", LocalDate.now(),
                LocalDate.now(), BigDecimal.ZERO, true);

        when(serviceManager.getInfoBook(1)).thenReturn(book);

        mockMvc.perform(get("/book/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nameBook").value("Название"))
                .andExpect(jsonPath("$.authorBook").value("Автор"));
    }

    @Test
    @DisplayName("Given get info book When book with specify id not found Then get status not found")
    public void getInfoBook_bookWithSpecifyIdNotFound_getStatusNotFound() throws Exception {

        int id = 999;

        doThrow(BookNotFoundException.class).when(serviceManager).getInfoBook(999);

        mockMvc.perform(get("/book/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given get info all book When list book not empty Then get status ok and list book")
    public void getInfoAllBooks_listBookNotEmpty_getStatusOkAndListBook() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        List<BookDto> expectedBooks =new ArrayList<>();
        BookDto book1 = new BookDto();
        book1.setBookId(1);
        expectedBooks.add(book1);

        BookDto book2 = new BookDto();
        book2.setBookId(2);
        expectedBooks.add(book2);

        when(serviceManager.getAllBook("CriteriaTest")).thenReturn(expectedBooks);

        MvcResult mvcResult = mockMvc.perform(get("/book/all/{criteria}", "CriteriaTest"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        List<BookDto> booksActual = objectMapper.readValue(jsonResponse, new TypeReference<List<BookDto>>() {});

        Assertions.assertEquals(expectedBooks.size(), booksActual.size());

        for (int i = 0; i < expectedBooks.size(); i++) {
            Assertions.assertEquals(expectedBooks.get(i).getId(),booksActual.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get info all book When list book empty Then get status not found")
    public void getInfoAllBooks_listBookEmpty_getStatusNotFound() throws Exception {

        List<BookDto> staleBooks = new ArrayList<>();

        doThrow(new ListBookEmptyException()).when(serviceManager).getAllBook("CriteriaTest");

        mockMvc.perform(get("/book/all/{criteria}", "CriteriaTest"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Given get info stale book When list stale book not empty Then get status ok and list stale book")
    public void getInfoStaleBooks_listStaleBookNotEmpty_getStatusOkAndListStaleBook() throws Exception {

        List<BookDto> staleBooks =new ArrayList<>();
        BookDto book1 = new BookDto();
        book1.setBookId(1);
        staleBooks.add(book1);

        when(serviceManager.getStaleBook("Test")).thenReturn(staleBooks);

        MvcResult mvcResult = mockMvc.perform(get("/book/stale/{criteria}", "Test"))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        List<BookDto> booksActual = objectMapper.readValue(jsonResponse, new TypeReference<List<BookDto>>() {});

        Assertions.assertEquals(staleBooks.size(), booksActual.size());

        for (int i = 0; i < staleBooks.size(); i++) {
            Assertions.assertEquals(staleBooks.get(i).getId(),booksActual.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get info stale book When list stale book empty Then get status not found")
    public void getInfoStaleBooks_listStaleBookEmpty_getStatusNotFound() throws Exception {

        List<BookDto> staleBooks = new ArrayList<>();

        doThrow(new ListBookEmptyException()).when(serviceManager).getStaleBook("Test");

        mockMvc.perform(get("/book/stale/{criteria}", "Test"))
                .andExpect(status().isNotFound());
    }
}
