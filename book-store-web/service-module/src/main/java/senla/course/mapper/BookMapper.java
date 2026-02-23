package senla.course.mapper;

import org.springframework.stereotype.Component;
import senla.course.dto.BookDto;
import senla.course.entitys.BookEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper extends Mapper<BookDto, BookEntity> {

    @Override
    public BookDto mappingEntityToDto(BookEntity entity) {
        if (entity == null) {
            return null;
        }
        int bookId = entity.getBookId();
        String nameBook = entity.getNameBook();
        String authorBook = entity.getAuthorBook();
        LocalDate datePublication = entity.getDatePublication();
        LocalDate dateAddWarehouse = entity.getDateAddWarehouse();
        BigDecimal price = entity.getPrice();
        boolean availability = entity.isAvailability();
        return new BookDto(bookId, nameBook, authorBook, datePublication, dateAddWarehouse, price, availability);
    }

    @Override
    public BookEntity mappingDtoToEntity(BookDto dto) {
        return null;
    }

    @Override
    public List<BookDto> mappingEntityListToListDto(List<BookEntity> entitys) {
        List<BookDto> booksDto = new ArrayList<BookDto>();
        for (BookEntity entity : entitys) {
            booksDto.add(mappingEntityToDto(entity));
        }
        return booksDto;
    }
}
