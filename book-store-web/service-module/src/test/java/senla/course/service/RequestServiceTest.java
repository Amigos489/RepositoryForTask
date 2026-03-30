package senla.course.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import senla.course.dao.*;
import senla.course.dto.RequestDto;
import senla.course.entitys.BookEntity;
import senla.course.entitys.RequestEntity;
import senla.course.exception.BookNotFoundException;
import senla.course.exception.ListRequestEmptyException;
import senla.course.mapper.RequestMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test RequestService")
public class RequestServiceTest {

    static RequestMapper mapper;

    @Mock
    BookDaoImpl bookDao;

    @Mock
    RequestDaoImpl requestDao;

    @InjectMocks
    DaoManager daoManager;

    @BeforeAll
    static void createMapper() {
        mapper = new RequestMapper();
    }

    @Test
    @DisplayName("Given create request When correct book id Then not throw exception")
    void createRequest_correctBookId_notThrowException() {

        int id = 1;

        BookEntity bookEntityForTest =
                new BookEntity("Название", "Автор",
                        LocalDate.now(), LocalDate.now(),
                        BigDecimal.ZERO, true);
        bookEntityForTest.setBookId(id);

        when(bookDao.findBookById(1)).thenReturn(bookEntityForTest);

        RequestService requestService = new RequestService(daoManager, mapper);
        Assertions.assertDoesNotThrow(() -> requestService.createRequest(id));
    }

    @Test
    @DisplayName("Given create request When book with specified id not found Then throw exception BookNotFoundException")
    void createRequest_bookWithSpecifiedIdNotFound_throwBookNotFoundException() {

        int id = 999;

        when(bookDao.findBookById(999)).thenReturn(null);

        RequestService requestService = new RequestService(daoManager, mapper);
        Assertions.assertThrows(BookNotFoundException.class, () -> requestService.createRequest(id));
    }

    @Test
    @DisplayName("Given get all request When list request not empty Then get list request")
    public void getAllRequest_listRequestNotEmpty_getListRequest() {

        List<RequestEntity> requestsEntity = new ArrayList<>();

        RequestEntity request1 = new RequestEntity();
        request1.setRequestId(1);
        BookEntity book1 = new BookEntity();
        book1.setBookId(1);
        request1.setBook(book1);
        requestsEntity.add(request1);

        RequestEntity request2 = new RequestEntity();
        request2.setRequestId(2);
        BookEntity book2 = new BookEntity();
        book2.setBookId(2);
        request2.setBook(book2);
        requestsEntity.add(request2);

        List<RequestDto> requestsDto = new ArrayList<>();

        requestsDto.add(mapper.mappingEntityToDto(request1));
        requestsDto.add(mapper.mappingEntityToDto(request2));

        when(requestDao.findAll("CriteriaTest")).thenReturn(requestsEntity);

        RequestService requestService = new RequestService(daoManager, mapper);

        List<RequestDto> requestDtoActual = requestService.getAllRequest("CriteriaTest");

        Assertions.assertEquals(requestsDto.size(), requestDtoActual.size());

        for (int i = 0; i < requestDtoActual.size(); i++) {
            Assertions.assertEquals(requestsDto.get(i).getId(), requestDtoActual.get(i).getId());
        }
    }

    @Test
    @DisplayName("Given get all request When list request empty Then throw exception ListRequestEmptyException")
    public void getAllRequest_listRequestEmpty_throwListRequestEmptyException() {

        List<RequestEntity> emptyRequestsEntity = new ArrayList<>();

        when(requestDao.findAll("CriteriaTest")).thenReturn(emptyRequestsEntity);

        RequestService requestService = new RequestService(daoManager, mapper);

        Assertions.assertThrows(ListRequestEmptyException.class, () -> requestService.getAllRequest("CriteriaTest"));
    }
}
