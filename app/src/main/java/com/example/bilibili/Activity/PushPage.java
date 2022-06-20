package com.example.bilibili.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bilibili.Adapter.MainAdapter;
import com.example.bilibili.Data.RequestPushData;
import com.example.bilibili.Data.RequestSearchData;
import com.example.bilibili.Entity.PushItem;
import com.example.bilibili.Enum.RequestType;
import com.example.bilibili.R;

import java.io.Serializable;
import java.util.List;

public class PushPage extends AppCompatActivity
{
    public static PushPage instance;
    private RecyclerView recyclerView;
    private List<PushItem> bufferPushItems;

    private EditText searchEditText;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(instance==null)
        {
            instance=this;
        }

        Init();

        RequestPushData.RequestData(this);

        EventHandler();

    }

    private void EventHandler()
    {
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String keyword = searchEditText.getText().toString();
                RequestSearchData.RequestData(PushPage.this, RequestType.PUSH,keyword);

            }
        });

    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private void Init()
    {
        getWindow().setStatusBarColor(getResources().getColor(R.color.transf));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//实现状态栏图标和文字颜色为暗色
        recyclerView = findViewById(R.id.recyclerview);
        searchButton = findViewById(R.id.main_search_btn);
        searchEditText = findViewById(R.id.main_search_ev);
    }



    public void GetPushData()
    {
        bufferPushItems = RequestPushData.GetData();

        MainAdapter mainAdapter = new MainAdapter(this, bufferPushItems);

        System.out.println("已设置Adapter");

        recyclerView.setAdapter(mainAdapter);

        GridLayoutManager layoutManager = new GridLayoutManager(PushPage.this,2);

        recyclerView.setLayoutManager(layoutManager);
    }

    public void ResponseSearchData(List<PushItem> pushItemList)
    {
        Intent toSearch = new Intent(PushPage.this,SearchPage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("List<PushItem>", (Serializable) pushItemList);
        toSearch.putExtra("pushItemList",bundle);
        startActivity(toSearch);
    }



}