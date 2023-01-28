package ru.practicum.shareit.booking.dto;

import lombok.Data;
import lombok.Builder;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private long id;
    private ItemDto item;
    private UserDto booker;
    private Status status;
    private LocalDateTime start;
    private LocalDateTime end;
}
