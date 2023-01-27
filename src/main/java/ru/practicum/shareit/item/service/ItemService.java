package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.Collection;
import java.util.List;

public interface ItemService {
    /**
     * Реализует добавление Вещи в хранилище
     * @param itemDto DTO объект Вещи
     * @param ownerId идентификатор Пользователя владельца
     * @return DTO добавленного объекта Item в хранилище
     */
    ItemDto createItem(ItemDto itemDto, Long ownerId);

    /**
     * Реализует обновление полей хранимой Вещи
     * @param itemDto объект Вещи с изменениями
     * @param itemId идентификатор Вещи
     * @param userId идентификатор Пользователя
     * @return DTO обновленного объекта Item
     */
    ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId);

    /**
     * Возвращает коллекцию DTO Вещей Пользователя
     * @param userId идентификатор Пользователя владельца Вещи
     * @return коллекцию ItemDto
     */
    List<ItemDto> getAllItemsByUserId(Long userId);

    /**
     * Возвращает DTO Вещи по идентификатору
     * @param id идентификатор вещи
     * @return ItemDto
     */
    ItemDto getItem(Long id, Long requesterId);

    /**
     * Реализует удаление Вещи из хранилища
     * @param id идентификатор удаляемой вещи
     */
    void removeItem(Long id);

    /**
     * Реализует поиск Вещей в хранилище по ключевому слову
     * @param text ключевое слово для поиска
     * @return коллекцию DTO объектов Item
     */
    Collection<ItemDto> search(String text, Integer from, Integer size);

    /**
     * Возвращает коллекцию DTO Комментариев Пользователя
     * @param itemId идентификатор Вещи
     * @param commentDto объект Комментария с изменениями
     * @param authorId идентификатор Пользователя комментария
     * @return коллекцию ItemDto
     */
    CommentDto createComment(Long itemId, CommentDto commentDto, Long authorId);
}
