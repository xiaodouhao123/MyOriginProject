package com.example.myorigindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VerticalDragListViewActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_drag_list_view);
        final List<String> list=new ArrayList<>();
        for (int i=0;i<50;i++){
            list.add("="+i);
        }
        listView=findViewById(R.id.lv_front);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                    TextView textView= (TextView) LayoutInflater.from(VerticalDragListViewActivity.this).inflate(R.layout.item_view,parent,false);
                textView.setText(list.get(position));
                return textView;
            }
        });
    }
}