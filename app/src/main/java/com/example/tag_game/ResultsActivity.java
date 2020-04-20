package com.example.tag_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.tag_game.data.DBHelper;
import com.example.tag_game.data.MyValues;

public class ResultsActivity extends AppCompatActivity {

    private Context context;
    private TextView tvName, tvTime;
    private Button btnDel;



    String[] data = {"name", "time"};
    GridView gvMain;
    ArrayAdapter<String> adapter;

    DBHelper dbHelper;

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

        btnDel = findViewById(R.id.btnDel);
        btnDel.setBackgroundResource(R.drawable.trashcan);
        btnDel.setOnClickListener(clearTable);



        // создаем объект для создания и управления версиями БД
        dbHelper = new DBHelper(this);

        //getFromTable();
        if(MyValues.add == "yes"){
          insertInTable();
        }
    }

    public void insertInTable()
    {
        // создаем объект для данных
        ContentValues cv = new ContentValues();

        // получаем данные из полей ввода
        String name = MyValues.name;
        String time = MyValues.time;

        cv.put("name", name);
        cv.put("time", time);

        // подключаемся к БД
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowID = db.insert("mytable", null, cv);
        getFromTable();
    }

    public void getFromTable()
    {
     // делаем запрос всех данных из таблицы mytable, получаем Cursor
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = c.getColumnIndex("id");
            int nameColIndex = c.getColumnIndex("name");
            int timeColIndex = c.getColumnIndex("time");

            do {
                // получаем значения по номерам столбцов и пишем все в row
               addRow(c.getString(nameColIndex), c.getString(timeColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        }
        c.close();

    }

    View.OnClickListener clearTable = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // удаляем все записи
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            int clearCount = db.delete("mytable", null, null);
        }
    };

    public void addRow(String name, String time) {
       /* //Сначала найдем в разметке активити саму таблицу по идентификатору
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        //Создаём экземпляр инфлейтера, который понадобится для создания строки таблицы из шаблона. В качестве контекста у нас используется сама активити
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Создаем строку таблицы, используя шаблон из файла /res/layout/table_row.xml
        TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, null);
        //Находим ячейку для номера дня по идентификатору
        TextView tv = (TextView) tr.getChildAt(0);
        //Обязательно приводим число к строке, иначе оно будет воспринято как идентификатор ресурса
        tv.setText(name);
        //Ищем следующую ячейку и устанавливаем её значение
        tv = (TextView) tr.getChildAt(1);
        tv.setText(time);
        tableLayout.addView(tr); //добавляем созданную строку в таблицу
        */

    }

    public void onBackPressed() {
        dbHelper.close();
        this.finish();
        super.onBackPressed();
    }
}
