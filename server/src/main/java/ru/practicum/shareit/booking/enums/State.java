package ru.practicum.shareit.booking.enums;

import java.util.Arrays;

public enum State {
    ALL, CURRENT, PAST, FUTURE, WAITING, REJECTED, UNSUPPORTED_STATUS;

    public static State fromValue(String value) {
        return Arrays.stream(values()).filter(state -> state.toString().equals(value)).findFirst().orElse(UNSUPPORTED_STATUS);
    }
}
