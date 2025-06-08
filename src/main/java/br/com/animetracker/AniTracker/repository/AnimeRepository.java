package br.com.animetracker.AniTracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.animetracker.AniTracker.model.Anime;


@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
    Optional<Anime> findByMalId(Long malId);
    boolean existsByMalId(Long malId);
}
