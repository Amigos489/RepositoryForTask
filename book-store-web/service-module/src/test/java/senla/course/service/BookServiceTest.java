package senla.course.service;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import senla.course.dao.DaoManager;
import senla.course.dto.BookDto;
import senla.course.entitys.BookEntity;
import senla.course.exception.BookNotFoundException;
import senla.course.exception.IncorrectIdException;
import senla.course.exception.ListBookEmptyException;
import senla.course.mapper.BookMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Test BookService")
class BookServiceTest {

    static BookMapper mapper;

    @Mock
    DaoManager mockedDaoManager;

    @BeforeAll
    static void createBookMapper() {
        mapper = new BookMapper();
    }

    @Test
    @DisplayName("Given find book by id When correct book id Then get bookDto")
    void findBookById_correctBookId_getBook() {

        int id = 1;

        BookEntity bookEntity =
                new BookEntity("NameTest", "AuthorTest",
                        LocalDate.now(), LocalDate.now(),
                        BigDecimal.ZERO, true);
        bookEntity.setBookId(id);

        BookDto expectedBookDto = mapper.mappingEntityToDto(bookEntity);


        mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationFindBookById(1)).thenReturn(bookEntity);

        BookService bookService = new BookService(mockedDaoManager, mapper);

        BookDto actualBookDto = bookService.findBookById(id);

        Assertions.assertEquals(expectedBookDto.getId(), actualBookDto.getId());
    }

    @Test
    @DisplayName("Given find book by id When book with specified id not found Then throw exception BookNotFoundException")
    void findBookById_bookWithSpecifiedIdNotFound_throwBookNotFoundException() {

        int id = 999;

        mockedDaoManager = Mockito.mock(DaoManager.class);

        when(mockedDaoManager.operationFindBookById(999)).thenThrow(BookNotFoundException.class);

        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.findBookById(id));
    }

    @Test
    @DisplayName("Given find book by id When incorrect book id Then throw exception IncorrectIdException")
    void findBookById_incorrectId_throwIncorrectIdException() {

        Integer firstIncorrectId = null;
        Integer secondIncorrectId = -1;

        mockedDaoManager = Mockito.mock(DaoManager.class);

        when(mockedDaoManager.operationFindBookById(Mockito.argThat(arg -> arg == null || arg < 0))).thenThrow(IncorrectIdException.class);

        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertThrows(IncorrectIdException.class, () -> bookService.findBookById(firstIncorrectId));
        Assertions.assertThrows(IncorrectIdException.class, () -> bookService.findBookById(secondIncorrectId));
    }

    @Test
    @DisplayName("Given get all books When list book not empty Then get list book")
    void getAllBook_ListBookNotEmpty_getListBook() {

        List<BookEntity> booksEntity = new ArrayList<>();
        BookEntity book1 = new BookEntity();
        book1.setBookId(1);
        booksEntity.add(book1);

        BookEntity book2 = new BookEntity();
        book2.setBookId(2);
        booksEntity.add(book2);

        List<BookDto> booksDto = new ArrayList<>();

        booksDto.add(mapper.mappingEntityToDto(book1));
        booksDto.add(mapper.mappingEntityToDto(book2));

        mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetAllBook("CriteriaTest")).thenReturn(booksEntity);

        BookService bookService = new BookService(mockedDaoManager, mapper);

        List<BookDto> bookDtoActual = bookService.getAllBook("CriteriaTest");

        Assertions.assertEquals(booksDto.size(), bookDtoActual.size());

        for (int i = 0; i < bookDtoActual.size(); i++) {
            Assertions.assertEquals(booksDto.get(i).getId(), bookDtoActual.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get all books When list book empty Then throw exception ListBookEmptyException")
    void getAllBook_ListBookEmpty_throwListBookEmptyException() {

        List<BookEntity> emptyBooksEntity = new ArrayList<>();

        mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetAllBook("CriteriaTest")).thenReturn(emptyBooksEntity);

        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertThrows(ListBookEmptyException.class, () -> bookService.getAllBook("CriteriaTest"));
    }

    @Test
    @DisplayName("Given add book on warehouse When correct book id Then not throw exception")
    void addBookOnWarehouse_correctBookId_notThrowException() {

        int id = 2;

        mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationFindBookById(2)).thenReturn(new BookEntity());

        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertDoesNotThrow(() -> bookService.addBookOnWarehouse(id));
    }

    @Test
    @DisplayName("Given add book on warehouse When incorrect book id Then throw exception IncorrectIdException")
    void addBookOnWarehouse_bookWithSpecifiedIdNotFound_throwIncorrectIdException() {

        Integer firstIncorrectId = null;
        Integer secondIncorrectId = -1;

        mockedDaoManager = Mockito.mock(DaoManager.class);
        doThrow(IncorrectIdException.class).when(mockedDaoManager).operationAddBookWarehouse(Mockito.argThat(arg -> arg == null || arg < 0));
        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertThrows(IncorrectIdException.class, () -> bookService.addBookOnWarehouse(firstIncorrectId));
        Assertions.assertThrows(IncorrectIdException.class, () -> bookService.addBookOnWarehouse(secondIncorrectId));
    }

    @Test
    @DisplayName("Given add book on warehouse When book with specified id not found Then throw exception BookNotFoundException")
    void addBookOnWarehouse_bookWithSpecifiedIdNotFound_throwBookNotFoundException() {

        int id = 999;

        mockedDaoManager = Mockito.mock(DaoManager.class);
        doThrow(BookNotFoundException.class).when(mockedDaoManager).operationAddBookWarehouse(999);

        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.addBookOnWarehouse(id));
    }

    @Test
    @DisplayName("Given write book from warehouse When correct book id Then not throw exception")
    void writeBookFromWarehouse_correctBookId_notThrowException() {

        int id = 5;

        mockedDaoManager = Mockito.mock(DaoManager.class);

        when(mockedDaoManager.operationFindBookById(5)).thenReturn(new BookEntity());

        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertDoesNotThrow(() -> bookService.writeBookOnWarehouse(id));
    }

    @Test
    @DisplayName("Given write book on warehouse When incorrect book id Then throw exception IncorrectIdException")
    void writeBookFromWarehouse_bookWithSpecifiedIdNotFound_throwIncorrectIdException() {

        Integer firstIncorrectId = null;
        Integer secondIncorrectId = -1;

        mockedDaoManager = Mockito.mock(DaoManager.class);

        doThrow(new IncorrectIdException(firstIncorrectId)).when(mockedDaoManager).operationWriteBookWarehouse(Mockito.argThat(arg -> arg == null || arg < 0));

        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertThrows(IncorrectIdException.class, () -> bookService.writeBookOnWarehouse(firstIncorrectId));
        Assertions.assertThrows(IncorrectIdException.class, () -> bookService.writeBookOnWarehouse(secondIncorrectId));
    }

    @Test
    @DisplayName("Given write book on warehouse When book with specified id not found Then throw exception BookNotFoundException")
    void writeBookFromWarehouse_bookWithSpecifiedIdNotFound_throwBookNotFoundException() {

        int id = 999;

        mockedDaoManager = Mockito.mock(DaoManager.class);
        doThrow(BookNotFoundException.class).when(mockedDaoManager).operationWriteBookWarehouse(999);
        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertThrows(BookNotFoundException.class ,() -> bookService.writeBookOnWarehouse(id));
    }

    @Test
    @DisplayName("Given get stale books When list stale book not empty Then get list book")
    void getStaleBook_ListBookNotEmpty_getListBook() {

        List<BookEntity> staleBooksEntity = new ArrayList<>();
        BookEntity book1 = new BookEntity();
        book1.setBookId(1);
        staleBooksEntity.add(book1);

        BookEntity book2 = new BookEntity();
        book2.setBookId(2);
        staleBooksEntity.add(book2);

        List<BookDto> staleBooksDto = new ArrayList<>();

        staleBooksDto.add(mapper.mappingEntityToDto(book1));
        staleBooksDto.add(mapper.mappingEntityToDto(book2));

        mockedDaoManager = Mockito.mock(DaoManager.class);

        when(mockedDaoManager.operationGetStaleBook(6,"CriteriaTest")).thenReturn(staleBooksEntity);

        BookService bookService = new BookService(mockedDaoManager, mapper);

        List<BookDto> actualBooksDto = bookService.getStaleBook("CriteriaTest");

        Assertions.assertEquals(staleBooksDto.size(), actualBooksDto.size());

        for (int i = 0; i < actualBooksDto.size(); i++) {
            Assertions.assertEquals(staleBooksDto.get(i).getId(), actualBooksDto.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get stale books When list stale book empty Then throw exception ListBookEmptyException")
    void getStaleBook_ListBookEmpty_throwListBookEmptyException() {

        List<BookEntity> staleBooksEntity = new ArrayList<>();

        mockedDaoManager = Mockito.mock(DaoManager.class);
        when(mockedDaoManager.operationGetStaleBook(6,"Test")).thenReturn(staleBooksEntity);

        BookService bookService = new BookService(mockedDaoManager, mapper);

        Assertions.assertThrows(ListBookEmptyException.class, () -> bookService.getStaleBook("Test"));
    }
}
