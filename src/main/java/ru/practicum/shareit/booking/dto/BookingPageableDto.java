package ru.practicum.shareit.booking.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class BookingPageableDto extends PageRequest {
    @Min(0)
    private final Integer from;

    @Min(1) @Max(20)
    private final Integer size;

    protected BookingPageableDto(Integer from, Integer size) {
        super(from/size, size, Sort.unsorted());
        this.from = from;
        this.size = size;
    }
}
