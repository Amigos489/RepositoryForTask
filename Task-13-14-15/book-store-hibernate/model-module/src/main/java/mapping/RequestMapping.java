package mapping;

import entitys.OrderEntity;
import entitys.RequestEntity;
import model.Order;
import model.Request;

public class RequestMapping extends AbstractMapper<Request, RequestEntity> {

    @Override
    public Request entityToModelMapping(RequestEntity requestEntity) {
        int requestId = requestEntity.getRequestId();
        int bookId = requestEntity.getBook().getBookId();
        String nameBook = requestEntity.getNameBook();
        int countRequest = requestEntity.getCountRequest();
        boolean isClosed = requestEntity.getIsClosed();
        return new Request(requestId, bookId, nameBook, countRequest, isClosed);
    }

    @Override
    public RequestEntity modelToEntityMapping(Request model) {
        Integer bookId = model.getBookId();
        Integer countRequest = model.getCountRequest();
        Boolean isClosed = model.getIsClosed();
        return new RequestEntity(bookId, countRequest, isClosed);
    }
}
