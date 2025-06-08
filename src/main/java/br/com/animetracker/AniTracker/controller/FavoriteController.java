package br.com.animetracker.AniTracker.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.animetracker.AniTracker.model.Anime;
import br.com.animetracker.AniTracker.security.CustomUserDetails;
import br.com.animetracker.AniTracker.service.FavoriteService;


@Controller
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/favorites")
    public String favorites(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        Long userId = userDetails.getUserId();
        List<Anime> favorites = favoriteService.getUserFavorites(userId);

        model.addAttribute("animeList", favorites);
        model.addAttribute("username", userDetails.getUsername());

        Map<Long, Boolean> favoritesMap = new HashMap<>();
        for (Anime anime : favorites) {
            favoritesMap.put(anime.getId(), true);
        }
        model.addAttribute("favorites", favoritesMap);

        return "favorites";
    }

    @PostMapping("/api/favorites/add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addFavorite(
            @RequestParam("animeId") Long animeId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Map<String, Object> response = new HashMap<>();

        if (userDetails == null) {
            response.put("success", false);
            response.put("message", "Not authenticated");
            return ResponseEntity.status(401).body(response);
        }

        Long userId = userDetails.getUserId();
        boolean success = favoriteService.addFavorite(userId, animeId);

        response.put("success", success);
        response.put("message", success ? "Added to favorites" : "Failed to add to favorites");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/favorites/remove")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeFavorite(
            @RequestParam("animeId") Long animeId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Map<String, Object> response = new HashMap<>();

        if (userDetails == null) {
            response.put("success", false);
            response.put("message", "Not authenticated");
            return ResponseEntity.status(401).body(response);
        }

        Long userId = userDetails.getUserId();
        boolean success = favoriteService.removeFavorite(userId, animeId);

        response.put("success", success);
        response.put("message", success ? "Removed from favorites" : "Failed to remove from favorites");

        return ResponseEntity.ok(response);
    }
}
