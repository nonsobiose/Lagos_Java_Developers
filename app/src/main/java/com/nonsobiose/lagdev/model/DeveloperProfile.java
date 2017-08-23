package com.nonsobiose.lagdev.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by slimb on 16/07/2017.
 */

@Parcel
public class DeveloperProfile {

    @SerializedName("login")
    @Expose
    private String login;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("location")
    @Expose
    public String location;

    @SerializedName("public_repos")
    @Expose
    public Integer publicRepos;

    @SerializedName("avatar_url")
    @Expose
    public String avatarUrl;

    @SerializedName("html_url")
    @Expose
    public String htmlUrl;

    public DeveloperProfile(String login, String avatarUrl, String name, String htmlUrl, String location, Integer publicRepos) {
        this.login = login;
        this.avatarUrl = avatarUrl;
        this.name = name;
        this.htmlUrl = htmlUrl;
        this.location = location;
        this.publicRepos = publicRepos;
    }

    public DeveloperProfile() {

    }

    public String getLogin() {
        return login;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getName() {
        return name;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getLocation() {
        return location;
    }

    public Integer getPublicRepos() {
        return publicRepos;
    }
}
