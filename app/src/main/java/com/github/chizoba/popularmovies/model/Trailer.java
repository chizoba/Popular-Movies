package com.github.chizoba.popularmovies.model;

/**
 * Created by Chizoba on 5/10/2017.
 */

public class Trailer {
    String id;
    public String name;
    public String key;

    public Trailer(String id, String name, String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }
}
