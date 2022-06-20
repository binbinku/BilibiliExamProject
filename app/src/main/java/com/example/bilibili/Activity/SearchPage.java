package com.example.bilibili.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.bilibili.Adapter.SearchAdapter;
import com.example.bilibili.Data.RequestSearchData;
import com.example.bilibili.Entity.PushItem;
import com.example.bilibili.Enum.RequestType;
import com.example.bilibili.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchPage extends AppCompatActivity
{
    public static SearchPage instance;
    private ListView listView;
    private List<PushItem> pushItemList;
    private SearchAdapter searchAdapter;

    private EditText searchET;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);
        if(instance==null)
        {
            instance =this;
        }
        Init();
        ResponseData();
        EventHandler();

    }

    private void EventHandler()
    {
        searchBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String keyword = searchET.getText().toString();
                RequestSearchData.RequestData(SearchPage.this, RequestType.SEARCH,keyword);
            }
        });
    }

    private void ResponseData()
    {
        Intent toSearch = getIntent();
        Bundle bundle = toSearch.getBundleExtra("pushItemList");

        pushItemList = (List<PushItem>) bundle.getSerializable("List<PushItem>");

        searchAdapter = new SearchAdapter(this, pushItemList);

        listView.setAdapter(searchAdapter);


    }
    

    private void Init()
    {
        listView = findViewById(R.id.search_listview);
        searchET = findViewById(R.id.seh_search_ev);
        searchBtn =findViewById(R.id.seh_search_btn);
    }

    public void ResponseSearchData(List<PushItem> pushItemList)
    {
        this.pushItemList = pushItemList;
        searchAdapter = new SearchAdapter(this, pushItemList);

        listView.setAdapter(searchAdapter);
    }
}