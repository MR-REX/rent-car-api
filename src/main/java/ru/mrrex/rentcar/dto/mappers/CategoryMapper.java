package ru.mrrex.rentcar.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.mrrex.rentcar.dto.responses.CategoryResponseDto;
import ru.mrrex.rentcar.models.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(source = "publicId", target = "id")
    CategoryResponseDto toCategoryResponse(Category category);
    
    List<CategoryResponseDto> toCategoryResponseList(List<Category> categories);
}
