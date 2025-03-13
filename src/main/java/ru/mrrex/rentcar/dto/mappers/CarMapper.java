package ru.mrrex.rentcar.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.mrrex.rentcar.dto.responses.CarResponseDto;
import ru.mrrex.rentcar.models.Car;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    @Mapping(source = "publicId", target = "id")
    CarResponseDto toCarResponseDto(Car car);
    
    List<CarResponseDto> toCarResponseDtoList(List<Car> cars);
}
