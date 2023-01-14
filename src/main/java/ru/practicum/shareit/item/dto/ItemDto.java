package ru.practicum.shareit.item.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.shareit.request.model.ItemRequest;

@Data
@RequiredArgsConstructor
public class ItemDto {
    private Long id;
    private String name;
    private String description;
    private Boolean available;
    private Long owner;
    private ItemRequest request;
}
