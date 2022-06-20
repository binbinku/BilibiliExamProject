package com.example.bilibili.Data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bilibili.Activity.PushPage;
import com.example.bilibili.Activity.SearchPage;
import com.example.bilibili.Entity.PushItem;
import com.example.bilibili.Enum.RequestType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestSearchData
{
    private static final String SEARCH_URL = "https://api.bilibili.com/x/web-interface/search/all/v2?";
    private static final int PAGE = 1;
    private static final int PAGESIZE = 64;

    public static void RequestData(Context context, RequestType requestType, String keyword)
    {
        RequestQueue volleyQueue = Volley.newRequestQueue(context);

        String url = SEARCH_URL + "keyword=" + keyword + "&page=" + PAGE + "&pagesize=" + PAGESIZE;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                System.out.println("[Search]获取搜索数据成功");
                System.out.println("[Search]" + response.toString());

                try
                {
                    JSONObject dataObj = response.getJSONObject("data");
                    System.out.println("[Search]" + dataObj.toString());

                    JSONArray resultArray = dataObj.getJSONArray("result");

                    JSONObject videoResult = null;
                    for (int i = 0; i < resultArray.length(); i++)
                    {
                        JSONObject obj = (JSONObject) resultArray.get(i);
                        System.out.println("[Comment]" + obj.toString());
                        if (obj.getString("result_type").equals("video"))
                        {
                            videoResult = obj;
                        }

                    }

                    if (videoResult != null)
                    {
                        List<PushItem> pushItemList = new ArrayList<>();

                        System.out.println("[Search]" + videoResult.toString());
                        JSONArray dataArray = videoResult.getJSONArray("data");

                        for (int i = 0; i < dataArray.length(); i++)
                        {
                            JSONObject obj = (JSONObject) dataArray.get(i);

                            PushItem pushItem = new PushItem();

                            String title = obj.getString("title");
                            String[] str = title.split("<|>");
                            String name = obj.getString("author");
                            String fixtitle ="";
                            for(String s : str)
                            {
                                if(s.contains("em"))
                                    continue;
                                fixtitle+=s;
                            }
                            int num = obj.getInt("play");
                            String imgurl = "http:" + obj.getString("pic");
                            String upic = obj.getString("upic");

                            int avid = obj.getInt("aid");
                            int cid = obj.getInt("id");

                            pushItem.setAvid(avid);
                            pushItem.setTitle(fixtitle);
                            pushItem.setImgUrl(imgurl);
                            pushItem.setView(num);
                            pushItem.setUserName(name);
                            pushItem.setUserFaceImg(upic);
                            pushItem.setCid(cid);

                            pushItemList.add(pushItem);
                        }
                        switch (requestType)
                        {
                            case PUSH:
                                PushPage.instance.ResponseSearchData(pushItemList);
                                break;
                            case SEARCH:
                                SearchPage.instance.ResponseSearchData(pushItemList);
                            default:
                                System.out.println("[Search]数据响应异常");
                                break;
                        }

                    } else
                    {
                        System.out.println("[Search]" + "视频数据获取失败");
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
                        System.out.println("[Search]获取搜索数据失败");
                    }
                }

        );

        volleyQueue.add(jsonObjectRequest);
    }

}
