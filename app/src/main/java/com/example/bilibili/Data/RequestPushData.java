package com.example.bilibili.Data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bilibili.Activity.PushPage;
import com.example.bilibili.Entity.PushItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestPushData
{
    private static final String PUSH_URL = "https://www.bilibili.com/index/ding.json";
    private static String[] modules = {"douga","teleplay","kichiku","dance","bangumi","fashion","life","guochuang","","movie","music","technology","game","ent"};
    private static List<PushItem> pushItemList = new ArrayList<>();




    public static void RequestData(Context context)
    {


        RequestQueue volleyQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(PUSH_URL, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                System.out.println("[Push]获取推荐视频数据成功");
                System.out.println("[Push]"+response.toString());
                try
                {
                   for(String str : modules)
                   {
                       JSONObject dougaObject = response.getJSONObject(str);

                       System.out.println("[Push]"+dougaObject.toString());

                       for (int i = 0; i < 10; i++)
                       {
                           JSONObject bufferObj = dougaObject.getJSONObject(i + "");
                           System.out.println("[Push]"+bufferObj.toString());

                           PushItem pushItem = new PushItem();

                           pushItem.setTitle(bufferObj.getString("title"));
                           pushItem.setAvid(bufferObj.getInt("aid"));
                           String name = bufferObj.getJSONObject("owner").getString("name");
                           String face = bufferObj.getJSONObject("owner").getString("face");
                           pushItem.setUserName(name);
                           pushItem.setUserFaceImg(face);
                           pushItem.setImgUrl(bufferObj.getString("pic"));
                           int num = bufferObj.getJSONObject("stat").getInt("view");
                           pushItem.setView(num);
                           pushItem.setCid(bufferObj.getInt("cid"));



                           pushItemList.add(pushItem);
                           System.out.println("[Push]"+pushItem.toString());
                       }


                       PushPage.instance.GetPushData();
                   }

                } catch (JSONException e)
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
                        System.out.println("[Push]获取推荐视频数据失败");
                    }
                }

        );

        volleyQueue.add(jsonObjectRequest);



    }

    public static List<PushItem> GetData()
    {
        return pushItemList;
    }


}
