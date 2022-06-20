package com.example.bilibili.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bilibili.Entity.Comment;
import com.example.bilibili.Entity.PushItem;
import com.example.bilibili.Entity.VideoItem;
import com.example.bilibili.R;

import java.util.List;
import java.util.zip.Inflater;

public class Playpage extends AppCompatActivity
{
    private WebView webView;
    private LinearLayout commentLayout;
    private ImageView mainImg;
    private TextView mainName;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playpage);
        Init();
        InitWebView();
        ParseData();

    }

    private void Init()
    {
        getWindow().setStatusBarColor(getResources().getColor(R.color.transf));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        commentLayout = findViewById(R.id.playpage_comments);
        mainName =findViewById(R.id.playpage_mainname);
        mainImg  =findViewById(R.id.playpage_mainimg);
        title = findViewById(R.id.playpage_title);
    }

    private void ParseData()
    {
        Intent toPlay = getIntent();

        VideoItem videoItem = (VideoItem) toPlay.getSerializableExtra("videoInfo");

        PushItem pushItem = (PushItem)toPlay.getSerializableExtra("PushItem");

        mainName.setText(pushItem.getUserName());

        title.setText(pushItem.getTitle());

        RoundedCorners roundedCorners = new RoundedCorners(10);

        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

        Glide.with(Playpage.this).load(Uri.parse(pushItem.getUserFaceImg())).apply(options).into(mainImg);

        Bundle bundle = toPlay.getBundleExtra("Comments");

        List<Comment> comments = (List<Comment>) bundle.getSerializable("List<Comment>");

        webView.loadUrl(videoItem.getVideoURL());

        for (Comment comment : comments)
        {
            View view = Playpage.this.getLayoutInflater().inflate(R.layout.comment_item,null);

            ImageView userImg = view.findViewById(R.id.comment_userimg);

            TextView userName = view.findViewById(R.id.comment_username);

            TextView content = view.findViewById(R.id.comment_content);


            Glide.with(Playpage.this).load(Uri.parse(comment.getUserImg())).apply(options).into(userImg);

            userName.setText(comment.getUserName());

            content.setText(comment.getContent());

            commentLayout.addView(view);

        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void InitWebView()
    {
        //初始化WebView控件
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUserAgentString("PC");
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                return false;
            }
        });

    }

}