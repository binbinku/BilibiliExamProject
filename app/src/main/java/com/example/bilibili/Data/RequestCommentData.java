package com.example.bilibili.Data;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.bilibili.Adapter.MainAdapter;
import com.example.bilibili.Adapter.SearchAdapter;
import com.example.bilibili.Entity.Comment;
import com.example.bilibili.Enum.RequestType;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RequestCommentData
{
    private static final String CommentURL = "http://api.bilibili.com/x/v2/reply?";

    public static void RequestData(Context context, RequestType requestType, int type, int avid)
    {
        RequestQueue volleyQueue = Volley.newRequestQueue(context);
        String url = CommentURL + "type=" + type + "&oid=" + avid;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                System.out.println("[Comment]获取视频评论成功");
                System.out.println("[Comment]" + response.toString());

                JSONObject dataObj = null;
                try
                {
                    List<Comment> comments = new ArrayList<>();

                    dataObj = response.getJSONObject("data");

                    JSONArray replies = dataObj.getJSONArray("replies");

                    for (int i = 0; i < replies.length(); i++)
                    {
                        JSONObject obj = (JSONObject) replies.get(i);
                        System.out.println("[Comment]" + obj.toString());

                        JSONObject memberObj = obj.getJSONObject("member");
                        Comment comment = new Comment();
                        String uname = memberObj.getString("uname");
                        String avatar = memberObj.getString("avatar");
                        int mid = memberObj.getInt("mid");
                        comment.setMid(mid);
                        comment.setUserImg(avatar);
                        comment.setUserName(uname);

                        JSONObject contentObj = obj.getJSONObject("content");
                        String message = contentObj.getString("message");
                        comment.setContent(message);
                        comments.add(comment);
                    }

                    switch (requestType)
                    {
                        case PUSH:
                            MainAdapter.instance.ResponseComment(comments);
                            break;
                        case SEARCH:
                            SearchAdapter.instance.ResponseComment(comments);
                            break;
                        default:
                            System.out.println("评论数据未能正确响应");
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
                        System.out.println("++++获取视频评论失败");
                    }
                }

        );

        volleyQueue.add(jsonObjectRequest);
    }

}
