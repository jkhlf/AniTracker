package br.com.animetracker.AniTracker.api.jikan;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JikanImageData {

    @JsonProperty("jpg")
    private JikanImageUrl jpg;

    @JsonProperty("webp")
    private JikanImageUrl webp;

    public static class JikanImageUrl {
        @JsonProperty("image_url")
        private String imageUrl;

        @JsonProperty("small_image_url")
        private String smallImageUrl;

        @JsonProperty("large_image_url")
        private String largeImageUrl;

        // Getters and Setters
        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getSmallImageUrl() {
            return smallImageUrl;
        }

        public void setSmallImageUrl(String smallImageUrl) {
            this.smallImageUrl = smallImageUrl;
        }

        public String getLargeImageUrl() {
            return largeImageUrl;
        }

        public void setLargeImageUrl(String largeImageUrl) {
            this.largeImageUrl = largeImageUrl;
        }
    }

    // Getters and Setters
    public JikanImageUrl getJpg() {
        return jpg;
    }

    public void setJpg(JikanImageUrl jpg) {
        this.jpg = jpg;
    }

    public JikanImageUrl getWebp() {
        return webp;
    }

    public void setWebp(JikanImageUrl webp) {
        this.webp = webp;
    }
}
