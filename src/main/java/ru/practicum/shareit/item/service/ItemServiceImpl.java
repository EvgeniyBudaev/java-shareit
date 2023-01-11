package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.PermissionException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.storage.ItemStorage;
import ru.practicum.shareit.user.storage.UserStorage;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemStorage itemStorage;
    private final UserStorage userStorage;

    @Override
    public ItemDto get(Long id) {
        return ItemMapper.toItemDto(itemStorage.get(id));
    }

    @Override
    public Collection<ItemDto> getAllByUserId(Long userId) {
        return itemStorage.getAllByOwnerId(userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto add(ItemDto itemDto, Long userId) {
        userStorage.get(userId);
        Item newItem = itemStorage.add(ItemMapper.toItem(itemDto, userId));
        return ItemMapper.toItemDto(newItem);
    }

    @Override
    public ItemDto patch(ItemDto itemDto, Long itemId, Long userId) throws NotFoundException {
        Item storedItem = itemStorage.get(itemId);
        if (!Objects.equals(storedItem.getOwner(), userId)) {
            throw new PermissionException("Владелец вещи не совпадает с пользователем " + userId + ". " +
                    "Изменить вещь может только владелец!");
        } else {

            itemDto.setId(itemId);
            Item patchedItem = itemStorage.patch(ItemMapper.toItem(itemDto, userId));
            return ItemMapper.toItemDto(patchedItem);
        }
    }

    @Override
    public boolean delete(Long id) {
        itemStorage.get(id);
        return itemStorage.delete(id);
    }

    @Override
    public Collection<ItemDto> search(String text, Long userId) {
        return itemStorage.search(text, userId)
                .stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
}
