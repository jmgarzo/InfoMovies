package com.jmgarzo.udacity.popularmovies.Objects;

/**
 * Created by jmgarzo on 12/03/17.
 */

public class Review {
    private int id;
    private String webReviewId;
    private String author;
    private String content;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWebReviewId() {
        return webReviewId;
    }

    public void setWebReviewId(String webReviewId) {
        this.webReviewId = webReviewId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;
}
