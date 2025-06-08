package br.com.animetracker.AniTracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.animetracker.AniTracker.model.Anime;
import br.com.animetracker.AniTracker.model.FavoriteList;
import br.com.animetracker.AniTracker.security.CustomUserDetails;
import br.com.animetracker.AniTracker.service.FavoriteListService;
import br.com.animetracker.AniTracker.service.FavoriteService;
import br.com.animetracker.AniTracker.service.RecommendationService;



@Controller
public class HomeController {

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private FavoriteListService favoriteListService;

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() &&
                                !auth.getPrincipal().equals("anonymousUser");

        model.addAttribute("isAuthenticated", isAuthenticated);

        List<Anime> trendingAnime = recommendationService.getCurrentSeasonAnime(8);
        List<Anime> topRatedAnime = recommendationService.getTopRatedAnime(8);

        model.addAttribute("trendingAnime", trendingAnime);
        model.addAttribute("topRatedAnime", topRatedAnime);

        if (isAuthenticated) {
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            model.addAttribute("username", userDetails.getUsername());

            List<Anime> recentFavorites = favoriteService.getUserFavorites(userDetails.getUserId());
            if (recentFavorites.size() > 6) {
                recentFavorites = recentFavorites.subList(0, 6);
            }
            model.addAttribute("recentFavorites", recentFavorites);

            List<FavoriteList> recentLists = favoriteListService.getUserFavoriteLists(userDetails.getUserId());
            if (recentLists.size() > 4) {
                recentLists = recentLists.subList(0, 4);
            }
            model.addAttribute("recentLists", recentLists);
        }

        return "home";
    }
}
