package com.example.th3_ript.bai8;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.th3_ript.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private EditText etSearch;
    private PostAdapter adapter;
    private final List<Post> allPosts = new ArrayList<>();
    private final List<Post> filteredPosts = new ArrayList<>();
    private final List<Post> displayedPosts = new ArrayList<>();
    private static final int PAGE_SIZE = 10;
    private boolean isLoading = false;
    private String currentSearchQuery = "";
    private static final String API_URL = "https://jsonplaceholder.typicode.com/posts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        recyclerView = findViewById(R.id.recyclerViewPosts);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        progressBar = findViewById(R.id.progressBar);
        etSearch = findViewById(R.id.etSearch);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PostAdapter(displayedPosts);
        recyclerView.setAdapter(adapter);

        // Swipe to refresh
        swipeRefreshLayout.setOnRefreshListener(() -> {
            displayedPosts.clear();
            loadPosts(true);
        });

        // Scroll listener for load more
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) { // Chỉ load khi scroll xuống
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (layoutManager != null && !isLoading) {
                        int visibleItemCount = layoutManager.getChildCount();
                        int totalItemCount = layoutManager.getItemCount();
                        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount - 2
                                && firstVisibleItemPosition >= 0
                                && displayedPosts.size() < filteredPosts.size()) {
                            loadMorePosts();
                        }
                    }
                }
            }
        });

        // Search functionality
        etSearch.addTextChangedListener(new TextWatcher() {
            private final Handler handler = new Handler(Looper.getMainLooper());
            private Runnable searchRunnable;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchRunnable != null) {
                    handler.removeCallbacks(searchRunnable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchRunnable = () -> filterPosts(s.toString());
                handler.postDelayed(searchRunnable, 300); // Debounce 300ms
            }
        });

        loadPosts(false);
    }

    private void loadPosts(boolean isRefresh) {
        isLoading = true;
        if (!isRefresh) {
            progressBar.setVisibility(View.VISIBLE);
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(API_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(() -> {
                    isLoading = false;
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(PostListActivity.this, "Lỗi tải dữ liệu", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Post>>(){}.getType();
                    List<Post> posts = gson.fromJson(responseData, listType);

                    runOnUiThread(() -> {
                        allPosts.clear();
                        allPosts.addAll(posts);
                        filterPosts(etSearch.getText().toString());
                        isLoading = false;
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                    });
                }
            }
        });
    }

    private void loadMorePosts() {
        if (isLoading || displayedPosts.size() >= filteredPosts.size()) {
            if (displayedPosts.size() >= filteredPosts.size()) {
                Toast.makeText(this, "Đã tải hết bài viết", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        isLoading = true;

        // Tính toán số lượng posts cần thêm
        int currentSize = displayedPosts.size();
        int totalSize = filteredPosts.size();
        int toIndex = Math.min(currentSize + PAGE_SIZE, totalSize);

        // Thêm posts mới vào displayed list
        List<Post> newPosts = filteredPosts.subList(currentSize, toIndex);
        int oldSize = displayedPosts.size();
        displayedPosts.addAll(newPosts);

        adapter.notifyItemRangeInserted(oldSize, newPosts.size());
        isLoading = false;

        Toast.makeText(this, "Đã tải thêm " + newPosts.size() + " bài viết", Toast.LENGTH_SHORT).show();
    }

    private void filterPosts(String query) {
        currentSearchQuery = query;
        filteredPosts.clear();
        displayedPosts.clear();

        if (query.isEmpty()) {
            filteredPosts.addAll(allPosts);
        } else {
            String lowerQuery = query.toLowerCase();
            for (Post post : allPosts) {
                if (post.getTitle().toLowerCase().contains(lowerQuery) ||
                    post.getBody().toLowerCase().contains(lowerQuery)) {
                    filteredPosts.add(post);
                }
            }
        }

        // Load trang đầu tiên
        int toIndex = Math.min(PAGE_SIZE, filteredPosts.size());
        if (toIndex > 0) {
            displayedPosts.addAll(filteredPosts.subList(0, toIndex));
        }

        adapter.notifyDataSetChanged();

        if (!query.isEmpty()) {
            Toast.makeText(this, "Tìm thấy " + filteredPosts.size() + " bài viết", Toast.LENGTH_SHORT).show();
        }
    }

    private static class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
        private final List<Post> posts;

        PostAdapter(List<Post> posts) {
            this.posts = posts;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_post, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Post post = posts.get(position);
            holder.tvTitle.setText(post.getTitle());
            holder.tvBody.setText(post.getBody());
            holder.tvId.setText("#" + post.getId());
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvId, tvTitle, tvBody;

            ViewHolder(View itemView) {
                super(itemView);
                tvId = itemView.findViewById(R.id.tvPostId);
                tvTitle = itemView.findViewById(R.id.tvPostTitle);
                tvBody = itemView.findViewById(R.id.tvPostBody);
            }
        }
    }
}

