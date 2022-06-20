package com.example.bilibili.Entity;

import java.io.Serializable;

public class VideoItem implements Serializable
{
    private String videoURL;
    private int avid;



    public VideoItem()
    {

    }
    public int getAvid()
    {
        return avid;
    }

    public void setAvid(int avid)
    {
        this.avid = avid;
    }
    public String getVideoURL()
    {
        return videoURL;
    }

    public void setVideoURL(String videoURL)
    {
        this.videoURL = videoURL;
    }
}
