package com.example.tag_game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class GameActivity extends AppCompatActivity {

    private static final int COLUMNS = 4;
    private static final int DIMENSIONS = COLUMNS*COLUMNS;

    private static String[] tileList; //список ячеек

    private static GestureDetectGridView mGridView;

    private static int mColumnWidth, mColumnHeight;

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String RIGHT = "right";
    public static final String LEFT = "left";


    private Context context;
    private Button start, pause;

    private int seconds = 0;
    private static boolean running;

    private static String selected ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setTitle("Игра");
        context = this;

        Bundle arguments = getIntent().getExtras();
        selected = arguments.get("selected").toString();

        init();//инициализация списка ячеек

        display(context);//отображение кнопок

        setDimensions();

        start = findViewById(R.id.btnStart);
        start.setOnClickListener(startGame);
        start.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        pause = findViewById(R.id.btnPause);
        pause.setOnClickListener(pauseGame);

        runTimer();
    }

    private void runTimer() {
        final TextView tvTime = findViewById(R.id.tvTime);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int min = (seconds % 3600) / 60;
                int sec = seconds % 60;
                String time = String.format("%02d:%02d", min, sec);
                tvTime.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));
                tvTime.setText(time);
                if(running){seconds++;}
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void setDimensions() {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mColumnWidth = mGridView.getWidth() / COLUMNS;
                mColumnHeight= mGridView.getHeight() / COLUMNS;

                display(context);
            }
        });
    }

    private static void display(Context context) { //отображение
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;

        //инициализируем кнопки
        switch (selected){
            case "cat":{
                for (int i = 0; i < tileList.length; i++){
                    button = new Button(context);

                    if(tileList[i].equals("0")){
                        button.setBackgroundResource(R.drawable.cat_piece_1);
                    } else if(tileList[i].equals("1")){
                        button.setBackgroundResource(R.drawable.cat_piece_2);
                    } else if(tileList[i].equals("2")){
                        button.setBackgroundResource(R.drawable.cat_piece_3);
                    } else if(tileList[i].equals("3")){
                        button.setBackgroundResource(R.drawable.cat_piece_4);
                    } else if(tileList[i].equals("4")){
                        button.setBackgroundResource(R.drawable.cat_piece_5);
                    } else if(tileList[i].equals("5")){
                        button.setBackgroundResource(R.drawable.cat_piece_6);
                    } else if(tileList[i].equals("6")){
                        button.setBackgroundResource(R.drawable.cat_piece_7);
                    } else if(tileList[i].equals("7")){
                        button.setBackgroundResource(R.drawable.cat_piece_8);
                    } else if(tileList[i].equals("8")){
                        button.setBackgroundResource(R.drawable.cat_piece_9);
                    } else if(tileList[i].equals("9")){
                        button.setBackgroundResource(R.drawable.cat_piece_10);
                    } else if(tileList[i].equals("10")){
                        button.setBackgroundResource(R.drawable.cat_piece_11);
                    } else if(tileList[i].equals("11")){
                        button.setBackgroundResource(R.drawable.cat_piece_12);
                    } else if(tileList[i].equals("12")){
                        button.setBackgroundResource(R.drawable.cat_piece_13);
                    } else if(tileList[i].equals("13")){
                        button.setBackgroundResource(R.drawable.cat_piece_14);
                    } else if(tileList[i].equals("14")){
                        button.setBackgroundResource(R.drawable.cat_piece_15);
                    } else if(tileList[i].equals("15")){
                        button.setBackgroundResource(R.drawable.cat_piece_16);
                        button.setVisibility(View.INVISIBLE);
                    }
                    buttons.add(button);
                }
            } break;

            case "dog":{
                for (int i = 0; i < tileList.length; i++){
                    button = new Button(context);

                    if(tileList[i].equals("0")){
                        button.setBackgroundResource(R.drawable.dog_piece_1);
                    } else if(tileList[i].equals("1")){
                        button.setBackgroundResource(R.drawable.dog_piece_2);
                    } else if(tileList[i].equals("2")){
                        button.setBackgroundResource(R.drawable.dog_piece_3);
                    } else if(tileList[i].equals("3")){
                        button.setBackgroundResource(R.drawable.dog_piece_4);
                    } else if(tileList[i].equals("4")){
                        button.setBackgroundResource(R.drawable.dog_piece_5);
                    } else if(tileList[i].equals("5")){
                        button.setBackgroundResource(R.drawable.dog_piece_6);
                    } else if(tileList[i].equals("6")){
                        button.setBackgroundResource(R.drawable.dog_piece_7);
                    } else if(tileList[i].equals("7")){
                        button.setBackgroundResource(R.drawable.dog_piece_8);
                    } else if(tileList[i].equals("8")){
                        button.setBackgroundResource(R.drawable.dog_piece_9);
                    } else if(tileList[i].equals("9")){
                        button.setBackgroundResource(R.drawable.dog_piece_10);
                    } else if(tileList[i].equals("10")){
                        button.setBackgroundResource(R.drawable.dog_piece_11);
                    } else if(tileList[i].equals("11")){
                        button.setBackgroundResource(R.drawable.dog_piece_12);
                    } else if(tileList[i].equals("12")){
                        button.setBackgroundResource(R.drawable.dog_piece_13);
                    } else if(tileList[i].equals("13")){
                        button.setBackgroundResource(R.drawable.dog_piece_14);
                    } else if(tileList[i].equals("14")){
                        button.setBackgroundResource(R.drawable.dog_piece_15);
                    } else if(tileList[i].equals("15")){
                        button.setBackgroundResource(R.drawable.dog_piece_16);
                        button.setVisibility(View.INVISIBLE);
                    }
                    buttons.add(button);
                }
            } break;

            case "custom":{
                selected = "cat";
            } break;
        }

        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    private void scramble() { //перемешивание ячеек в массиве
        int index;
        String temp;
        Random random = new Random();
        for (int i = tileList.length - 1; i>0;i--){
            index = random.nextInt(i+1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private void init() { //инициализация списка ячеек
        mGridView = (GestureDetectGridView)findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);


        tileList = new String[DIMENSIONS];
        for(int i = 0; i< DIMENSIONS; i++){
            tileList[i] = String.valueOf(i);
        }
    }

    View.OnClickListener startGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setClickable(false);
            running = true;
            scramble();//перемешивание ячеек в массиве
            display(context);
        }
    };

    View.OnClickListener pauseGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            running = false;
            String title = "Игра на паузе.";
            String posString = "Продолжить";
            running = false;
            AlertDialog.Builder ad = new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.AlertDialogCustom));
            ad.setTitle(title);  // заголовок
            ad.setPositiveButton(posString, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    running = true;
                    dialog.cancel();
                }
            });
            ad.setCancelable(false);
            AlertDialog dialog = ad.create();
            dialog.show();
        }
    };


    private static void swap(Context context, int position, int swap){
        if(tileList[position+swap].equals("15")) {
            String newPosition = tileList[position + swap];
            tileList[position + swap] = tileList[position];
            tileList[position] = newPosition;
        }
        display(context);

        if(isSolved()){
            Toast.makeText(context, "ПОБЕДА!", Toast.LENGTH_SHORT);
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length ; i++){
            if(tileList[i].equals(String.valueOf(i))){

                solved = true;
            } else {
                solved = false;
                break;
            }
        }
        if(solved){running = false;}
        return solved;
    }

    public void onBackPressed() {
        if (isSolved()) {
            String title = "Сохранить результат?";
            String posString = "Да";
            String negString = "Нет";
            running = false;
            AlertDialog.Builder ad = new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.AlertDialogCustom));
            ad.setTitle(title);  // заголовок
            ad.setPositiveButton(posString, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    Intent show = new Intent();
                    show.setClass(context, ResultsActivity.class);
                    finish();
                    startActivity(show);

                }
            });
            ad.setNegativeButton(negString, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.cancel();
                    finish();
                }
            });
            ad.setCancelable(false);
            AlertDialog dialog = ad.create();
            dialog.show();
        }
        else {
            running = false;
            String title = "Вы точно хотите выйти? Ваш результат не будет сохранен!";
            String posString = "Да";
            String negString = "Нет";
            AlertDialog.Builder ad = new AlertDialog.Builder(new ContextThemeWrapper(context,R.style.AlertDialogCustom));
            ad.setTitle(title);  // заголовок
            ad.setPositiveButton(posString, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.cancel();
                    finish();

                }
            });
            ad.setNegativeButton(negString, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int arg1) {
                    dialog.cancel();
                    running = true;
                }
            });
            ad.setCancelable(false);
            AlertDialog dialog = ad.create();
            dialog.show();
        }
    }

    public static void moveTiles(Context context, String direction, int position) {

        if (position == 0) {//Левый верхний угол
            if (direction.equals(RIGHT)) {
                swap(context, position, 1);
            } else if (direction.equals(DOWN)) {
                swap(context, position, COLUMNS);
            }
        } else if (position > 0 && position < COLUMNS - 1) {//Верхние ячейки
            if (direction.equals(RIGHT)) {
                swap(context, position, 1);
            } else if (direction.equals(LEFT)) {
                swap(context, position, -1);
            } else if (direction.equals(DOWN)) {
                swap(context, position, COLUMNS);
            }
        } else if (position == COLUMNS - 1) { //Правый верхний угол
            if (direction.equals(LEFT)) {
                swap(context, position, -1);
            } else if (direction.equals(DOWN)) {
                swap(context, position, COLUMNS);
            }
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS
                && position % COLUMNS == 0) { //Левые ячейки
            if (direction.equals(UP)) {
                swap(context, position, -COLUMNS);
            } else if (direction.equals(RIGHT)) {
                swap(context, position, 1);
            } else if (direction.equals(DOWN)) {
                swap(context, position, COLUMNS);
            }
        } else if (position == COLUMNS * 3 - 1 || position == COLUMNS * 4 - 1) { //Правые ячейки И нижний правый угол
            if (direction.equals(UP)) {
                swap(context, position, -COLUMNS);
            } else if (direction.equals(LEFT)) {
                swap(context, position, -1);
            } else if (direction.equals(DOWN)) {
                //только для тех, что станут нижним правым углом
                if (position <= DIMENSIONS - COLUMNS - 1) {
                    swap(context, position, COLUMNS);
                }
            }
        } else if (position == DIMENSIONS - COLUMNS) {//нижний левый угол
            if (direction.equals(UP)) {
                swap(context, position, -COLUMNS);
            } else if (direction.equals(RIGHT)) {
                swap(context, position, 1);
            }
        } else if (position > DIMENSIONS - COLUMNS && position < COLUMNS - 1) {//Нижние ячейки
            if (direction.equals(UP)) {
                swap(context, position, -COLUMNS);
            } else if (direction.equals(RIGHT)) {
                swap(context, position, 1);
            } else if (direction.equals(LEFT)) {
                swap(context, position, -1);
            }
        } else { //центральные
            if (direction.equals(UP)) {
                swap(context, position, -COLUMNS);
            } else if (direction.equals(LEFT)) {
                swap(context, position, -1);
            } else if (direction.equals(RIGHT)) {
                swap(context, position, 1);
            } else if (direction.equals(DOWN)) {
                swap(context, position, COLUMNS);
            }
        }
    }
}





