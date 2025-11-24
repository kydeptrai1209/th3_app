package com.example.th3_ript.bai5;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.th3_ript.R;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> imageUrls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_gallery);

        recyclerView = findViewById(R.id.recyclerViewImages);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        imageUrls = generateImageUrls();
        ImageAdapter adapter = new ImageAdapter(imageUrls, position -> showFullScreenImage(position));
        recyclerView.setAdapter(adapter);
    }

    private List<String> generateImageUrls() {
        List<String> urls = new ArrayList<>();
        // Sử dụng picsum.photos để lấy ảnh mẫu
        for (int i = 1; i <= 30; i++) {
            urls.add("https://picsum.photos/400/400?random=" + i);
        }
        return urls;
    }

    private void showFullScreenImage(int position) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_fullscreen_image);

        ViewPager2 viewPager = dialog.findViewById(R.id.viewPagerImages);
        ImageView btnClose = dialog.findViewById(R.id.btnClose);

        FullScreenImageAdapter adapter = new FullScreenImageAdapter(imageUrls);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position, false);

        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        private final List<String> imageUrls;
        private final OnItemClickListener listener;

        interface OnItemClickListener {
            void onItemClick(int position);
        }

        ImageAdapter(List<String> imageUrls, OnItemClickListener listener) {
            this.imageUrls = imageUrls;
            this.listener = listener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_grid, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String url = imageUrls.get(position);
            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.imageView);

            holder.itemView.setOnClickListener(v -> listener.onItemClick(position));
        }

        @Override
        public int getItemCount() {
            return imageUrls.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
            }
        }
    }

    private static class FullScreenImageAdapter extends RecyclerView.Adapter<FullScreenImageAdapter.ViewHolder> {
        private final List<String> imageUrls;

        FullScreenImageAdapter(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_image_fullscreen, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            String url = imageUrls.get(position);
            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return imageUrls.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageViewFull);
            }
        }
    }
}

