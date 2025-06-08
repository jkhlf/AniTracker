package br.com.animetracker.AniTracker.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.animetracker.AniTracker.model.Anime;
import br.com.animetracker.AniTracker.model.User;
import br.com.animetracker.AniTracker.model.UserFavorite;
import br.com.animetracker.AniTracker.repository.AnimeRepository;
import br.com.animetracker.AniTracker.repository.UserFavoriteRepository;
import br.com.animetracker.AniTracker.repository.UserRepository;

@Service
public class FavoriteService {

    @Autowired
    private UserFavoriteRepository userFavoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimeRepository animeRepository;

    public List<Anime> getUserFavorites(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return List.of();
        }

        User user = userOpt.get();
        List<UserFavorite> favorites = userFavoriteRepository.findByUser(user);

        return favorites.stream()
                .map(UserFavorite::getAnime)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean addFavorite(Long userId, Long animeId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Anime> animeOpt = animeRepository.findById(animeId);

        if (userOpt.isEmpty() || animeOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        Anime anime = animeOpt.get();

        if (userFavoriteRepository.existsByUserAndAnime(user, anime)) {
            return true;
        }

        UserFavorite favorite = new UserFavorite(user, anime);
        userFavoriteRepository.save(favorite);
        return true;
    }

    @Transactional
    public boolean removeFavorite(Long userId, Long animeId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Anime> animeOpt = animeRepository.findById(animeId);

        if (userOpt.isEmpty() || animeOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        Anime anime = animeOpt.get();

        Optional<UserFavorite> favoriteOpt = userFavoriteRepository.findByUserAndAnime(user, anime);
        if (favoriteOpt.isEmpty()) {
            return false;
        }

        userFavoriteRepository.delete(favoriteOpt.get());
        return true;
    }

    public boolean isFavorite(Long userId, Long animeId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<Anime> animeOpt = animeRepository.findById(animeId);

        if (userOpt.isEmpty() || animeOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        Anime anime = animeOpt.get();

        return userFavoriteRepository.existsByUserAndAnime(user, anime);
    }
}
