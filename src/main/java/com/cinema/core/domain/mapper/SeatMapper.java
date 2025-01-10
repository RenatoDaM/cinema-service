package com.cinema.core.domain.mapper;

import com.cinema.core.domain.entity.Seat;
import com.cinema.entrypoints.api.dto.response.SeatResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeatMapper {

    SeatMapper INSTANCE = Mappers.getMapper(SeatMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "seatNumber", source = "seatNumber")
    @Mapping(target = "available", source = "available")
    @Mapping(target = "type", source = "type")
    SeatResponse toDto(Seat entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "seatNumber", source = "seatNumber")
    @Mapping(target = "available", source = "available")
    @Mapping(target = "type", source = "type")
    Seat toEntity(SeatResponse dto);
}
