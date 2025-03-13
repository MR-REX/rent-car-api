package ru.mrrex.rentcar.dto.mappers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.mrrex.rentcar.dto.responses.DetailedCarResponseDto;
import ru.mrrex.rentcar.models.Car;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BrandMapper.class, CategoryMapper.class})
public interface DetailedCarMapper {

    @Mapping(source = "publicId", target = "id")
    @Mapping(source = "rentalPricePerDay", target = "rentalPricePerDay")
    @Mapping(target = "colors", ignore = true)
    DetailedCarResponseDto toDetailedCarResponseDto(Car car);

    List<DetailedCarResponseDto> toDetailedCarResponseDtoList(List<Car> cars);

    @AfterMapping
    default void addRandomColors(@MappingTarget final DetailedCarResponseDto dto) {
        Random random = new Random();

        int colorsCount = random.nextInt(5);
        Set<String> uniqueColors = new HashSet<>();

        String[] possibleColors =
                new String[] {"black", "white", "gray", "red", "green", "blue", "yellow", "orange"};

        for (int i = 0; i < colorsCount; i++)
            uniqueColors.add(possibleColors[random.nextInt(possibleColors.length)]);

        dto.setColors(new ArrayList<>(uniqueColors));
    }
}
