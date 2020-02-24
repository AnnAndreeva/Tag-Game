package com.example.tag_game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Button play, results, info;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Пятнашки");
        context = this;

        name = findViewById(R.id.tvAppName);
        name.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        play = findViewById(R.id.btnPlay);
        play.setOnClickListener(startGame);
        play.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        results = findViewById(R.id.btnResults);
        results.setOnClickListener(showResults);
        results.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        info = findViewById(R.id.btnInfo);
        info.setOnClickListener(showInfo);
        info.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

    }

    View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent start = new Intent();
            start.setClass(context,SelectActivity.class);
            startActivity(start);
        }
    };

    View.OnClickListener showInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.AlertDialogCustom));
            builder.setTitle("Справка");
            builder.setMessage("Игра-головоломка.\n" +
                    "Представляет собой поле размером 4x4, в котором расположены квадраты с " +
                    "фрагментами изображения. Пользователь может перемещать квадраты по полю " +
                    "за счёт пустой ячейки. Цель игры - собрать изображение за наименьшее " +
                    "число шагов.\nСоздатель: Андреева А.А.\nСтудентка СНИУ им. Королёва\n2019 г.");
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    };

    View.OnClickListener showResults = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent show = new Intent();
            show.setClass(context, ResultsActivity.class);
            startActivity(show);
        }
    };
    public void onBackPressed() {
        this.finish();
    }

}
