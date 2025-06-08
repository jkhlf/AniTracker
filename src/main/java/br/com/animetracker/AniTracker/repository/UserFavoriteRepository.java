package br.com.animetracker.AniTracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.animetracker.AniTracker.model.Anime;
import br.com.animetracker.AniTracker.model.User;
import br.com.animetracker.AniTracker.model.UserFavorite;
import br.com.animetracker.AniTracker.model.UserFavoriteId;



@Repository
public interface UserFavoriteRepository extends JpaRepository<UserFavorite, UserFavoriteId> {
    List<UserFavorite> findByUser(User user);
    Optional<UserFavorite> findByUserAndAnime(User user, Anime anime);
    boolean existsByUserAndAnime(User user, Anime anime);
    void deleteByUserAndAnime(User user, Anime anime);
}
