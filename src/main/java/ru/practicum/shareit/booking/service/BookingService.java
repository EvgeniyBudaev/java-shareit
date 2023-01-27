package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.model.AccessLevel;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.State;

import java.util.List;

public interface BookingService {
    /**
     * Реализует бронирование
     * @param bookerId идентификатор
     * @param booking Бронь
     * @return Booking
     */
    Booking addBooking(long bookerId, Booking booking);

    /**
     * Реализует подтверждение или отклонение запроса на бронирование
     * @param ownerId идентификатор Владельца
     * @param bookingId идентификатор Брони
     * @param approved статус бронирования
     * @param accessLevel уровень доступа
     * @return Booking
     */
    Booking approveOrRejectBooking(long ownerId, long bookingId, boolean approved, AccessLevel accessLevel);

    /**
     * Возвращает Бронирование по идентификатору
     * @param bookingId идентификатор Брони
     * @param userId идентификатор пользователя
     * @param accessLevel уровень доступа
     * @return Booking
     */
    Booking getBookingById(long bookingId, long userId, AccessLevel accessLevel);

    /**
     * Возвращает коллекцию Booking для текущего Пользователя
     * @param state состояние
     * @param bookerId идентификатор
     * @return коллекцию Booking
     */
    List<Booking> getBookingsOfCurrentUser(State state, long bookerId);

    /**
     * Возвращает коллекцию Booking для текущего Владельца
     * @param state состояние
     * @param ownerId идентификатор Владельца
     * @return коллекцию Booking
     */
    List<Booking> getBookingsOfOwner(State state, long ownerId);
}
