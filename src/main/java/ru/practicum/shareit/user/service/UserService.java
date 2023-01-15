package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.Collection;

public interface UserService {
    /**
     * Возвращает DTO Пользователя по идентификатору
     * @param id идентификатор пользователя
     * @return UserDto
     */
    UserDto get(Long id);

    /**
     * Возвращает коллекцию DTO Пользователей
     * @return коллекцию UserDto
     */
    Collection<UserDto> getAll();

    /**
     * Реализует добавление Пользователя в хранилище
     * @param userDto DTO объект Пользователь
     * @return DTO добавленного объекта UserDto в хранилище
     */
    UserDto add(UserDto userDto);

    /**
     * Реализует обновление полей Пользователя
     * @param userDto объект Пользователь с изменениями
     * @param id идентификатор Пользователя
     * @return DTO обновленного объекта UserDto
     */
    UserDto patch(UserDto userDto, Long id);

    /**
     * Реализует удаление Пользователя из хранилища
     * @param id идентификатор Пользователя
     * @return true в случае успешного удаления
     */
    Boolean delete(Long id);
}
