package vn.edu.tlu.nhom9.qltv.LOPDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; // Thêm import này

import vn.edu.tlu.nhom9.qltv.LOPPRODUCT.ThanhVien;
import vn.edu.tlu.nhom9.qltv.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDao {
    private final SQLiteDatabase sqLiteDatabase;
    private final CreateData createData;
    private static final String TAG = "ThanhVienDao"; // Tag để lọc log

    public ThanhVienDao(Context context) {
        createData = new CreateData(context);
        sqLiteDatabase = createData.getWritableDatabase();
    }

    public long ADDTV(ThanhVien thanhVien) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ThanhVien.COL_NAME_HOTEN, thanhVien.getHoTenTV());
        contentValues.put(ThanhVien.COL_NAME_NAMSINH, thanhVien.getNamsinhTV());
        return sqLiteDatabase.insert(ThanhVien.TB_NAME, null, contentValues);
    }

    public int UPDATETV(ThanhVien thanhVien) {
        ContentValues values = new ContentValues();
        values.put(ThanhVien.COL_NAME_HOTEN, thanhVien.getHoTenTV());
        values.put(ThanhVien.COL_NAME_NAMSINH, thanhVien.getNamsinhTV());
        return sqLiteDatabase.update(ThanhVien.TB_NAME, values, "maTV=?", new String[]{String.valueOf(thanhVien.getIDTV())});
    }

    public int DELETETV(int maTV) {
        // Nên truyền vào mã (ID) để xóa, thay vì cả đối tượng
        return sqLiteDatabase.delete(ThanhVien.TB_NAME, "maTV=?", new String[]{String.valueOf(maTV)});
    }

    // get tất cả dữ liệu
    public List<ThanhVien> GETTV() {
        String sql = "SELECT * FROM " + ThanhVien.TB_NAME;
        return getdata(sql);
    }

    // get dữ liệu theo id
    public ThanhVien getId(String id) {
        // Sửa lại câu truy vấn, dùng 1 dấu '=' là đủ
        String sql = "SELECT * FROM " + ThanhVien.TB_NAME + " WHERE maTV=?";
        List<ThanhVien> list = getdata(sql, id);
        // SỬA LỖI: Kiểm tra list có rỗng không trước khi lấy phần tử
        if (list.isEmpty()) {
            return null; // Trả về null nếu không tìm thấy
        }
        return list.get(0);
    }

    // PHẦN QUAN TRỌNG NHẤT: ĐÃ SỬA LỖI CURSOR
    private List<ThanhVien> getdata(String sql, String... selectionArgs) {
        List<ThanhVien> list = new ArrayList<>();
        // Sử dụng try-with-resources để đảm bảo Cursor luôn được đóng tự động
        try (Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs)) {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    ThanhVien thanhVien = new ThanhVien();
                    // Sử dụng getColumnIndexOrThrow và các phương thức get đúng kiểu dữ liệu
                    thanhVien.setIDTV(cursor.getInt(cursor.getColumnIndexOrThrow(ThanhVien.COL_NAME_ID)));
                    thanhVien.setHoTenTV(cursor.getString(cursor.getColumnIndexOrThrow(ThanhVien.COL_NAME_HOTEN)));
                    thanhVien.setNamsinhTV(cursor.getString(cursor.getColumnIndexOrThrow(ThanhVien.COL_NAME_NAMSINH)));
                    list.add(thanhVien);
                }
            }
        } catch (Exception e) {
            // Ghi lại lỗi ra Logcat để dễ dàng gỡ rối
            Log.e(TAG, "Lỗi khi lấy dữ liệu ThanhVien: " + e.getMessage());
        }
        return list;
    }
}