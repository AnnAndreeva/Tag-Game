package com.example.tag_game;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tag_game.data.MyValues;

import java.util.ArrayList;
import java.util.Random;



public class GameActivity extends AppCompatActivity {

    private static final int COLUMNS = 4;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private static String[] tileList; //список ячеек

    private static GestureDetectGridView mGridView;

    private static int mColumnWidth, mColumnHeight;

    public static final String UP = "up";
    public static final String DOWN = "down";
    public static final String RIGHT = "right";
    public static final String LEFT = "left";

    public static String time;

    private static Context contextSuper;
    private Button start, pause;

    private int seconds = 0;
    private static boolean running = false;
    private static boolean solved = false;

    private static String selected = "";

    //private static ArrayList<Bitmap> pieces;
    private static ArrayList<BitmapDrawable> customPieces;
    Bitmap custom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setTitle("Игра");
        contextSuper = this;
        MyValues.time = "";

        inputNameDialog();

        Bundle arguments = getIntent().getExtras();
        selected = arguments.get("selected").toString();
        if (selected.equals("cat")) {
            customPieces = splitImage(BitmapFactory.decodeResource(getResources(), R.drawable.cat));
        } else if (selected.equals("dog")) {
            customPieces = splitImage(BitmapFactory.decodeResource(getResources(), R.drawable.dog));
        } else if (selected.equals("custom")) {
            Intent intent = getIntent();
            custom = (Bitmap) intent.getParcelableExtra("BitmapImage");
            customPieces = splitImage(custom);
        }
        init();//инициализация списка ячеек

        display(contextSuper);//отображение кнопок

        setDimensions();

        start = findViewById(R.id.btnStart);
        start.setOnClickListener(startGame);
        start.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        pause = findViewById(R.id.btnPause);
        pause.setOnClickListener(pauseGame);
        pause.setClickable(false);

        runTimer();
    }



    private ArrayList<BitmapDrawable> splitImage(Bitmap bitmap) {
        int piecesNumber = 16;
        int rows = 4;
        int cols = 4;

        ArrayList<Bitmap> pieces = new ArrayList<>(piecesNumber);
        ArrayList<BitmapDrawable> customPieces = new ArrayList<>(piecesNumber);
        // Get the bitmap of the source image

        // Calculate the with and height of the pieces
        int pieceWidth = bitmap.getWidth()/cols;
        int pieceHeight = bitmap.getHeight()/rows;

        // Create each bitmap piece and add it to the resulting array
        int yCoord = 0;
        for (int row = 0; row < rows; row++) {
            int xCoord = 0;
            for (int col = 0; col < cols; col++) {
                pieces.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, pieceWidth, pieceHeight));
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),
                        Bitmap.createBitmap(bitmap, xCoord, yCoord, pieceWidth, pieceHeight));

                customPieces.add(bitmapDrawable);
                xCoord += pieceWidth;
            }
            yCoord += pieceHeight;
        }

        return customPieces;
    }

    private void runTimer() {
        final TextView tvTime = findViewById(R.id.tvTime);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int min = (seconds % 3600) / 60;
                int sec = seconds % 60;
                time = String.format("%02d:%02d", min, sec);
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

                display(contextSuper);
            }
        });
    }

    private static void display(Context context)
    { //отображение
        ArrayList<Button> buttons = new ArrayList<>();
        Button button;
        //инициализируем кнопки
        for (int i = 0; i < tileList.length; i++) {
            button = new Button(context);
            if (!running) button.setClickable(false);

            if (tileList[i].equals("0")) {
                button.setBackground(customPieces.get(0));
            } else if (tileList[i].equals("1")) {
                button.setBackground(customPieces.get(1));
            } else if (tileList[i].equals("2")) {
                button.setBackground(customPieces.get(2));
            } else if (tileList[i].equals("3")) {
                button.setBackground(customPieces.get(3));
            } else if (tileList[i].equals("4")) {
                button.setBackground(customPieces.get(4));
            } else if (tileList[i].equals("5")) {
                button.setBackground(customPieces.get(5));
            } else if (tileList[i].equals("6")) {
                button.setBackground(customPieces.get(6));
            } else if (tileList[i].equals("7")) {
                button.setBackground(customPieces.get(7));
            } else if (tileList[i].equals("8")) {
                button.setBackground(customPieces.get(8));
            } else if (tileList[i].equals("9")) {
                button.setBackground(customPieces.get(9));
            } else if (tileList[i].equals("10")) {
                button.setBackground(customPieces.get(10));
            } else if (tileList[i].equals("11")) {
                button.setBackground(customPieces.get(11));
            } else if (tileList[i].equals("12")) {
                button.setBackground(customPieces.get(12));
            } else if (tileList[i].equals("13")) {
                button.setBackground(customPieces.get(13));
            } else if (tileList[i].equals("14")) {
                button.setBackground(customPieces.get(14));
            } else if (tileList[i].equals("15")) {
                button.setBackground(customPieces.get(15));
                button.setVisibility(View.INVISIBLE);
            }
            buttons.add(button);
        }
        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    private void scramble() { //перемешивание ячеек в массиве
        boolean NumIsFree[] = new boolean[15];//NumIsFree[i] показывает, определили ли мы уже позицию i-й костяшки
        boolean Ok; //Флаг, определяющий корректность выбора костяшки для данной позиции
        for (int i = 0; i < 15; i++) //Объявляем, что изначально все костяшки свободны
            NumIsFree[i] = true;

        String temp;
        Random random = new Random();
        int randNum = 0;

        for (int i = 0; i < 15; i++) //Нам надо определить номер костяшки, находящейся в каждой из 15 позиций поля
        {
            Ok = false; //Каждый раз сбрасываем значение флага
            while (!Ok) //Продолжаем случайным образом определять номер костяшки, пока он не окажется корректным
            {
                randNum = random.nextInt(15); //random(n) генерирует псевдослучайное число от 0 до n - 1, а нам нужно от 1 до 15
                if (NumIsFree[randNum]) //Если костяшка с таким номером еще свободна
                    Ok = true; //то мы определили ее номер корректно
            }
            tileList[i] = String.valueOf(randNum); //Записываем этот корректный номер в i-ю позицию
            NumIsFree[randNum] = false; //Костяшка с этим номером теперь занята
        }

        int Chaos = 0; //Количество беспорядков на поле
        int CurrNum; //Костяшка, для которой мы рассматриваем беспорядки
        for (int i = 0; i < 14; i++) //Считаем для костяшек на первых 14 позициях (для 15-й это бессмысленно)
        {
            CurrNum = Integer.parseInt (tileList[i]);
            for (int j = i + 1; j < 15; j++)
                if (CurrNum > Integer.parseInt (tileList[j]))
                    Chaos++;
        }
        if (Chaos % 2 == 1) //Если общее число беспорядков нечетное,
        { //меняем местами костяшки на 14-й и 15-й позициях
            temp = tileList[13];
            tileList[13] = tileList[14];
            tileList[14] = temp;
        }
    }

    private void init() { //инициализация списка ячеек
        mGridView = findViewById(R.id.grid); //(GestureDetectGridView)
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
            v.setVisibility(View.INVISIBLE);
            running = true;
            pause.setClickable(true);
            scramble();//перемешивание ячеек в массиве
            display(contextSuper);

        }
    };

    View.OnClickListener pauseGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            running = false;
            String title = "Игра на паузе.";
            String posString = "Продолжить";
            AlertDialog.Builder ad = new AlertDialog.Builder(new ContextThemeWrapper(contextSuper,R.style.AlertDialogCustom));
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

    private void inputNameDialog(){
        String title = "Здравствуйте! Как вас зовут?";
        //Получаем вид с файла alert_dialog.xml, который применим для диалогового окна:
        LayoutInflater li = LayoutInflater.from(contextSuper);
        View adView = li.inflate(R.layout.alert_dialog, null);

        //Создаем AlertDialog
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(contextSuper,R.style.AlertDialogCustom));

        //Настраиваем prompt.xml для нашего AlertDialog:
        mDialogBuilder.setView(adView);

        //Настраиваем отображение поля для ввода текста в открытом диалоге:
        final EditText userInput = (EditText) adView.findViewById(R.id.input_text);


        //Настраиваем сообщение в диалоговом окне:
        mDialogBuilder
               // .setCancelable(false)
                .setTitle(title)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (userInput.getText().toString().length() == 0){
                                    MyValues.name = "Игрок";
                                } else {
                                    MyValues.name = String.valueOf(userInput.getText());
                                }
                            }
                        });

        //Создаем AlertDialog:
        AlertDialog alertDialog = mDialogBuilder.create();
        if (userInput.getText().toString().length() == 0){
            MyValues.name = "Игрок";
        }
        //и отображаем его:
        alertDialog.show();
    }

    private static void swap(Context context, int position, int swap){
        
        if (running) {
           if (tileList[position + swap].equals("15")) {
                String newPosition = tileList[position + swap];
                tileList[position + swap] = tileList[position];
                tileList[position] = newPosition;
            }
            display(context);

            if (isSolved()) {
                saveResultDialog();
            }
        }
        isSolved();
    }

    private static boolean isSolved() {
        for (int i = 0; i < tileList.length ; i++){
            if(tileList[i].equals(String.valueOf(i))){
                solved = true;
            } else {
                solved = false;
                break;
            }
        }
        if(solved){
            running = false;        }
        return solved;
    }


    private static void saveResultDialog(){
        String title = "ПОБЕДА!\nСохранить результат?";
        String posString = "Да";
        String negString = "Нет";
        running = false;
        AlertDialog.Builder ad = new AlertDialog.Builder(new ContextThemeWrapper(contextSuper,R.style.AlertDialogCustom));
        ad.setTitle(title);  // заголовок
        ad.setPositiveButton(posString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
                Intent res = new Intent();
                res.setClass(contextSuper, ResultsActivity.class);
                MyValues.add = "yes";
                MyValues.time = time;
                contextSuper.startActivity(res);
            }
        });
        ad.setNegativeButton(negString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
                Intent back = new Intent();
                back.setClass(contextSuper, MainActivity.class);
                contextSuper.startActivity(back);
            }
        });
        ad.setCancelable(false);
        AlertDialog dialog = ad.create();
        dialog.show();
    }

    public void onBackPressed() {
        if (!running){
            finish();
        }
        else {
            running = false;
            String title = "Вы точно хотите выйти? Ваш результат не будет сохранен!";
            String posString = "Да";
            String negString = "Нет";
            AlertDialog.Builder ad = new AlertDialog.Builder(new ContextThemeWrapper(contextSuper,R.style.AlertDialogCustom));
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





