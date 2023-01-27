package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingRequest;
import ru.practicum.shareit.booking.service.BookingServiceImpl;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceImpl bookingServiceImpl;

    @PostMapping
    public ResponseEntity<BookingDto> create(@RequestBody @Valid BookingRequest request,
                                             @RequestHeader("X-Sharer-User-Id") Long requesterId) {
        return ResponseEntity.status(201).body(bookingServiceImpl.create(request, requesterId));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> setStatusByOwner(@PathVariable Long bookingId,
                                                       @RequestParam("approved") Boolean approved,
                                                       @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return ResponseEntity.ok().body(bookingServiceImpl.changeStatusByOwner(bookingId, approved, ownerId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> getById(@PathVariable Long bookingId,
                                              @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok().body(bookingServiceImpl.getById(bookingId, userId));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> getAll(@RequestParam(name = "state", required = false, defaultValue = "ALL") String state,
                                                   @RequestHeader("X-Sharer-User-Id") Long userId,
                                                   @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                   @RequestParam(value = "size", defaultValue = "20") @Positive Integer size) {
        return ResponseEntity.ok().body(bookingServiceImpl.getAllByUser(state, userId, from, size));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> getOwnerBookings(
            @RequestParam(name = "state", required = false, defaultValue = "ALL") String state,
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "20") @Positive Integer size) {
        return ResponseEntity.ok().body(bookingServiceImpl.getAllByUserOwner(state, userId, from, size));
    }
}
