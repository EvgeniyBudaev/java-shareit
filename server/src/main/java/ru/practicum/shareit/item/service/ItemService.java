package ru.practicum.shareit.item.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.shareit.item.dto.*;


public interface ItemService {
    /**
     * Добавление Вещи
     * @param itemDto Вещь
     * @param userId  идентификатор Пользователя владельца
     * @return объект ItemDtoResponse
     */
    ItemDtoResponse createItem(ItemDto itemDto, Long userId);

    /**
     * Обновление полей хранимой Вещи
     *
     * @param itemId  идентификатор Вещи
     * @param userId  идентификатор Пользователя владельца
     * @param itemDto Вещь
     * @return объект ItemDtoResponse
     */
    ItemDtoResponse updateItem(Long itemId, Long userId, ItemDtoUpdate itemDto);

    /**
     * Возвращает ItemDtoResponse Вещи Пользователя
     *
     * @param userId идентификатор Пользователя владельца Вещи
     * @param itemId идентификатор Вещи
     * @return ItemDtoResponse
     */
    ItemDtoResponse getItemByItemId(Long userId, Long itemId);

    /**
     * Возвращает коллекцию Вещей Пользователя
     *
     * @param pageable пагинация
     * @param userId   идентификатор Пользователя владельца Вещи
     * @return ItemListDto
     */
    ItemListDto getPersonalItems(Pageable pageable, Long userId);

    /**
     * Поиск Вещей Пользователя
     *
     * @param pageable пагинация
     * @param text ключевое слово для поиска
     * @return ItemListDto
     */
    ItemListDto getFoundItems(Pageable pageable, String text);

    /**
     * Добавление Комментария
     *
     * @param itemId     идентификатор Вещи
     * @param userId     идентификатор Пользователя владельца Вещи
     * @param commentDto Комментарий
     * @return CommentDtoResponse
     */
    CommentDtoResponse addComment(Long itemId, Long userId, CommentDto commentDto);
}
