package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;


@Data
@Builder
@Jacksonized
public class CommentDto {
    private String text;
}
