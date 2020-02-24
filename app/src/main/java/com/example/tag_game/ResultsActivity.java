package com.example.tag_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    private Context context;
    private TextView tvName, tvTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setTitle("Результаты");
        context = this;

        tvName = findViewById(R.id.tvName);
        tvName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        tvTime = findViewById(R.id.tvTime);
        tvTime.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        // получаем элемент GridView
        GridView resList = (GridView) findViewById(R.id.gridRes);

    }
    public void onBackPressed() {
        this.finish();
    }
}
