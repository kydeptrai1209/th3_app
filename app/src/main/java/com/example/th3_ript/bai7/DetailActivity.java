package com.example.th3_ript.bai7;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.th3_ript.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");

        TextView tvTitle = findViewById(R.id.tvDetailTitle);
        TextView tvDescription = findViewById(R.id.tvDetailDescription);

        tvTitle.setText(title);
        tvDescription.setText(description);
    }
}

