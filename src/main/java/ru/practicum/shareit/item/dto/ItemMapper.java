package ru.practicum.shareit.item.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.model.Item;

@Component
@RequiredArgsConstructor
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : 0L
        );
    }

    public static Item toItem(ItemDto itemDto, Long id) {
        return new Item(
                itemDto.getId(),
                itemDto.getName() == null ? "" : itemDto.getName(),
                itemDto.getDescription() == null ? "" : itemDto.getDescription(),
                itemDto.getAvailable(),
                id
        );
    }
}
