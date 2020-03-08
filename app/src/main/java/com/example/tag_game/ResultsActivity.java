package com.example.tag_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tag_game.data.ResultsContract;
import com.example.tag_game.data.ResultsDBHelper;

public class ResultsActivity extends AppCompatActivity {

    private Context context;
    private TextView tvName, tvTime;
    private static String name ="", time ="", add = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        setTitle("Результаты");
        context = this;

        Bundle arguments = getIntent().getExtras();
        name = arguments.get("name").toString();
        time = arguments.get("time").toString();
        add = arguments.get("add").toString();

        tvName = findViewById(R.id.tvName);
        tvName.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        tvTime = findViewById(R.id.tvTime);
        tvTime.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));

        if(add == "yes"){
            addResultToDB(name, time);
        }


    }

    public void addResultToDB(String name, String time) {
        ResultsDBHelper mDbHelper = new ResultsDBHelper(context);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ResultsContract.ResultsTable.COLUMN_NAME, name);
        values.put(ResultsContract.ResultsTable.COLUMN_TIME, time);

        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.insert(ResultsContract.ResultsTable.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при сохранении результата", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Результат сохранён под номером: " + newRowId, Toast.LENGTH_SHORT).show();
            getResultFromDB(db);
        }
    }

    public void getResultFromDB(SQLiteDatabase db) {
        // Зададим условие для выборки - список столбцов
        String[] projection = {
                ResultsContract.ResultsTable._ID,
                ResultsContract.ResultsTable.COLUMN_NAME,
                ResultsContract.ResultsTable.COLUMN_TIME };
        Cursor cursor = db.query(
                ResultsContract.ResultsTable.TABLE_NAME,          // таблица
                projection,                                       // столбцы
                null,                                    // столбцы для условия WHERE
                null,                                // значения для условия WHERE
                null,                                   // Don't group the rows
                null,                                    // Don't filter by row groups
                null);                                  // порядок сортировки
        int idColumnIndex = cursor.getColumnIndex( ResultsContract.ResultsTable._ID);
        int nameColumnIndex = cursor.getColumnIndex( ResultsContract.ResultsTable.COLUMN_NAME);
        int timeColumnIndex = cursor.getColumnIndex( ResultsContract.ResultsTable.COLUMN_TIME);
        // Проходим через все ряды
        while (cursor.moveToNext()) {
            // Используем индекс для получения строки или числа
            int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentTime = cursor.getString(timeColumnIndex);
            // Выводим значения каждого столбца
            addRow(currentName, currentTime);
        }
            // Всегда закрываем курсор после чтения
            cursor.close();
    }
    public void addRow(String name, String time) {
        //Сначала найдем в разметке активити саму таблицу по идентификатору
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        //Создаём экземпляр инфлейтера, который понадобится для создания строки таблицы из шаблона. В качестве контекста у нас используется сама активити
        LayoutInflater inflater = LayoutInflater.from(this);
        //Создаем строку таблицы, используя шаблон из файла /res/layout/table_row.xml
        TableRow tr = (TableRow) inflater.inflate(R.layout.table_row, null);
        //Находим ячейку для номера дня по идентификатору
        TextView tv = (TextView) tr.findViewById(R.id.nameRes);
        //Обязательно приводим число к строке, иначе оно будет воспринято как идентификатор ресурса
        tv.setText(name);
        //Ищем следующую ячейку и устанавливаем её значение
        tv = (TextView) tr.findViewById(R.id.timeRes);
        tv.setText(time);
        tableLayout.addView(tr); //добавляем созданную строку в таблицу
    }
    public void onBackPressed() {
        this.finish();
    }
}
