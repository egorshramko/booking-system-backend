package io.github.egorshramko.booking.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность фильм
 * Представляет собой справочную сущность о фильмах
 * Поля:
 * actual - актуальность
 * name - название
 * releaseYear - год выхода
 * ageLimit - возрастное ограничение
 * pathToPoster - путь до изображения постера
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @SequenceGenerator(name = "movie_id_gen", sequenceName = "movie_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    private String name;

    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    private AgeLimit ageLimit;

    //TODO: добавить поле, кем добавлен в справочник

    private String posterFilename;

}
