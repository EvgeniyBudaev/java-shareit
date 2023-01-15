package ru.practicum.shareit.user.storage;

import ru.practicum.shareit.user.model.User;

import java.util.Collection;

public interface UserStorage {
    /**
     * Возвращает Пользователя по идентификатору
     * @param id идентфикатор Пользователя
     * @return объект User
     */
    User get(Long id);

    /**
     * Возвращает коллекцию Пользователей
     * @return коллекция User
     */
    Collection<User> getAll();

    /**
     * Реализует добавление Пользователя в хранилище
     * @param user объект Пользователь
     * @return добавленный объект User в хранилище
     */
    User add(User user);

    /**
     * Реализует обновление полей Пользователя
     * @param user объект Пользователь с изменениями
     * @return обновленный объект User
     */
    User patch(User user);

    /**
     * Реализует удаление Пользователя из хранилища
     * @param id идентификатор удаляемого Пользователя
     * @return true в случае успешного удаления
     */
    Boolean delete(Long id);
}
