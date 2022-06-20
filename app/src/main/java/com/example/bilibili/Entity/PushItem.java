package com.example.bilibili.Entity;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class PushItem implements Serializable
{
    private  int avid;
    private  String imgUrl;
    private  String userName;
    private  int view;
    private  String title;
    private  String videoURL;
    private  int cid;
    private  String userFaceImg;

    public PushItem()
    {
    }

    public PushItem(String imgUrl, String title, String userName, int view)
    {
        this.imgUrl = imgUrl;
        this.userName = userName;
        this.view = view;
        this.title =title;
    }

    public String getUserFaceImg()
    {
        return userFaceImg;
    }

    public void setUserFaceImg(String userFaceImg)
    {
        this.userFaceImg = userFaceImg;
    }

    public int getCid()
    {
        return cid;
    }

    public void setCid(int cid)
    {
        this.cid = cid;
    }

    public String getVideoURL()
    {
        return videoURL;
    }

    public void setVideoURL(String videoURL)
    {
        this.videoURL = videoURL;
    }

    public int getAvid()
    {
        return avid;
    }

    public void setAvid(int avid)
    {
        this.avid = avid;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl)
    {
        this.imgUrl = imgUrl;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getView()
    {
        return view;
    }

    public void setView(int view)
    {
        this.view = view;
    }

    @NonNull
    public String toString()
    {
        String str =
                "\navid:"+avid+
                "\ntitle:"+title+
                "\nuserName:"+userName+
                "\nview:"+view+
                "\nimgUrl:"+imgUrl;
        return str;
    }

}
