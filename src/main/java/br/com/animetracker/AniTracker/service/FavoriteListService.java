package br.com.animetracker.AniTracker.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.animetracker.AniTracker.model.Anime;
import br.com.animetracker.AniTracker.model.FavoriteList;
import br.com.animetracker.AniTracker.model.User;
import br.com.animetracker.AniTracker.repository.AnimeRepository;
import br.com.animetracker.AniTracker.repository.FavoriteListRepository;
import br.com.animetracker.AniTracker.repository.UserRepository;


@Service
public class FavoriteListService {

    @Autowired
    private FavoriteListRepository favoriteListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnimeRepository animeRepository;

    public List<FavoriteList> getUserFavoriteLists(Long userId) {
        return favoriteListRepository.findByUserId(userId);
    }

    public Optional<FavoriteList> getFavoriteList(Long listId, Long userId) {
        return favoriteListRepository.findByIdAndUserId(listId, userId);
    }

    @Transactional
    public FavoriteList createFavoriteList(Long userId, String name, String description) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        if (favoriteListRepository.existsByNameAndUserId(name, userId)) {
            throw new IllegalArgumentException("A list with this name already exists");
        }

        User user = userOpt.get();
        FavoriteList favoriteList = new FavoriteList(name, description, user);

        user.addFavoriteList(favoriteList);
        return favoriteListRepository.save(favoriteList);
    }

    @Transactional
    public FavoriteList updateFavoriteList(Long listId, Long userId, String name, String description) {
        Optional<FavoriteList> listOpt = favoriteListRepository.findByIdAndUserId(listId, userId);
        if (!listOpt.isPresent()) {
            throw new IllegalArgumentException("List not found or not owned by user");
        }

        FavoriteList list = listOpt.get();

        if (!list.getName().equals(name) &&
            favoriteListRepository.existsByNameAndUserId(name, userId)) {
            throw new IllegalArgumentException("A list with this name already exists");
        }

        list.setName(name);
        list.setDescription(description);

        return favoriteListRepository.save(list);
    }

    @Transactional
    public boolean deleteFavoriteList(Long listId, Long userId) {
        Optional<FavoriteList> listOpt = favoriteListRepository.findByIdAndUserId(listId, userId);
        if (!listOpt.isPresent()) {
            return false;
        }

        favoriteListRepository.delete(listOpt.get());
        return true;
    }

    @Transactional
    public boolean addAnimeToList(Long listId, Long animeId, Long userId) {
        Optional<FavoriteList> listOpt = favoriteListRepository.findByIdAndUserId(listId, userId);
        Optional<Anime> animeOpt = animeRepository.findById(animeId);

        if (!listOpt.isPresent() || !animeOpt.isPresent()) {
            return false;
        }

        FavoriteList list = listOpt.get();
        Anime anime = animeOpt.get();

        list.addAnime(anime);
        favoriteListRepository.save(list);

        return true;
    }

    @Transactional
    public boolean removeAnimeFromList(Long listId, Long animeId, Long userId) {
        Optional<FavoriteList> listOpt = favoriteListRepository.findByIdAndUserId(listId, userId);
        Optional<Anime> animeOpt = animeRepository.findById(animeId);

        if (!listOpt.isPresent() || !animeOpt.isPresent()) {
            return false;
        }

        FavoriteList list = listOpt.get();
        Anime anime = animeOpt.get();

        list.removeAnime(anime);
        favoriteListRepository.save(list);

        return true;
    }

    public Set<Anime> getAnimesInList(Long listId, Long userId) {
        Optional<FavoriteList> listOpt = favoriteListRepository.findByIdAndUserId(listId, userId);

        if (!listOpt.isPresent()) {
            return new HashSet<>();
        }

        return listOpt.get().getAnimes();
    }
}
