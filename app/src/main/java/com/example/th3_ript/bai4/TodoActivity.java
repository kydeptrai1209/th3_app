package com.example.th3_ript.bai4;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th3_ript.R;

import java.util.List;
import java.util.UUID;

public class TodoActivity extends AppCompatActivity {

    private EditText editTodo;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private TodoStorage storage;
    private List<TodoItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        editTodo = findViewById(R.id.editTodo);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        storage = new TodoStorage(this);
        items = storage.loadTodos();

        adapter = new TodoAdapter(items, new TodoAdapter.OnTodoListener() {
            @Override
            public void onToggleComplete(int position) {
                TodoItem item = items.get(position);
                item.setCompleted(!item.isCompleted());
                storage.saveTodos(items);
                adapter.notifyItemChanged(position);
            }

            @Override
            public void onEdit(int position) {
                showEditDialog(position);
            }

            @Override
            public void onDelete(int position) {
                items.remove(position);
                storage.saveTodos(items);
                adapter.notifyItemRemoved(position);
                Toast.makeText(TodoActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String title = editTodo.getText().toString().trim();
            if (TextUtils.isEmpty(title)) {
                Toast.makeText(this, "Vui lòng nhập công việc", Toast.LENGTH_SHORT).show();
                return;
            }

            TodoItem newItem = new TodoItem(UUID.randomUUID().toString(), title, false);
            items.add(0, newItem);
            storage.saveTodos(items);
            adapter.notifyItemInserted(0);
            editTodo.setText("");
            recyclerView.smoothScrollToPosition(0);
        });
    }

    private void showEditDialog(int position) {
        TodoItem item = items.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa công việc");

        final EditText input = new EditText(this);
        input.setText(item.getTitle());
        builder.setView(input);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String newTitle = input.getText().toString().trim();
            if (!TextUtils.isEmpty(newTitle)) {
                item.setTitle(newTitle);
                storage.saveTodos(items);
                adapter.notifyItemChanged(position);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    static class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

        interface OnTodoListener {
            void onToggleComplete(int position);
            void onEdit(int position);
            void onDelete(int position);
        }

        private final List<TodoItem> items;
        private final OnTodoListener listener;

        TodoAdapter(List<TodoItem> items, OnTodoListener listener) {
            this.items = items;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_todo, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            TodoItem item = items.get(position);
            holder.tvTitle.setText(item.getTitle());
            holder.checkBox.setChecked(item.isCompleted());

            if (item.isCompleted()) {
                holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() & (~android.graphics.Paint.STRIKE_THRU_TEXT_FLAG));
            }

            holder.checkBox.setOnClickListener(v -> listener.onToggleComplete(position));
            holder.btnEdit.setOnClickListener(v -> listener.onEdit(position));
            holder.btnDelete.setOnClickListener(v -> listener.onDelete(position));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            CheckBox checkBox;
            TextView tvTitle;
            ImageButton btnEdit, btnDelete;

            ViewHolder(View itemView) {
                super(itemView);
                checkBox = itemView.findViewById(R.id.checkBox);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                btnEdit = itemView.findViewById(R.id.btnEdit);
                btnDelete = itemView.findViewById(R.id.btnDelete);
            }
        }
    }
}

