package com.example.quizzapp.objects.Responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lobby {

    @SerializedName("playersNames")
    @Expose
    private List<String> playersNames = null;
    @SerializedName("id")
    @Expose
    private String id;

    public List<String> getPlayersNames() {
        return playersNames;
    }

    public void setPlayersNames(List<String> playersNames) {
        this.playersNames = playersNames;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}