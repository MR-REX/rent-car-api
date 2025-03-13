package ru.mrrex.rentcar.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.mrrex.rentcar.dto.responses.BrandResponseDto;
import ru.mrrex.rentcar.models.Brand;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BrandMapper {

    @Mapping(source = "publicId", target = "id")
    BrandResponseDto toBrandResponseDto(Brand brand);
    
    List<BrandResponseDto> toBrandResponseDtoList(List<Brand> brands);
}
