package br.com.animetracker.AniTracker.service;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.animetracker.AniTracker.api.jikan.JikanAnimeData;
import br.com.animetracker.AniTracker.api.jikan.JikanApiResponse;
import reactor.util.retry.Retry;

@Service
public class JikanApiService {

    private static final Logger logger = LoggerFactory.getLogger(JikanApiService.class);

    @Autowired
    private WebClient webClient;

    public JikanApiResponse<JikanAnimeData> searchAnimeByName(String query, int limit) {
        try {
            return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/anime")
                        .queryParam("q", query)
                        .queryParam("limit", limit)
                        .queryParam("order_by", "score")
                        .queryParam("sort", "desc")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<JikanApiResponse<JikanAnimeData>>() {})
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> !throwable.getMessage().contains("404"))
                        .doBeforeRetry(retrySignal -> logger.info("Retrying API call after error: {}", retrySignal.failure().getMessage())))
                .doOnError(error -> logger.error("Error searching anime by name: {}", error.getMessage()))
                .onErrorReturn(new JikanApiResponse<>())
                .block(Duration.ofSeconds(20));        } catch (Exception e) {
            logger.error("Exception during anime search by name: {}", e.getMessage());
            return new JikanApiResponse<>();
        }
    }

    public JikanApiResponse<JikanAnimeData> getTopAnime(int limit) {
        try {
            return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/top/anime")
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<JikanApiResponse<JikanAnimeData>>() {})
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> !throwable.getMessage().contains("404"))
                        .doBeforeRetry(retrySignal -> logger.info("Retrying API call after error: {}", retrySignal.failure().getMessage())))
                .doOnError(error -> logger.error("Error getting top anime: {}", error.getMessage()))
                .onErrorReturn(new JikanApiResponse<>())
                .block(Duration.ofSeconds(20));
        } catch (Exception e) {
            logger.error("Exception during top anime fetch: {}", e.getMessage());
            return new JikanApiResponse<>();
        }
    }

    public JikanApiResponse<JikanAnimeData> getCurrentSeasonAnime(int limit) {
        try {
            return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/seasons/now")
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<JikanApiResponse<JikanAnimeData>>() {})
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> !throwable.getMessage().contains("404"))
                        .doBeforeRetry(retrySignal -> logger.info("Retrying API call after error: {}", retrySignal.failure().getMessage())))
                .doOnError(error -> logger.error("Error getting current season anime: {}", error.getMessage()))
                .onErrorReturn(new JikanApiResponse<>())
                .block(Duration.ofSeconds(20));
        } catch (Exception e) {
            logger.error("Exception during seasonal anime fetch: {}", e.getMessage());
            return new JikanApiResponse<>();
        }
    }

    public JikanApiResponse<JikanAnimeData> getPopularAnime(int limit) {
        try {
            return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/anime")
                        .queryParam("order_by", "score")
                        .queryParam("sort", "desc")
                        .queryParam("limit", limit)
                        .queryParam("min_score", "8")
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<JikanApiResponse<JikanAnimeData>>() {})
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .filter(throwable -> !throwable.getMessage().contains("404"))
                        .doBeforeRetry(retrySignal -> logger.info("Retrying API call after error: {}", retrySignal.failure().getMessage())))
                .doOnError(error -> logger.error("Error getting popular anime: {}", error.getMessage()))
                .onErrorReturn(new JikanApiResponse<>())
                .block(Duration.ofSeconds(20));
        } catch (Exception e) {
            logger.error("Exception during popular anime fetch: {}", e.getMessage());
            return new JikanApiResponse<>();
        }
    }
}
