package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemStorage {
    /**
     * Возвращает Вещь по идентификатору
     * @param id идентфикатор Вещи
     * @return объект Item
     */
    Item get(Long id);

    /**
     * Возвращает коллекцию DTO всех Вещей
     * @return коллекцию ItemDto
     */
    Collection<Item> getAll();

    /**
     * Возвращает коллекцию Вещей Пользователя
     * @param ownerId идентификатор Пользователя - владельца Вещи
     * @return коллекцию объектов Item
     */
    Collection<Item> getAllByOwnerId(Long ownerId);

    /**
     * Реализует добавление Вещи в хранилище
     * @param item объект Вещи
     * @return добавленный объект Item в хранилище
     */
    Item add(Item item);

    /**
     * Реализует обновление полей хранимой Вещи
     * @param item объект Вещи с изменениями
     * @return обновленный объект Item
     */
    Item patch(Item item);

    /**
     * Реализует удаление Вещи из хранилища
     * @param id идентификатор удаляемой вещи
     * @return true в случае успешного удаления
     */
    boolean delete(Long id);
}
