package vn.edu.tlu.nhom9.qltv.LOPDAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; // Thêm import này

import vn.edu.tlu.nhom9.qltv.LOPPRODUCT.Sach;
import vn.edu.tlu.nhom9.qltv.LOPPRODUCT.Top;
import vn.edu.tlu.nhom9.qltv.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class Top10Dao {
    private final CreateData createData;
    private final SQLiteDatabase liteDatabase;
    private static final String TAG = "Top10Dao"; // Tag để lọc log

    public Top10Dao(Context context) {
        // Không cần lưu context ở đây nữa vì chúng ta không tạo SachDao
        createData = new CreateData(context);
        liteDatabase = createData.getWritableDatabase();
    }

    public List<Top> GetTop() {
        List<Top> list = new ArrayList<>();

        // CẢI TIẾN: Sử dụng JOIN để kết hợp 2 bảng PhieuMuon và Sach
        // trong một câu truy vấn duy nhất. Cách này hiệu quả hơn rất nhiều.
        String sql = "SELECT s.tenSach, COUNT(pm.maSach) as soLuong " +
                "FROM PhieuMuon pm " +
                "JOIN Sach s ON pm.maSach = s.maSach " +
                "GROUP BY s.tenSach " +
                "ORDER BY soLuong DESC " +
                "LIMIT 10";

        // Sử dụng try-with-resources để đảm bảo Cursor luôn được đóng tự động
        try (Cursor cursor = liteDatabase.rawQuery(sql, null)) {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Top top = new Top();
                    // Lấy dữ liệu trực tiếp từ cursor, không cần gọi SachDao nữa
                    top.tensach = cursor.getString(cursor.getColumnIndexOrThrow("tenSach"));
                    top.soluong = cursor.getInt(cursor.getColumnIndexOrThrow("soLuong"));
                    list.add(top);
                }
            }
        } catch (Exception e) {
            // Ghi lại lỗi ra Logcat để dễ dàng gỡ rối
            Log.e(TAG, "Lỗi khi lấy Top 10 sách: " + e.getMessage());
        }
        return list;
    }
}