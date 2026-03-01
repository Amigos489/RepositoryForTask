package senla.course.mapper;

import org.springframework.stereotype.Component;
import senla.course.dto.BookDto;
import senla.course.dto.RequestDto;
import senla.course.entitys.BookEntity;
import senla.course.entitys.RequestEntity;

import java.util.ArrayList;
import java.util.List;

@Component
public class RequestMapper extends Mapper<RequestDto, RequestEntity> {

    @Override
    public RequestDto mappingEntityToDto(RequestEntity entity) {
        int requestId = entity.getRequestId();
        int bookId = entity.getBook().getBookId();
        String nameBook = entity.getNameBook();
        int countRequest = entity.getCountRequest();
        boolean isClosed = entity.getIsClosed();
        return new RequestDto(requestId, bookId, nameBook, countRequest, isClosed);
    }

    @Override
    public RequestEntity mappingDtoToEntity(RequestDto dto) {
        Integer bookId = dto.getBookId();
        Integer countRequest = dto.getCountRequest();
        Boolean isClosed = dto.getIsClosed();
        return new RequestEntity(bookId, countRequest, isClosed);
    }

    @Override
    public List<RequestDto> mappingEntityListToListDto(List<RequestEntity> entitys) {
        List<RequestDto> requestsDto = new ArrayList<RequestDto>();
        for (RequestEntity entity : entitys) {
            requestsDto.add(mappingEntityToDto(entity));
        }
        return requestsDto;
    }
}
