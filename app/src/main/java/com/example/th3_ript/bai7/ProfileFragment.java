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

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView tvProfile = view.findViewById(R.id.tvProfile);
        tvProfile.setText("👤 Thông tin cá nhân\n\n" +
                "Tên: Nguyễn Văn A\n" +
                "Email: nguyenvana@example.com\n" +
                "Số điện thoại: 0123456789\n" +
                "Địa chỉ: Hà Nội, Việt Nam");

        return view;
    }
}

