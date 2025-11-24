package com.example.th3_ript.bai3;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.th3_ript.R;

public class CountdownActivity extends AppCompatActivity {

    private EditText etSeconds;
    private Button btnStart, btnStop, btnReset;
    private TextView tvCountdown, tvStatus;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis = 0;
    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        etSeconds = findViewById(R.id.etSeconds);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnReset = findViewById(R.id.btnReset);
        tvCountdown = findViewById(R.id.tvCountdown);
        tvStatus = findViewById(R.id.tvStatus);

        btnStart.setOnClickListener(v -> startCountdown());
        btnStop.setOnClickListener(v -> stopCountdown());
        btnReset.setOnClickListener(v -> resetCountdown());

        updateUI();
    }

    private void startCountdown() {
        if (isRunning) {
            Toast.makeText(this, "Đồng hồ đang chạy!", Toast.LENGTH_SHORT).show();
            return;
        }

        String input = etSeconds.getText().toString().trim();
        if (TextUtils.isEmpty(input)) {
            etSeconds.setError("Vui lòng nhập số giây");
            return;
        }

        try {
            int seconds = Integer.parseInt(input);
            if (seconds <= 0) {
                etSeconds.setError("Số giây phải lớn hơn 0");
                return;
            }

            timeLeftInMillis = seconds * 1000L;
            isRunning = true;
            tvStatus.setText("Đang đếm ngược...");

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateCountdownText();
                }

                @Override
                public void onFinish() {
                    isRunning = false;
                    tvCountdown.setText("00:00");
                    tvStatus.setText("Time's up!");

                    // Rung điện thoại
                    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        vibrator.vibrate(1000); // Rung 1 giây
                    }

                    Toast.makeText(CountdownActivity.this, "Time's up!", Toast.LENGTH_LONG).show();
                    updateUI();
                }
            }.start();

            updateUI();
        } catch (NumberFormatException e) {
            etSeconds.setError("Vui lòng nhập số hợp lệ");
        }
    }

    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
        tvStatus.setText("Đã dừng");
        updateUI();
    }

    private void resetCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        isRunning = false;
        timeLeftInMillis = 0;
        tvCountdown.setText("00:00");
        tvStatus.setText("Nhập số giây và bấm Start");
        etSeconds.setText("");
        updateUI();
    }

    private void updateCountdownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;
        String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        tvCountdown.setText(timeFormatted);
    }

    private void updateUI() {
        btnStart.setEnabled(!isRunning);
        btnStop.setEnabled(isRunning);
        etSeconds.setEnabled(!isRunning);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}

