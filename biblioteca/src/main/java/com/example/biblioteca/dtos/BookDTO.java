package com.example.biblioteca.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String title;
    private Long authorId;
    private Long publisherId;
    private List<Long> categoryIds; // ВСЕГДА пустой массив
}
