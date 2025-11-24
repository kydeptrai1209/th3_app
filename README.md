# Ứng dụng Android - 8 Bài Tập Thực Hành

Đây là project Android bao gồm 8 bài tập thực hành về các tính năng cơ bản trong phát triển ứng dụng Android.

## Danh sách các bài tập

### Bài 1 - Danh sách sinh viên
- Hiển thị danh sách 20 sinh viên với FlatList/RecyclerView
- Mỗi item hiển thị: tên, tuổi, lớp
- Nhấn vào item để hiển thị Alert với tên sinh viên

### Bài 2 - Form đăng nhập
- Form với 2 ô nhập: Username và Password
- Nút "Login" với validation
- Hiển thị lỗi nếu bỏ trống
- Hiển thị popup "Đăng nhập thành công" nếu hợp lệ

### Bài 3 - Đồng hồ đếm ngược
- Nhập số giây cần đếm
- Nút Start để bắt đầu đếm ngược
- Hiển thị "Time's up" và rung điện thoại khi hết giờ

### Bài 4 - Ứng dụng To-Do
- Thêm công việc mới
- Đánh dấu hoàn thành
- Sửa tên công việc
- Xóa công việc
- Lưu trữ dữ liệu với AsyncStorage

### Bài 5 - Ứng dụng xem hình ảnh
- Hiển thị grid 3 cột các ảnh từ URL
- Nhấn ảnh để mở modal full-screen
- Vuốt sang trái/phải để đổi ảnh

### Bài 6 - Gọi API thời tiết
- Nhập tên thành phố để tìm kiếm
- Fetch API Open-Meteo (miễn phí, không cần API key)
- Hiển thị: nhiệt độ, độ ẩm, mô tả thời tiết
- Có trạng thái loading và xử lý lỗi

### Bài 7 - App 3 tab với Navigation
- BottomNavigationView với 3 tab: Home, News, Profile
- Mỗi tab là một Fragment riêng
- Tab Home: nhấn item để mở chi tiết, truyền tham số

### Bài 8 - Hiển thị danh sách bài viết
- Lấy danh sách bài viết từ JSONPlaceholder API
- Hiển thị dưới dạng RecyclerView
- Tính năng: Load more (phân trang), Pull to refresh, Tìm kiếm

## Công nghệ sử dụng

- **Language:** Java
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 36
- **Libraries:**
  - AndroidX (AppCompat, RecyclerView, ViewPager2, Navigation)
  - OkHttp - HTTP client
  - Gson - JSON parser
  - Glide - Image loading
  - SwipeRefreshLayout - Pull to refresh

## Cài đặt

1. Clone repository:
```bash
git clone https://github.com/kydeptrai1209/th3_app.git
```

2. Mở project trong Android Studio

3. Sync Gradle và build project:
```
Build → Rebuild Project
```

4. Chạy trên emulator hoặc thiết bị thật

## Yêu cầu hệ thống

- Android Studio Arctic Fox trở lên
- JDK 11 trở lên
- Android SDK 24+

## Tác giả

Phát triển bởi: [kydeptrai1209](https://github.com/kydeptrai1209)

## License

MIT License

