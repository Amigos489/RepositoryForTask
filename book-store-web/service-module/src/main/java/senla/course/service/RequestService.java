package senla.course.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import senla.course.dao.DaoManager;
import senla.course.dto.RequestDto;
import senla.course.entitys.BookEntity;
import senla.course.entitys.RequestEntity;
import senla.course.exception.BookNotFoundException;
import senla.course.exception.ListRequestEmptyException;
import senla.course.mapper.Mapper;

import java.util.List;

@Service
public class RequestService {

    @Value("${orderManagement.possibilityClosedRequest}")
    private boolean possibilityClosedRequest;
    private DaoManager daoManager;
    private Mapper<RequestDto, RequestEntity> mapper;

    public RequestService(DaoManager daoManager, Mapper<RequestDto, RequestEntity> mapper) {
        this.possibilityClosedRequest = possibilityClosedRequest;
        this.daoManager = daoManager;
        this.mapper = mapper;
    }

    public void createRequest(Integer bookId) {

        BookEntity book = daoManager.operationFindBookById(bookId);
        if (book == null) {
            throw new BookNotFoundException(bookId);
        }

        int activeRequestId = daoManager.operationFindActiveRequestOnBook(bookId);

        if (activeRequestId != -1) {
            daoManager.operationIncrementRequestById(activeRequestId);
            return;
        }

        RequestDto requestDto = new RequestDto(bookId);
        daoManager.operationCreateRequest(mapper.mappingDtoToEntity(requestDto));
    }

    public List<RequestDto> getAllRequest(String criteria) {
        List<RequestEntity> requests = daoManager.operationGetAllRequest(criteria);

        if (requests.isEmpty()) {
            throw new ListRequestEmptyException();
        }

        return mapper.mappingEntityListToListDto(requests);
    }
}
