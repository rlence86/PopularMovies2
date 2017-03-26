package com.ramonlence.popularmovies.entities;

import java.io.Serializable;

/**
 * Created by ramon on 26/3/17.
 */

public class Trailer implements Serializable {
    private String id;
    private String key;
    private String title;
    private String site;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
