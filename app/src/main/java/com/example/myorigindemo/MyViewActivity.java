package com.example.myorigindemo;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myorigindemo.myview.LetterSideBar;
import com.example.myorigindemo.myview.ShapeView;

public class MyViewActivity extends AppCompatActivity implements LetterSideBar.LetterTouchListener {
    private Button bt_change_shape;
    private ShapeView shapeView;
    private LetterSideBar letterSideBar;
    private TextView tv_slida_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view);
        bt_change_shape=findViewById(R.id.bt_change_shape);
        shapeView=findViewById(R.id.shapeView);
        letterSideBar=findViewById(R.id.ls);
        tv_slida_bar=findViewById(R.id.tv_slide_bar);
        letterSideBar.setmListener(this);
        bt_change_shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exeChange();
            }
        });
    }
    private void exeChange(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shapeView.changeShape();
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                }

        }).start();
    }

    @Override
    public void touch(String text,boolean isTouch) {
        tv_slida_bar.setVisibility(isTouch?View.VISIBLE:View.GONE);
        tv_slida_bar.setText(text);
    }
}