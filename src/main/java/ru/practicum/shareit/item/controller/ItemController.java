package ru.practicum.shareit.item.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.logger.Logger;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/items")
@AllArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final CommentMapper commentMapper;
    private final String userIdHeader = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestHeader(userIdHeader) long userId, @Valid @RequestBody ItemDto itemDto) {
        Logger.logRequest(HttpMethod.POST, "/items", itemDto.toString());
        ItemDto itemCreated = itemService.addItem(userId, itemDto);
        return ResponseEntity.status(201).body(itemCreated);
    }

    @GetMapping("{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable long itemId, @RequestHeader(userIdHeader) long userId) {
        Logger.logRequest(HttpMethod.GET, "/items/" + itemId, "пусто");
        return ResponseEntity.ok().body(itemService.getItemById(itemId, userId));
    }

    @GetMapping     // Просмотр владельцем списка всех его вещей с указанием названия и описания для каждой
    public ResponseEntity<List<ItemDto>> getAllItems(@RequestHeader(userIdHeader) long userId) {
        Logger.logRequest(HttpMethod.GET, "/items", "пусто");
        return ResponseEntity.ok().body(itemService.getAllItems(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> searchItems(@RequestParam String text) {
        Logger.logRequest(HttpMethod.GET, "/items/search?text=" + text, "пусто");
        return ResponseEntity.ok().body(itemService.searchItems(text));
    }


    @PatchMapping("{itemId}")
    public ItemDto updateItem(@RequestHeader(userIdHeader) long userId, @PathVariable long itemId, @RequestBody ItemDto itemDto) {
        Logger.logRequest(HttpMethod.PATCH, "/items/" + itemId, itemDto.toString());
        Item item = itemMapper.convertFromDto(itemDto);
        return itemMapper.convertToDto(itemService.updateItem(userId, itemId, item));
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity<Void> removeItem(@RequestHeader(userIdHeader) long userId, @PathVariable long itemId) {
        Logger.logRequest(HttpMethod.DELETE, "/items/" + itemId, "пусто");
        itemService.removeItem(userId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto addComment(@RequestHeader(userIdHeader) long userId, @PathVariable long itemId,
                                 @RequestBody @Valid CommentDto commentDto) {
        Logger.logRequest(HttpMethod.POST, "/items/" + itemId + "/comment", commentDto.toString());
        Comment comment = commentMapper.convertFromDto(commentDto);
        return commentMapper.convertToDto(itemService.addComment(userId, itemId, comment));
    }
}