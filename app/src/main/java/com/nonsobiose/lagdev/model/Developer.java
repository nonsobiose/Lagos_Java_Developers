package com.nonsobiose.lagdev.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Developer {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

    @SerializedName("url")
    @Expose
    private String url;

    public Developer(String login, String avatarUrl, String url) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.url = url;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUrl() {
        return url;
    }
}

