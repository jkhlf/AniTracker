package br.com.animetracker.AniTracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.animetracker.AniTracker.model.FavoriteList;


@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {

    List<FavoriteList> findByUserId(Long userId);

    Optional<FavoriteList> findByIdAndUserId(Long id, Long userId);

    boolean existsByNameAndUserId(String name, Long userId);
}
