package br.com.animetracker.AniTracker.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;


@Entity
@Table(name = "user_favorites")
@IdClass(UserFavoriteId.class)
public class UserFavorite {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "anime_id")
    private Anime anime;

    @Column(name = "favorited_at")
    private LocalDateTime favoritedAt;

    @PrePersist
    protected void onCreate() {
        this.favoritedAt = LocalDateTime.now();
    }

    public UserFavorite() {
    }

    public UserFavorite(User user, Anime anime) {
        this.user = user;
        this.anime = anime;
    }

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Anime getAnime() {
        return anime;
    }

    public void setAnime(Anime anime) {
        this.anime = anime;
    }

    public LocalDateTime getFavoritedAt() {
        return favoritedAt;
    }

    public void setFavoritedAt(LocalDateTime favoritedAt) {
        this.favoritedAt = favoritedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFavorite)) return false;

        UserFavorite that = (UserFavorite) o;

        if (!getUser().equals(that.getUser())) return false;
        return getAnime().equals(that.getAnime());
    }

    @Override
    public int hashCode() {
        int result = getUser().hashCode();
        result = 31 * result + getAnime().hashCode();
        return result;
    }
}
