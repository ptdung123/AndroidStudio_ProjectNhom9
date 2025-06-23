package vn.edu.tlu.nhom9.qltv.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import vn.edu.tlu.nhom9.qltv.R;
import vn.edu.tlu.nhom9.qltv.view.activity.BookListActivity;
import vn.edu.tlu.nhom9.qltv.view.activity.LoginActivity;

public class MainController {

    private final FirebaseAuth mAuth;

    public MainController() {
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * Phương thức điều hướng đến các màn hình chức năng khác nhau.
     * @param context Context của Activity hiện tại, cần thiết để tạo Intent.
     * @param featureId ID của chuỗi string đại diện cho chức năng (ví dụ: R.string.quan_ly_sach).
     */
    public void navigateToFeature(Context context, int featureId) {
        Intent intent = null;

        if (featureId == R.string.quan_ly_sach) {
            // Nếu người dùng chọn "Quản Lý Sách", tạo Intent để mở BookListActivity.
            intent = new Intent(context, BookListActivity.class);
        } else if (featureId == R.string.quan_ly_phieu_muon) {
            // TODO: Tạo Intent để mở màn hình Quản Lý Phiếu Mượn khi đã có
            // intent = new Intent(context, BorrowTicketActivity.class);
            Toast.makeText(context, "Chức năng 'Quản Lý Phiếu Mượn' đang được phát triển.", Toast.LENGTH_SHORT).show();
        } else if (featureId == R.string.quan_ly_doc_gia) {
            // TODO: Tạo Intent để mở màn hình Quản Lý Độc Giả khi đã có
            // intent = new Intent(context, MemberListActivity.class);
            Toast.makeText(context, "Chức năng 'Quản Lý Độc Giả' đang được phát triển.", Toast.LENGTH_SHORT).show();
        } else if (featureId == R.string.thong_ke) {
            // TODO: Tạo Intent để mở màn hình Thống Kê khi đã có
            // intent = new Intent(context, StatisticsActivity.class);
            Toast.makeText(context, "Chức năng 'Thống Kê' đang được phát triển.", Toast.LENGTH_SHORT).show();
        }

        // Nếu có một Intent hợp lệ được tạo, hãy khởi chạy nó.
        if (intent != null) {
            context.startActivity(intent);
        }
    }

    /**
     * Xử lý việc đăng xuất người dùng.
     * @param currentActivity Activity hiện tại, cần để thực hiện việc chuyển màn hình và finish().
     */
    public void handleLogout(Activity currentActivity) {
        mAuth.signOut();
        // Tạo Intent để quay về màn hình Login
        Intent intent = new Intent(currentActivity, LoginActivity.class);
        // Xóa tất cả các Activity cũ khỏi stack để người dùng không thể bấm "back" quay lại
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        currentActivity.startActivity(intent);
        currentActivity.finish(); // Đóng Activity hiện tại
    }
}