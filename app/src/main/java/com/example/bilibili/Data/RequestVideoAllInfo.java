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

public class RequestVideoAllInfo
{

    private static final String VIDEO_URL = "http://api.bilibili.com/x/web-interface/view?";

    public static void RequestCid(Context context, RequestType requestType, int aid)
    {
        RequestQueue volleyQueue = Volley.newRequestQueue(context);

        String url = VIDEO_URL + "aid="+aid;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            System.out.println("[VideoALL]" + response.toString());

                            JSONObject dataObj = response.getJSONObject("data");

                            System.out.println("[VideoALL]" + dataObj.toString());

                            int cid =  dataObj.getInt("cid");

                            SearchAdapter.instance.ResponceCid(aid,cid);

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
                        System.out.println("[VideoALL]+获取视频数据失败");
                    }
                }

        );
        volleyQueue.add(jsonObjectRequest);
    }

}
