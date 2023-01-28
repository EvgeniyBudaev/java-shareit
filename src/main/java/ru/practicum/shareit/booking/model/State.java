package ru.practicum.shareit.booking.model;

public enum State {
    ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED;

    public static State convert(String source) {
        try {
            return State.valueOf(source);
        } catch (Exception e) {
            throw new IllegalArgumentException(String.format("Unknown state: %S", source));
        }
    }
}
