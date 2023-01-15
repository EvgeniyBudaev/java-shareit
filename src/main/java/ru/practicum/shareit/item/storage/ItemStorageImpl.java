package ru.practicum.shareit.item.storage;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.NotValidException;
import ru.practicum.shareit.item.model.Item;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemStorageImpl implements ItemStorage {
    private Long increment = 0L;
    private final Map<Long, Item> items = new HashMap<>();

    @Override
    public Item get(Long id) {
        if (!items.containsKey(id)) {
            throw new NotFoundException("Вещь с идентификатором " +
                    id + " не зарегистрирована!");
        }
        return items.get(id);
    }

    @Override
    public Collection<Item> getAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Collection<Item> getAllByOwnerId(Long ownerId) {
        return items.values()
                .stream()
                .filter(item -> item.getOwner().getId() == ownerId)
                .collect(Collectors.toList());
    }

    @Override
    public Item add(Item item) {
        if (item.getAvailable() == null) {
            throw new NotValidException("поле Доступность не может быть пустым!");
        }
        if (item.getName() == null || item.getName().isEmpty()) {
            throw new NotValidException("поле Название не может быть пустым!");
        }
        if (item.getDescription() == null || item.getDescription().isEmpty()) {
            throw new NotValidException("поле Описание не может быть пустым!");
        }
        item.setId(++increment);
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public Item patch(Item item) {
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public boolean delete(Long id) {
        items.remove(id);
        return !items.containsKey(id);
    }
}
