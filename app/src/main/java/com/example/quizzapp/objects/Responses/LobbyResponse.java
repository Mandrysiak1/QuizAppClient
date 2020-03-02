package com.example.quizzapp.objects.Responses;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LobbyResponse {

    @SerializedName("lobbies")
    @Expose
    private List<Lobby> lobbies = null;

    public List<Lobby> getLobbies() {
        return lobbies;
    }

    public void setLobbies(List<Lobby> lobbies) {
        this.lobbies = lobbies;
    }

}