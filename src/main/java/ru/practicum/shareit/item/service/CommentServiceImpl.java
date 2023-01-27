package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.enums.Status;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.repository.BookingRepository;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.exception.IllegalRequestException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl {
    private final BookingRepository bookingRepository;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Transactional
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

    private boolean checkCommentTruth(Long itemId, Long authorId) {
        List<Booking> allBookings = bookingRepository.findAllByBookerIdAndItemIdAndEndIsBefore(authorId,
                itemId, LocalDateTime.now());
        allBookings = allBookings.stream().filter(b -> b.getStatus().equals(Status.APPROVED))
                .collect(Collectors.toList());
        return allBookings.isEmpty();
    }
}
