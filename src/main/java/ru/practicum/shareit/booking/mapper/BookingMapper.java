package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoBooker;
import ru.practicum.shareit.booking.dto.BookingDtoItem;
import ru.practicum.shareit.booking.dto.BookingDtoShort;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ItemMapper.class})
public interface BookingMapper {

    BookingDto toBookingDto(Booking booking);

    Booking toBooking(BookingDto bookingDto);

    @Mapping(target = "bookerId", source = "booker.id")
    BookingDtoShort toDtoShort(Booking booking);

    @Mapping(target = "bookerId", source = "booker.id")
    BookingDtoShort dtoToDtoShort(BookingDto bookingDto);

    List<BookingDtoShort> toDtoShortList(List<Booking> bookings);

    @Mapping(target = "item.id", source = "itemId")
    Booking toBookingFromRequest(BookingRequest request);

    List<BookingDto> toBookingDtoList(List<Booking> bookings);

    BookingDtoBooker toBookingDtoBooker(User booker);

    BookingDtoItem toBookingDtoItem(Item item);
}