package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping()
    public ResponseEntity<ItemDto> createItem(@Valid @RequestBody ItemDto itemDto,
                                              @RequestHeader(userIdHeader) Long userId) {
        return ResponseEntity.status(201).body(itemService.createItem(itemDto, userId));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@RequestBody ItemDto itemDto, @PathVariable Long itemId,
                                              @RequestHeader(userIdHeader) Long userId) {
        return ResponseEntity.ok().body(itemService.updateItem(itemDto, itemId, userId));
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Collection<ItemDto>> search(@RequestParam("text") String text,
                                                @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(value = "size", defaultValue = "20") @Positive Integer size) {
        return ResponseEntity.ok().body(itemService.search(text, from, size));
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId) {
        itemService.removeItem(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable Long itemId,
                                           @RequestHeader(userIdHeader) Long requesterId) {
        return ResponseEntity.ok().body(itemService.getItem(itemId, requesterId));
    }

    @GetMapping()
    public ResponseEntity<List<ItemDto>> findAll(@RequestHeader(userIdHeader) Long userId) {
        return ResponseEntity.ok().body(itemService.getAllItemsByUserId(userId));
    }

    @PostMapping("/{itemId}/comment")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommentDto> createComment(@PathVariable Long itemId,
                                                    @RequestBody @Valid CommentDto commentDto,
                                                    @RequestHeader(userIdHeader) Long authorId) {
        return ResponseEntity.ok().body(itemService.createComment(itemId, commentDto, authorId));
    }
}
