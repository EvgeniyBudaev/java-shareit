package ru.practicum.shareit.item.storage;

import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemStorage {
    /**
     * Возвращает Вещь по идентификатору
     * @param id идентфикатор Вещи
     * @return объект Item
     */
    Item getItem(Long id);

    /**
     * Возвращает коллекцию всех Вещей
     * @return коллекцию объектов ItemDto
     */
    List<Item> getAllItems();

    /**
     * Реализует добавление Вещи в хранилище
     * @param item объект Вещи
     * @return добавленный объект Item в хранилище
     */
    Item createItem(Item item);

    /**
     * Реализует обновление полей хранимой Вещи
     * @param item объект Вещи с изменениями
     * @return обновленный объект Item
     */
    Item updateItem(Item item);

    /**
     * Реализует удаление Вещи из хранилища
     * @param id идентификатор удаляемой вещи
     * @return true в случае успешного удаления
     */
    void removeItem(Long id);
}
