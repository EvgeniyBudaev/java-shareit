package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
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
        Item newItem = itemMapper.toItem(itemDto);
        userStorage.get(userId);
        Item createdItem = itemStorage.add(newItem);
        return itemMapper.toItemDto(createdItem);
    }

    @Override
    public ItemDto patch(ItemDto itemDto, Long itemId, Long userId) {
        Item item  = itemMapper.toItem(itemDto);
        userIdValidator(userId);
        Item oldItem = itemStorage.get(itemId);
        itemOwnerNameDescAvailValidator(item, oldItem, userId);
        Item changedItem = itemStorage.patch(oldItem);
        return itemMapper.toItemDto(changedItem);
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

    private void itemOwnerNameDescAvailValidator(Item item, Item oldItem, long userId) {
        if (oldItem.getOwner().getId() != userId) {
            throw new NotFoundException("User is not owner of this item!");
        }
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }
    }

    private void userIdValidator(Long userId) {
        if (!itemStorage.getAll().contains(itemStorage.get(userId))) {
            throw new NotFoundException(String.format("user with id = %d not found.", userId));
        }
    }
}