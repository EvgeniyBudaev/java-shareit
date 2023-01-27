package ru.practicum.shareit.booking.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingNestedDto;
import ru.practicum.shareit.booking.dto.BookingToInputDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ItemMapper.class})
public interface BookingMapper {
    Booking toBooking(BookingToInputDto bookingToInputDto, User booker, Item item);

    BookingDto toBookingDto(Booking booking);

    BookingNestedDto toBookingNestedDto(Booking booking);
}
