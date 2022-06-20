package com.example.bilibili.Data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bilibili.Adapter.MainAdapter;
import com.example.bilibili.Adapter.SearchAdapter;
import com.example.bilibili.Entity.VideoItem;
import com.example.bilibili.Enum.RequestType;

import org.json.JSONArray;
import org.json.JSONObject;

public class RequestVideoData
{
    private static final String VIDEOINFO_URL = "http://api.bilibili.com/x/player/playurl?";

    private static VideoItem videoItem;
    private static final int QN = 16;

    public static void RequestData(Context context, RequestType requestType, int avid, int cid)
    {
        String url = VIDEOINFO_URL+"avid="+avid+"&cid="+cid+"&qn="+QN;

        RequestQueue volleyQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            System.out.println("[Video]"+response.toString());

                            JSONObject dataObj = response.getJSONObject("data");

                            System.out.println("[Video]"+dataObj.toString());

                            JSONArray durlArray = dataObj.getJSONArray("durl");

                            System.out.println("[Video]"+durlArray.get(0).toString());

                            JSONObject durArray0 = (JSONObject) durlArray.get(0);

                            String videoURL = durArray0.getString("url")+"&referer=https://www.bilibili.com";

                            System.out.println("[Video]"+videoURL);

                            VideoItem videoItem = new VideoItem();

                            videoItem.setAvid(avid);

                            videoItem.setVideoURL(videoURL);


                            switch (requestType)
                            {
                                case PUSH:
                                    MainAdapter.instance.ResponseData(videoItem);
                                    break;
                                case SEARCH:
                                    SearchAdapter.instance.ResponseData(videoItem);
                                    break;
                                default:
                                    System.out.println("视频数据未能正确响应");
                                    break;
                            }

                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        System.out.println("[Video]+获取视频数据失败");
                    }
                }

        );
        volleyQueue.add(jsonObjectRequest);
    }

    public VideoItem GetVideoInfo()
    {
        return videoItem;
    }


}
