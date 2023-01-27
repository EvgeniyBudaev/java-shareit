package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import ru.practicum.shareit.booking.dto.BookingNestedDto;
import ru.practicum.shareit.item.dto.ItemAllFieldsDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemAllFieldsDto toItemDto(Item item);

    ItemAllFieldsDto toItemDto(Item item, Collection<Comment> comments);

    ItemAllFieldsDto toItemDto(Item from,
                               Collection<Comment> comments,
                               BookingNestedDto lastBooking,
                               BookingNestedDto nextBooking);

    Item toItem(ItemAllFieldsDto itemAllFieldsDto, User owner);
}
