package br.com.animetracker.AniTracker.api.jikan;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JikanApiResponse<T> {

    @JsonProperty("data")
    private List<T> data;

    @JsonProperty("pagination")
    private JikanPagination pagination;

    // Getters and Setters
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public JikanPagination getPagination() {
        return pagination;
    }
    public void setPagination(JikanPagination pagination) {
        this.pagination = pagination;
    }

    public static class JikanPagination {
        @JsonProperty("last_visible_page")
        private int lastVisiblePage;

        @JsonProperty("has_next_page")
        private boolean hasNextPage;

        public int getLastVisiblePage() {
            return lastVisiblePage;
        }

        public void setLastVisiblePage(int lastVisiblePage) {
            this.lastVisiblePage = lastVisiblePage;
        }

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public void setHasNextPage(boolean hasNextPage) {
            this.hasNextPage = hasNextPage;
        }
    }
}
