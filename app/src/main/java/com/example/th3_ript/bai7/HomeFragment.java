package com.example.th3_ript.bai7;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th3_ript.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<HomeItem> items = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            items.add(new HomeItem("Item " + i, "Mô tả cho item " + i));
        }

        HomeAdapter adapter = new HomeAdapter(items, item -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        return view;
    }

    private static class HomeItem {
        private final String title;
        private final String description;

        HomeItem(String title, String description) {
            this.title = title;
            this.description = description;
        }

        String getTitle() {
            return title;
        }

        String getDescription() {
            return description;
        }
    }

    private static class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
        private final List<HomeItem> items;
        private final OnItemClickListener listener;

        interface OnItemClickListener {
            void onItemClick(HomeItem item);
        }

        HomeAdapter(List<HomeItem> items, OnItemClickListener listener) {
            this.items = items;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_2, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            HomeItem item = items.get(position);
            holder.text1.setText(item.getTitle());
            holder.text2.setText(item.getDescription());
            holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView text1, text2;

            ViewHolder(View itemView) {
                super(itemView);
                text1 = itemView.findViewById(android.R.id.text1);
                text2 = itemView.findViewById(android.R.id.text2);
            }
        }
    }
}

