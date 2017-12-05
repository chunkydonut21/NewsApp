package com.example.android.newsapp;

public class News {

    private String mType;
    private String mTitle;
    private String mUrl;
    private String mThumbnail;

    public News(String type, String title, String url, String thumbnail){
        mType = type;
        mTitle = title;
        mUrl = url;
        mThumbnail = thumbnail;
    }

    public String getType(){
        return mType;

    }  public String getTitle(){
        return mTitle;

    }  public String getUrl(){
        return mUrl;
    }

    public String getThumbnail(){
        return mThumbnail;
    }
}
