package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.booking.dto.BookingInputDto;
import ru.practicum.shareit.booking.model.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDto convertToDto(Booking booking);

    @Mapping(target = "bookerId", source = "booker.id")
    BookingDtoShort convertToDtoShort(Booking booking);

    Booking convertFromDto(BookingInputDto bookingInputDto);
}
