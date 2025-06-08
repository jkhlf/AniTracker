package br.com.animetracker.AniTracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.animetracker.AniTracker.api.jikan.JikanAnimeData;
import br.com.animetracker.AniTracker.api.jikan.JikanApiResponse;
import br.com.animetracker.AniTracker.api.jikan.JikanGenreData;
import br.com.animetracker.AniTracker.model.Anime;
import br.com.animetracker.AniTracker.repository.AnimeRepository;



@Service
public class RecommendationService {

    @Autowired
    private JikanApiService jikanApiService;

    @Autowired
    private AnimeRepository animeRepository;

    
    private Anime createAnimeFromApiData(JikanAnimeData animeData) {
        String imageUrl = null;
        if (animeData.getImages() != null && animeData.getImages().getJpg() != null) {
            imageUrl = animeData.getImages().getJpg().getImageUrl();
        }

        String genres = "";
        if (animeData.getGenres() != null && !animeData.getGenres().isEmpty()) {
            genres = animeData.getGenres().stream()
                    .map(JikanGenreData::getName)
                    .collect(Collectors.joining(", "));
        }

        return new Anime(
                animeData.getMalId(),
                animeData.getTitle(),
                imageUrl,
                animeData.getSynopsis(),
                genres,
                animeData.getUrl()
        );
    }


    public List<Anime> searchAnimeByName(String searchQuery, int limit) {
        int searchLimit = (limit <= 0 || limit > 25) ? 10 : limit;

        try {
            JikanApiResponse<JikanAnimeData> response = jikanApiService.searchAnimeByName(searchQuery, searchLimit);

            if (response != null && response.getData() != null) {
                List<Anime> animeList = new ArrayList<>();
                  for (JikanAnimeData animeData : response.getData()) {
                    Anime anime = createAnimeFromApiData(animeData);

                    if (anime != null) {
                        Optional<Anime> existingAnime = animeRepository.findByMalId(anime.getMalId());

                        if (existingAnime.isPresent()) {
                            animeList.add(existingAnime.get());
                        } else {
                            Anime savedAnime = animeRepository.save(anime);
                            animeList.add(savedAnime);
                        }
                    }
                }

                return animeList;
            }
        } catch (Exception e) {
            System.err.println("Error searching anime by name: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    public List<Anime> getTopRatedAnime(int limit) {
        int searchLimit = (limit <= 0 || limit > 25) ? 10 : limit;

        try {
            JikanApiResponse<JikanAnimeData> response = jikanApiService.getTopAnime(searchLimit);

            if (response != null && response.getData() != null) {
                List<Anime> animeList = new ArrayList<>();
                for (JikanAnimeData animeData : response.getData()) {
                    Anime anime = createAnimeFromApiData(animeData);

                    if (anime != null) {
                        Optional<Anime> existingAnime = animeRepository.findByMalId(anime.getMalId());

                        if (existingAnime.isPresent()) {
                            animeList.add(existingAnime.get());
                        } else {
                            Anime savedAnime = animeRepository.save(anime);
                            animeList.add(savedAnime);
                        }
                    }
                }

                return animeList;
            }
        } catch (Exception e) {
            System.err.println("Error getting top rated anime: " + e.getMessage());
        }

        return new ArrayList<>();
    }

    public List<Anime> getCurrentSeasonAnime(int limit) {
        int searchLimit = (limit <= 0 || limit > 25) ? 10 : limit;

        try {
            JikanApiResponse<JikanAnimeData> response = jikanApiService.getCurrentSeasonAnime(searchLimit);

            if (response != null && response.getData() != null) {
                List<Anime> animeList = new ArrayList<>();
                for (JikanAnimeData animeData : response.getData()) {
                    Anime anime = createAnimeFromApiData(animeData);

                    if (anime != null) {
                        Optional<Anime> existingAnime = animeRepository.findByMalId(anime.getMalId());

                        if (existingAnime.isPresent()) {
                            animeList.add(existingAnime.get());
                        } else {
                            Anime savedAnime = animeRepository.save(anime);
                            animeList.add(savedAnime);
                        }
                    }
                }

                return animeList;
            }
        } catch (Exception e) {
            System.err.println("Error getting current season anime: " + e.getMessage());
        }

        return new ArrayList<>();
    }


    public List<Anime> getPopularAnime(int limit) {
        int searchLimit = (limit <= 0 || limit > 25) ? 10 : limit;

        try {
            JikanApiResponse<JikanAnimeData> response = jikanApiService.getPopularAnime(searchLimit);

            if (response != null && response.getData() != null) {
                List<Anime> animeList = new ArrayList<>();
                for (JikanAnimeData animeData : response.getData()) {
                    Anime anime = createAnimeFromApiData(animeData);

                    if (anime != null) {
                        Optional<Anime> existingAnime = animeRepository.findByMalId(anime.getMalId());

                        if (existingAnime.isPresent()) {
                            animeList.add(existingAnime.get());
                        } else {
                            Anime savedAnime = animeRepository.save(anime);
                            animeList.add(savedAnime);
                        }
                    }
                }

                return animeList;
            }
        } catch (Exception e) {
            System.err.println("Error getting popular anime: " + e.getMessage());
        }

        return new ArrayList<>();
    }
}
