package br.com.animetracker.AniTracker.model;

import java.io.Serializable;
import java.util.Objects;

public class UserFavoriteId implements Serializable {

    private Long user;
    private Long anime;

    public UserFavoriteId() {
    }

    public UserFavoriteId(Long user, Long anime) {
        this.user = user;
        this.anime = anime;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getAnime() {
        return anime;
    }

    public void setAnime(Long anime) {
        this.anime = anime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserFavoriteId that = (UserFavoriteId) o;
        return Objects.equals(user, that.user) &&
               Objects.equals(anime, that.anime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, anime);
    }
}
