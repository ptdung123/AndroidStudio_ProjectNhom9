package vn.edu.tlu.nhom9.qltv.LOPDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; // Thêm import này

import vn.edu.tlu.nhom9.qltv.LOPPRODUCT.Sach;
import vn.edu.tlu.nhom9.qltv.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    private final SQLiteDatabase sqLiteDatabase;
    private final CreateData createData;
    private static final String TAG = "SachDao"; // Tag để lọc log

    public SachDao(Context context) {
        createData = new CreateData(context);
        sqLiteDatabase = createData.getWritableDatabase();
    }

    public long ADDS(Sach sach) {
        ContentValues values = new ContentValues();
        values.put(Sach.COL_NAME_MALS, sach.getMals());
        values.put(Sach.COL_NAME_TENS, sach.getTens());
        values.put(Sach.COL_NAME_GIAS, sach.getGias());
        values.put(Sach.COL_NAME_tacgia, sach.getTacgia());
        return sqLiteDatabase.insert(Sach.TB_NAME, null, values);
    }

    public int DELETES(Sach maSach) {
        // Nên truyền vào mã sách (primary key) để xóa, thay vì cả đối tượng
        return sqLiteDatabase.delete(Sach.TB_NAME, "maSach=?", new String[]{String.valueOf(maSach)});
    }

    public int UPDATES(Sach sach) {
        ContentValues values = new ContentValues();
        // Không nên cập nhật khóa chính (maSach)
        values.put(Sach.COL_NAME_MALS, sach.getMals());
        values.put(Sach.COL_NAME_TENS, sach.getTens());
        values.put(Sach.COL_NAME_GIAS, sach.getGias());
        values.put(Sach.COL_NAME_tacgia, sach.getTacgia());
        return sqLiteDatabase.update(Sach.TB_NAME, values, "maSach=?", new String[]{String.valueOf(sach.getMas())});
    }

    public List<Sach> GETS() {
        String sql = "SELECT * FROM " + Sach.TB_NAME;
        // Cải tiến: Trả về trực tiếp, không cần biến trung gian
        return getdata(sql);
    }

    public Sach getId(String id) {
        String sql = "SELECT * FROM " + Sach.TB_NAME + " WHERE maSach=?";
        List<Sach> list = getdata(sql, id);
        // SỬA LỖI: Kiểm tra list có rỗng không trước khi lấy phần tử đầu tiên
        if (list.isEmpty()) {
            return null; // Trả về null nếu không tìm thấy sách
        }
        return list.get(0);
    }

    // PHẦN QUAN TRỌNG NHẤT: ĐÃ SỬA LỖI CURSOR
    private List<Sach> getdata(String sql, String... selectionArgs) {
        List<Sach> list = new ArrayList<>();
        // Sử dụng try-with-resources để đảm bảo Cursor luôn được đóng tự động
        try (Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs)) {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    Sach sach = new Sach();
                    // Sử dụng getColumnIndexOrThrow và các phương thức get đúng kiểu dữ liệu
                    sach.setMas(cursor.getInt(cursor.getColumnIndexOrThrow(Sach.COL_NAME_MAS)));
                    sach.setMals(cursor.getInt(cursor.getColumnIndexOrThrow(Sach.COL_NAME_MALS)));
                    sach.setTens(cursor.getString(cursor.getColumnIndexOrThrow(Sach.COL_NAME_TENS)));
                    sach.setTacgia(cursor.getString(cursor.getColumnIndexOrThrow(Sach.COL_NAME_tacgia)));
                    sach.setGias(cursor.getInt(cursor.getColumnIndexOrThrow(Sach.COL_NAME_GIAS)));
                    list.add(sach);
                }
            }
        } catch (Exception e) {
            // Ghi lại lỗi ra Logcat để dễ dàng gỡ rối
            Log.e(TAG, "Lỗi khi lấy dữ liệu Sach: " + e.getMessage());
        }
        return list;
    }
}