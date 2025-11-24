package com.example.th3_ript.bai1;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th3_ript.R;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Student> students = generateStudents();
        StudentAdapter adapter = new StudentAdapter(students, student -> {
            new AlertDialog.Builder(this)
                    .setTitle("Thông tin sinh viên")
                    .setMessage("Tên: " + student.getName())
                    .setPositiveButton("OK", null)
                    .show();
        });

        recyclerView.setAdapter(adapter);
    }

    private List<Student> generateStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Nguyễn Văn An", 20, "CNTT1"));
        students.add(new Student("Trần Thị Bình", 21, "CNTT2"));
        students.add(new Student("Lê Văn Cường", 22, "CNTT1"));
        students.add(new Student("Phạm Thị Dung", 20, "CNTT3"));
        students.add(new Student("Hoàng Văn Em", 21, "CNTT2"));
        students.add(new Student("Vũ Thị Phương", 22, "CNTT1"));
        students.add(new Student("Đặng Văn Giang", 20, "CNTT3"));
        students.add(new Student("Bùi Thị Hoa", 21, "CNTT2"));
        students.add(new Student("Đinh Văn Inh", 22, "CNTT1"));
        students.add(new Student("Cao Thị Khánh", 20, "CNTT3"));
        students.add(new Student("Mai Văn Long", 21, "CNTT2"));
        students.add(new Student("Lý Thị Mai", 22, "CNTT1"));
        students.add(new Student("Phan Văn Nam", 20, "CNTT3"));
        students.add(new Student("Dương Thị Oanh", 21, "CNTT2"));
        students.add(new Student("Tô Văn Phúc", 22, "CNTT1"));
        students.add(new Student("Võ Thị Quỳnh", 20, "CNTT3"));
        students.add(new Student("Đỗ Văn Rộng", 21, "CNTT2"));
        students.add(new Student("Trương Thị Sương", 22, "CNTT1"));
        students.add(new Student("Lâm Văn Tài", 20, "CNTT3"));
        students.add(new Student("Hồ Thị Uyên", 21, "CNTT2"));
        return students;
    }

    private static class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
        private final List<Student> students;
        private final OnItemClickListener listener;

        interface OnItemClickListener {
            void onItemClick(Student student);
        }

        StudentAdapter(List<Student> students, OnItemClickListener listener) {
            this.students = students;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_student, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Student student = students.get(position);
            holder.tvName.setText(student.getName());
            holder.tvAge.setText("Tuổi: " + student.getAge());
            holder.tvClass.setText("Lớp: " + student.getClassName());
            holder.itemView.setOnClickListener(v -> listener.onItemClick(student));
        }

        @Override
        public int getItemCount() {
            return students.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvAge, tvClass;

            ViewHolder(View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tvName);
                tvAge = itemView.findViewById(R.id.tvAge);
                tvClass = itemView.findViewById(R.id.tvClass);
            }
        }
    }
}

