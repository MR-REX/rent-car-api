package ru.mrrex.rentcar.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.mrrex.rentcar.dto.responses.CategoryResponse;
import ru.mrrex.rentcar.models.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    @Mapping(source = "publicId", target = "id")
    CategoryResponse toCategoryResponse(Category category);
    
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);
}
