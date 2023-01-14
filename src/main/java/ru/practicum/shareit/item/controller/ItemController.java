package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private ItemService itemService;
    private final String userIdHeader = "X-Sharer-User-Id";

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{itemId}")
    public ItemDto getId(@PathVariable Long itemId) {
        return itemService.get(itemId);
    }

    @GetMapping
    public Collection<ItemDto> getAll(@RequestHeader(userIdHeader) Long userId) {
        return itemService.getAllByUserId(userId);
    }

    @PostMapping
    public ItemDto addItem(@RequestHeader(userIdHeader) Long userId,
                           @Valid @RequestBody ItemDto itemDto) {
        return itemService.add(itemDto, userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto patchItem(@RequestHeader(userIdHeader) Long userId,
                             @PathVariable Long itemId,
                             @Valid @RequestBody ItemDto itemDto) {
        return itemService.patch(itemDto, itemId, userId);
    }

    @GetMapping("/search")
    public Collection<ItemDto> search(@RequestHeader(userIdHeader) Long userId,
                                      @RequestParam String text) {
        return itemService.search(text, userId);
    }
}
