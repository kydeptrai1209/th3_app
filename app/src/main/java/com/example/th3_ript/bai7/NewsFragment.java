package com.example.th3_ript.bai7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.th3_ript.R;

public class NewsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        TextView tvNews = view.findViewById(R.id.tvNews);
        tvNews.setText("📰 Tin tức mới nhất\n\n" +
                "• Tin 1: Ứng dụng Android đang phát triển mạnh\n\n" +
                "• Tin 2: Java và Kotlin là hai ngôn ngữ chính\n\n" +
                "• Tin 3: Material Design 3 ra mắt\n\n" +
                "• Tin 4: Jetpack Compose ngày càng phổ biến");

        return view;
    }
}

