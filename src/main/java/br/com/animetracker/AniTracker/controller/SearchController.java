package br.com.animetracker.AniTracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.animetracker.AniTracker.model.Anime;
import br.com.animetracker.AniTracker.model.FavoriteList;
import br.com.animetracker.AniTracker.security.CustomUserDetails;
import br.com.animetracker.AniTracker.service.FavoriteListService;
import br.com.animetracker.AniTracker.service.FavoriteService;
import br.com.animetracker.AniTracker.service.RecommendationService;


@Controller
public class SearchController {

    @Autowired
    private RecommendationService recommendationService;
    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteListService favoriteListService;    @PostMapping("/search-results")
    public String searchResults(@RequestParam("sentiment") String searchQuery,
                               @RequestParam(value = "limit", defaultValue = "10") int limit,
                               Model model, Authentication authentication) {

        List<Anime> searchResults = recommendationService.searchAnimeByName(searchQuery, limit);

        model.addAttribute("animeList", searchResults);

        Map<Long, Boolean> favoritesMap = new HashMap<>();
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated() &&
                               !authentication.getPrincipal().equals("anonymousUser");

        model.addAttribute("isAuthenticated", isAuthenticated);        if (isAuthenticated) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            Long userId = userDetails.getUserId();            for (Anime anime : searchResults) {
                favoritesMap.put(anime.getId(), favoriteService.isFavorite(userId, anime.getId()));
            }

            model.addAttribute("username", userDetails.getUsername());

            List<FavoriteList> userLists = favoriteListService.getUserFavoriteLists(userId);
            model.addAttribute("userLists", userLists);
        }

        model.addAttribute("favorites", favoritesMap);

        return "results";
    }
}
