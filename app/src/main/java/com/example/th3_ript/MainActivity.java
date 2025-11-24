package com.example.th3_ript;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.th3_ript.bai1.StudentListActivity;
import com.example.th3_ript.bai2.LoginActivity;
import com.example.th3_ript.bai3.CountdownActivity;
import com.example.th3_ript.bai4.TodoActivity;
import com.example.th3_ript.bai5.ImageGalleryActivity;
import com.example.th3_ript.bai6.WeatherActivity;
import com.example.th3_ript.bai7.NavigationActivity;
import com.example.th3_ript.bai8.PostListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listMenu);
        String[] exercises = {
            "Bài 1 - Danh sách sinh viên",
            "Bài 2 - Form đăng nhập",
            "Bài 3 - Đồng hồ đếm ngược",
            "Bài 4 - Todo App",
            "Bài 5 - Xem hình ảnh",
            "Bài 6 - API thời tiết",
            "Bài 7 - Navigation 3 Tab",
            "Bài 8 - Danh sách bài viết"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, exercises);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = null;
            switch (position) {
                case 0:
                    intent = new Intent(MainActivity.this, StudentListActivity.class);
                    break;
                case 1:
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    break;
                case 2:
                    intent = new Intent(MainActivity.this, CountdownActivity.class);
                    break;
                case 3:
                    intent = new Intent(MainActivity.this, TodoActivity.class);
                    break;
                case 4:
                    intent = new Intent(MainActivity.this, ImageGalleryActivity.class);
                    break;
                case 5:
                    intent = new Intent(MainActivity.this, WeatherActivity.class);
                    break;
                case 6:
                    intent = new Intent(MainActivity.this, NavigationActivity.class);
                    break;
                case 7:
                    intent = new Intent(MainActivity.this, PostListActivity.class);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        });
    }
}