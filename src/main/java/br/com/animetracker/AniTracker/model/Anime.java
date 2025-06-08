package br.com.animetracker.AniTracker.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;


@Entity
@Table(name = "animes")
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mal_id", unique = true, nullable = false)
    private Long malId;

    @Column(nullable = false)
    private String title;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(length = 2000)
    private String synopsis;

    @Column(length = 500)
    private String genres;

    @Column(name = "url_mal", length = 500)
    private String urlMal;

    @OneToMany(mappedBy = "anime", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserFavorite> favoritedBy = new HashSet<>();

    public Anime() {
    }

    public Anime(Long malId, String title, String imageUrl, String synopsis, String genres, String urlMal) {
        this.malId = malId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.synopsis = synopsis;
        this.genres = genres;
        this.urlMal = urlMal;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getUrlMal() {
        return urlMal;
    }

    public void setUrlMal(String urlMal) {
        this.urlMal = urlMal;
    }

    public Set<UserFavorite> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(Set<UserFavorite> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", malId=" + malId +
                ", title='" + title + '\'' +
                ", genres='" + genres + '\'' +
                '}';
    }
}
