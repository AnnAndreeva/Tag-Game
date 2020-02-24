package com.example.tag_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SelectActivity extends AppCompatActivity {
    private Context context;
    private Button img1Btn, img2Btn, imgCustomBtn;
    private TextView text;
    public String selected = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        context = this;

        text = findViewById(R.id.textView);
        text.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        img1Btn = findViewById(R.id.img1Btn);
        img1Btn.setOnClickListener(selectImg);

        img2Btn = findViewById(R.id.img2Btn);
        img2Btn.setOnClickListener(selectImg);

        imgCustomBtn = findViewById(R.id.imgCustomBtn);
        imgCustomBtn.setOnClickListener(selectImg);
        imgCustomBtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));
    }
    View.OnClickListener selectImg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent start = new Intent();
            start.setClass(context,GameActivity.class);
            switch (v.getId()) {
                case R.id.img1Btn: selected = "cat"; break;
                case R.id.img2Btn: selected = "dog"; break;
                case R.id.imgCustomBtn: selected = "custom"; break;
            }
            start.putExtra("selected", selected);
            startActivity(start);
        }
    };
}
