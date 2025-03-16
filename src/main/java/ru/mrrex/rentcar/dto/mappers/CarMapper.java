package ru.mrrex.rentcar.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.mrrex.rentcar.dto.responses.CarResponse;
import ru.mrrex.rentcar.models.Car;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CarMapper {

    @Mapping(source = "publicId", target = "id")
    CarResponse toCarResponse(Car car);
    
    List<CarResponse> toCarResponseList(List<Car> cars);
}
