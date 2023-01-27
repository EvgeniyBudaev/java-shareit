package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.exception.IllegalRequestException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ItemDto createItem(ItemDto itemDto, Long userId) {
        User owner = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("Owner with id: " + userId + " not found"));
        Item newItem = itemMapper.toItem(itemDto);
        newItem.setOwner(owner);
        Item createdItem = itemRepository.save(newItem);
        log.info("Item with id: " + newItem.getId() + " created");
        return itemMapper.toItemDto(createdItem);
    }

    @Transactional
    @Override
    public ItemDto updateItem(ItemDto itemDto, Long itemId, Long userId) {
        Item oldItem = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException("Item with id: " + itemId + " not found"));
        if (!Objects.equals(oldItem.getOwner().getId(), userId))
            throw new NotFoundException("User not owner");
        Item item  = itemMapper.toItem(itemDto);
        Item changedItem = itemOwnerNameDescAvailValidator(item, oldItem);
        log.info("Item with id: " + changedItem.getId() + " updated");
        return itemMapper.toItemDto(changedItem);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ItemDto> getAllItemsByUserId(Long userId) {
        List<ItemDto> itemListDto = itemRepository.findAll()
                .stream()
                .filter(i -> Objects.equals(i.getOwner().getId(), userId))
                .map(itemMapper::toItemDto)
                .collect(Collectors.toList());
        for (ItemDto itemDto : itemListDto) {
            addBooking(itemDto);
            addComment(itemDto);
        }
        return itemListDto;
    }

    @Transactional(readOnly = true)
    @Override
    public ItemDto getItem(Long id, Long requesterId) {
        ItemDto itemDto;
        Item item = itemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Item with id: " + id + " not found"));
        itemDto = itemMapper.toItemDto(item);
        addComment(itemDto);
        if (!item.getOwner().getId().equals(requesterId)) {
            return itemDto;
        }
        return addBooking(itemDto);
    }

    @Transactional
    @Override
    public void removeItem(Long id) {
        itemRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Item with id: " + id + " not found"));
        itemRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<ItemDto> search(String text, Integer from, Integer size) {
        if (text.isBlank()) {
            return Collections.emptyList();
        } else {
            log.debug("Searching: {}", text);
            Pageable page = PageRequest.of(from / size, size);
            return itemMapper.toDtoList(itemRepository.search(text, page).getContent());
        }
    }

    @Transactional
    @Override
    public CommentDto createComment(Long itemId, CommentDto commentDto, Long authorId) {
        User author = userRepository.findById(authorId).orElseThrow(() ->
                new NotFoundException("Author not found."));
        Item item = itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException("Item not found."));
        commentDto.setItemId(itemId);
        createCommentValidator(commentDto, authorId);
        commentDto.setAuthorName(author.getName());
        Comment comment = commentMapper.toComment(commentDto);
        comment.setAuthor(author);
        comment.setItem(item);
        comment.setCreated(LocalDateTime.now());
        return commentMapper.toCommentDto(commentRepository.save(comment));
    }

    private void createCommentValidator(CommentDto commentDto, Long authorId) {
        if (checkCommentTruth(commentDto.getItemId(), authorId)) {
            throw new IllegalRequestException("Comment not truly.");
        }
    }

    private Item itemOwnerNameDescAvailValidator(Item item, Item oldItem) {
        if (item.getName() != null) {
            oldItem.setName(item.getName());
        }
        if (item.getDescription() != null) {
            oldItem.setDescription(item.getDescription());
        }
        if (item.getAvailable() != null) {
            oldItem.setAvailable(item.getAvailable());
        }
        return oldItem;
    }

    private ItemDto addBooking(ItemDto item) {
        LocalDateTime moment = LocalDateTime.now();
        Optional<Booking> bookingBefore = bookingRepository.findByItemIdAndEndIsBefore(item.getId(), moment);
        Optional<Booking> bookingAfter = bookingRepository.findByItemIdAndStartIsAfter(item.getId(), moment);
        bookingBefore.ifPresent(booking -> item.setLastBooking(bookingMapper.toDtoShort(booking)));
        bookingAfter.ifPresent(booking -> item.setNextBooking(bookingMapper.toDtoShort(booking)));
        return item;
    }

    private void addComment(ItemDto item) {
        List<Comment> comments = commentRepository.findAllByItemIdOrderByCreatedDesc(item.getId());
        item.setComments(commentMapper.toDtoList(comments));
    }

    private boolean checkCommentTruth(Long itemId, Long authorId) {
        List<Booking> allBookings = bookingRepository.findAllByBookerIdAndItemIdAndEndIsBefore(authorId,
                itemId, LocalDateTime.now());
        allBookings = allBookings.stream().filter(b -> b.getStatus().equals(Status.APPROVED))
                .collect(Collectors.toList());
        return allBookings.isEmpty();
    }

}
