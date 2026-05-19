package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.AgeLimit;
import io.github.egorshramko.booking.model.domain.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.name = :name AND " +
            "m.releaseYear = :releaseYear AND m.ageLimit = :ageLimit ")
    Optional<Movie> findMovieByParams(
            @Param("name") String name,
            @Param("releaseYear") Integer releaseYear,
            @Param("ageLimit") AgeLimit ageLimit);

    Optional<Movie> findByIdAndActualIsTrue(Long id);
    Page<Movie> findAllByActualIsTrue(Pageable pageable);

}
