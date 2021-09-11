package com.example.android.newsapp;

import java.util.Date;

public class News {

    private String mArticalTitle;
    private String mSectionName;
    private String mAuthorName;
    private String mDatePublished;
    private String mUrl;

    public News(String articalTitle, String sectionName, String authorName, String datePublished, String url) {

        mArticalTitle = articalTitle;
        mSectionName = sectionName;
        mAuthorName = authorName;
        mDatePublished = datePublished;
        mUrl = url;
    }

    public String getmArticalTitle() {
        return mArticalTitle;
    }

    public String getmSectionName() {
        return mSectionName;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }

    public String getmDatePublished() {
        return mDatePublished;
    }

    public String getmUrl() {
        return mUrl;
    }
}
