package br.com.animetracker.AniTracker.api.jikan;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JikanAnimeData {

    @JsonProperty("mal_id")
    private Long malId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("images")
    private JikanImageData images;

    @JsonProperty("synopsis")
    private String synopsis;

    @JsonProperty("genres")
    private List<JikanGenreData> genres;

    @JsonProperty("url")
    private String url;

    @JsonProperty("score")
    private Double score;

    @JsonProperty("episodes")
    private Integer episodes;

    @JsonProperty("status")
    private String status;

    // Getters and Setters
    public Long getMalId() {
        return malId;
    }

    public void setMalId(Long malId) {
        this.malId = malId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public JikanImageData getImages() {
        return images;
    }

    public void setImages(JikanImageData images) {
        this.images = images;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public List<JikanGenreData> getGenres() {
        return genres;
    }

    public void setGenres(List<JikanGenreData> genres) {
        this.genres = genres;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
