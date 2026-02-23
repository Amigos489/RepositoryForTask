package senla.course.mapper;

import java.util.List;

public abstract class Mapper <D, E> {

    public abstract D mappingEntityToDto(E entity);
    public abstract E mappingDtoToEntity(D dto);
    public abstract List<D> mappingEntityListToListDto(List<E> entitys);
}
