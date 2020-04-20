package com.example.tag_game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class SelectActivity extends AppCompatActivity {
    private Context context;
    private Button img1Btn, img2Btn, imgCustomBtn;
    private TextView text;

    public String selected = "";

    private final int PIC_GET = 1;
    private final int PIC_CROP = 2;
    private final int PIC_DEFAULT = 3;

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
        imgCustomBtn.setOnClickListener(selectCustomImg);

        imgCustomBtn.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/myFont.ttf"));
    }

    View.OnClickListener selectImg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent start = new Intent();
            start.setClass(context, GameActivity.class);
            switch (v.getId()) {
                case R.id.img1Btn:
                    selected = "cat";
                    break;
                case R.id.img2Btn:
                    selected = "dog";
                    break;
            }
            start.putExtra("selected", selected);
            startActivity(start);
        }
    };
    View.OnClickListener selectCustomImg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selected = "custom";
            //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            //Тип получаемых объектов - image:
            photoPickerIntent.setType("image/*");
            //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
            startActivityForResult(photoPickerIntent, PIC_GET);
        }
    };
    //Обрабатываем результат выбора в галерее:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(selected.equals("custom")) {
            if (resultCode == RESULT_OK) {
                if (requestCode == PIC_GET) {
                    //Получаем URI изображения
                    final Uri imageUri = data.getData();
                    performCrop(imageUri);
                } else if (requestCode == PIC_CROP) {
                    // Получим кадрированное изображение
                    Bundle extras = data.getExtras();
                    Bitmap thePic = extras.getParcelable("data");
                    putImage(thePic);
                }
            }
        }
        else
            if (selected.equals("cat")) {
                putImage(BitmapFactory.decodeResource(getResources(), R.drawable.cat));
            } else if (selected.equals("dog")) {
                putImage(BitmapFactory.decodeResource(getResources(), R.drawable.dog));
            }
    }
  protected void putImage(Bitmap selectedImage){
      Intent intent = new Intent();
      intent.setClass(context, GameActivity.class);
      intent.putExtra("BitmapImage", selectedImage);
      intent.putExtra("selected", selected);
      startActivity(intent);
  }

    private void performCrop(Uri imageUri){
        try {
            // Намерение для кадрирования. Не все устройства поддерживают его
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(imageUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 340);
            cropIntent.putExtra("outputY", 340);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch(ActivityNotFoundException anfe){
            String errorMessage = "Извините, но ваше устройство не поддерживает кадрирование";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}


