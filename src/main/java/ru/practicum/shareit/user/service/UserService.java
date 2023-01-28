package ru.practicum.shareit.user.service;

import ru.practicum.shareit.exception.DataExistException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {

    /**
     * Реализует добавление Пользователя в хранилище
     * @param userDto объект Пользователь
     * @return UserDto
     */
    UserDto addUser(UserDto userDto) throws DataExistException;

    /**
     * Реализует обновление полей Пользователя
     * @param userId идентификатор Пользователя
     * @param user объект Пользователь с изменениями
     * @return User
     */
    User updateUser(long userId, User user) throws DataExistException;

    /**
     * Возвращает Пользователя по идентификатору
     * @param userId идентификатор пользователя
     * @return User
     */
    User getUserById(long userId);

    /**
     * Возвращает коллекцию Пользователей
     * @return коллекцию User
     */
    List<User> getAllUsers() throws DataExistException;

    /**
     * Реализует удаление Пользователя из хранилища
     * @param userId идентификатор Пользователя
     */
    void removeUser(long userId);
}