package ru.practicum.shareit.booking.enums;

import ru.practicum.shareit.exception.IllegalRequestException;

public enum RequestStatus {
    ALL,
    CURRENT,
    PAST,
    FUTURE,
    WAITING,
    REJECTED;

    public static RequestStatus parseState(String line) {
        RequestStatus state;
        try {
            state = RequestStatus.valueOf(line);
        } catch (IllegalArgumentException e) {
            throw new IllegalRequestException("Unknown state: " + line);
        }
        return state;
    }
}