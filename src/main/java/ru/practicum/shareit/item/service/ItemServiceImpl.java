package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.PermissionException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemMapper itemMapper;
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public ItemDto get(Long id) {
        return itemMapper.toItemDto(itemStorage.get(id));
    }

    @Override
    public List<ItemDto> getAllByUserId(Long userId) {
        return itemStorage.getAllByOwnerId(userId)
                .stream()
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto add(ItemDto itemDto, Long userId) {
        userStorage.get(userId);
        Item newItem = itemStorage.add(itemMapper.toItem(itemDto, userId));
        return itemMapper.toItemDto(newItem);
    }

    @Override
    public ItemDto patch(ItemDto itemDto, Long itemId, Long userId) throws NotFoundException {
        Item storedItem = itemStorage.get(itemId);
        if (!storedItem.getOwner().equals(userId)) {
            String errorMessage = String.format("Владелец вещи не совпадает с пользователем userId = %d." +
                    " Изменить вещь может только владелец!", userId);
            throw new PermissionException(errorMessage);
        } else {
            itemDto.setId(itemId);
            Item patchedItem = itemStorage.patch(itemMapper.toItem(itemDto, userId));
            return itemMapper.toItemDto(patchedItem);
        }
    }

    @Override
    public boolean delete(Long id) {
        itemStorage.get(id);
        return itemStorage.delete(id);
    }

    @Override
    public Collection<ItemDto> search(String text) {
        if (text.isBlank()) {
            return new ArrayList<>();
        }
        return itemStorage.getAll()
                .stream()
                .filter(i -> i.getDescription().toLowerCase().contains(text.toLowerCase()) && i.getAvailable())
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
