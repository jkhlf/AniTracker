package br.com.animetracker.AniTracker.api.jikan;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JikanGenreData {

    @JsonProperty("mal_id")
    private Integer malId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    // Getters and Setters
    public Integer getMalId() {
        return malId;
    }
    public void setMalId(Integer malId) {
        this.malId = malId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type; 
    }
}
