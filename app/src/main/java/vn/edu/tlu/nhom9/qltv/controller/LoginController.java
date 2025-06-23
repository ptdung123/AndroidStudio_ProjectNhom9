package vn.edu.tlu.nhom9.qltv.controller;

// ===> BƯỚC 1: ĐẢM BẢO BẠN ĐÃ IMPORT DÒNG NÀY <===
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;

public class LoginController {

    private final FirebaseAuth mAuth;

    public interface LoginListener {
        void onLoginSuccess(String message);
        void onLoginFailure(String error);
    }

    public LoginController() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void handleLogin(String email, String password, final LoginListener listener) {

        // ===> BƯỚC 2: KIỂM TRA DỮ LIỆU ĐẦU VÀO <===

        // 2.1. Kiểm tra email rỗng
        if (email == null || email.trim().isEmpty()) {
            listener.onLoginFailure("Vui lòng nhập email.");
            return; // Dừng lại ngay lập tức
        }

        // 2.2. Kiểm tra định dạng email chuẩn (RẤT QUAN TRỌNG)
        // Dòng code này sẽ chặn tất cả các email sai định dạng
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            listener.onLoginFailure("Địa chỉ email không hợp lệ.");
            return; // Dừng lại ngay lập tức
        }

        // 2.3. Kiểm tra mật khẩu rỗng
        if (password == null || password.trim().isEmpty()) {
            listener.onLoginFailure("Vui lòng nhập mật khẩu.");
            return; // Dừng lại ngay lập tức
        }

        // ===> BƯỚC 3: CHỈ KHI MỌI THỨ HỢP LỆ, MỚI GỌI FIREBASE <===
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Thành công
                        listener.onLoginSuccess("Đăng nhập thành công!");
                    } else {
                        // Thất bại
                        listener.onLoginFailure("Email hoặc mật khẩu không chính xác.");
                    }
                });
    }
}