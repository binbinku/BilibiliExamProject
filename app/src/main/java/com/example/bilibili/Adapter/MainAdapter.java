package com.example.bilibili.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.bilibili.Activity.Playpage;
import com.example.bilibili.Data.RequestCommentData;
import com.example.bilibili.Data.RequestVideoAllInfo;
import com.example.bilibili.Data.RequestVideoData;
import com.example.bilibili.Entity.Comment;
import com.example.bilibili.Entity.PushItem;
import com.example.bilibili.Entity.VideoItem;
import com.example.bilibili.Enum.RequestType;
import com.example.bilibili.R;

import java.io.Serializable;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder>
{
    public static MainAdapter instance;
    private Context context;
    private List<PushItem> pushItemList;
    private Intent toPlay;

    public MainAdapter(Context context, List<PushItem> pushItemList)
    {
        if (instance == null)
        {
            instance = this;
        }
        this.context = context;
        this.pushItemList = pushItemList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = View.inflate(context, R.layout.main_list_item, null);
        MainViewHolder mainViewHolder = new MainViewHolder(view);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position)
    {
        PushItem pushItem = pushItemList.get(position);
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(Uri.parse(pushItem.getImgUrl())).apply(options).into(holder.imageView);
        holder.userName.setText(pushItem.getUserName());
        holder.num.setText(String.valueOf(pushItem.getView()));
        holder.title.setText(pushItem.getTitle());
        holder.itemRoot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                System.out.println(position);
                RequestVideoData.RequestData(context, RequestType.PUSH, pushItem.getAvid(), pushItem.getCid());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return pushItemList.size();
    }

    public void ResponseData(VideoItem videoItem)
    {
        toPlay = new Intent(context, Playpage.class);
        toPlay.putExtra("videoInfo", videoItem);
        PushItem bf = null;
        for (PushItem pushItem : pushItemList)
        {
            if (videoItem.getAvid() == pushItem.getAvid())
            {
                bf = pushItem;
            }
        }
        if (bf == null)
        {
            bf = new PushItem();
        }
        toPlay.putExtra("PushItem", bf);
        RequestCommentData.RequestData(context, RequestType.PUSH, 1, bf.getAvid());
    }

    public void ResponseComment(List<Comment> comments)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("List<Comment>", (Serializable) comments);
        toPlay.putExtra("Comments", bundle);
        context.startActivity(toPlay);
    }


}

class MainViewHolder extends RecyclerView.ViewHolder
{
    public ImageView imageView;
    public TextView userName;
    public TextView num;
    public TextView title;
    public LinearLayout itemRoot;

    public MainViewHolder(@NonNull View itemView)
    {
        super(itemView);
        imageView = itemView.findViewById(R.id.recyc_img);
        userName = itemView.findViewById(R.id.recyc_username);
        num = itemView.findViewById(R.id.recyc_num);
        title = itemView.findViewById(R.id.recyc_title);
        itemRoot = itemView.findViewById(R.id.recyc_item);
    }
}

