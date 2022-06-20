package com.example.bilibili.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.example.bilibili.Enum.SearchVideoResponseOrder;
import com.example.bilibili.R;

import java.io.Serializable;
import java.util.List;

public class SearchAdapter extends BaseAdapter
{
    public static SearchAdapter instance;
    private Context context;
    private List<PushItem> itemList;

    private Intent toPlay;


    public SearchAdapter(Context context, List<PushItem> itemList)
    {
        this.context = context;
        this.itemList = itemList;

        instance = null;

        instance = this;

    }

    @Override
    public int getCount()
    {
        return itemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        SearchViewHolder searchViewHolder = null;
        PushItem pushItem = itemList.get(position);

        if (convertView == null)
        {
            convertView = View.inflate(context, R.layout.search_list_item, null);

            ImageView imageView = convertView.findViewById(R.id.search_item_img);
            TextView title = convertView.findViewById(R.id.search_item_title);
            TextView name = convertView.findViewById(R.id.search_item_name);
            TextView num = convertView.findViewById(R.id.search_item_num);
            LinearLayout root = convertView.findViewById(R.id.search_item_root);

            root.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    System.out.println("点击播放");
                    RequestVideoAllInfo.RequestCid(context, RequestType.SEARCH, pushItem.getAvid());
                }
            });

            searchViewHolder = new SearchViewHolder();

            searchViewHolder.imageView = imageView;
            searchViewHolder.title = title;
            searchViewHolder.name = name;
            searchViewHolder.num = num;
            searchViewHolder.root = root;


            convertView.setTag(searchViewHolder);

        } else
        {
            searchViewHolder = (SearchViewHolder) convertView.getTag();
        }

        //TODO:数据更新
        RoundedCorners roundedCorners = new RoundedCorners(10);
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
        Glide.with(context).load(Uri.parse(pushItem.getImgUrl())).apply(options).into(searchViewHolder.imageView);

        searchViewHolder.title.setText(pushItem.getTitle());

        searchViewHolder.name.setText(pushItem.getUserName());

        searchViewHolder.num.setText(String.valueOf(pushItem.getView()));


        return convertView;
    }

    public void ResponceCid(int aid, int cid)
    {
        for (PushItem pushItem : itemList)
        {
            if (pushItem.getAvid() == aid)
            {
                pushItem.setCid(cid);
                break;
            }
        }
        RequestVideoData.RequestData(context, RequestType.SEARCH, aid, cid);
    }


    public void ResponseData(VideoItem videoItem)
    {

        toPlay = new Intent(context, Playpage.class);

        toPlay.putExtra("videoInfo", videoItem);


        PushItem bf = null;

        for (PushItem pushItem : itemList)
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

        RequestCommentData.RequestData(context, RequestType.SEARCH, 1, bf.getAvid());

    }

    public void ResponseComment(List<Comment> comments)
    {

        Bundle bundle = new Bundle();

        bundle.putSerializable("List<Comment>", (Serializable) comments);

        toPlay.putExtra("Comments", bundle);

        context.startActivity(toPlay);

    }


}

class SearchViewHolder
{
    public LinearLayout root;
    public ImageView imageView;
    public TextView title;
    public TextView name;
    public TextView num;
}

