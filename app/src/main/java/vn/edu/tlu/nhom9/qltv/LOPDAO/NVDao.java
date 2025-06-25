package vn.edu.tlu.nhom9.qltv.LOPDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; // Thêm import này

import vn.edu.tlu.nhom9.qltv.LOPPRODUCT.NhanVien;
import vn.edu.tlu.nhom9.qltv.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class NVDao {
    private SQLiteDatabase sqlite;
    private final CreateData createData;
    private static final String TAG = "NVDao"; // Tag để lọc log cho dễ

    public NVDao(Context context) {
        createData = new CreateData(context);
        sqlite = createData.getWritableDatabase();
    }

    public void OPEN() {
        sqlite = createData.getWritableDatabase();
    }

    public void Close() {
        createData.close();
    }

    public long ADDNV(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put(NhanVien.COL_MANV, nhanVien.getMaNV());
        values.put(NhanVien.COL_TENNV, nhanVien.getHoTen());
        values.put(NhanVien.COL_MK, nhanVien.getMaKhau());
        return sqlite.insert(NhanVien.TB_NAME, null, values);
    }

    public int UPDATE(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        // Không nên cập nhật mã nhân viên, chỉ cập nhật thông tin
        // values.put(NhanVien.COL_MANV, nhanVien.getMaNV());
        values.put(NhanVien.COL_TENNV, nhanVien.getHoTen());
        values.put(NhanVien.COL_MK, nhanVien.getMaKhau());
        return sqlite.update(NhanVien.TB_NAME, values, "maNV=?", new String[]{nhanVien.getMaNV()});
    }

    public int Thaypass(NhanVien nhanVien) {
        ContentValues values = new ContentValues();
        values.put(NhanVien.COL_MK, nhanVien.getMaKhau());
        return sqlite.update(NhanVien.TB_NAME, values, "maNV=?", new String[]{nhanVien.getMaNV()});
    }

    public int DELETE(String mNV) {
        return sqlite.delete(NhanVien.TB_NAME, "maNV=?", new String[]{mNV});
    }

    public NhanVien getId(String maNV) {
        String selectId = "SELECT * FROM " + NhanVien.TB_NAME + " WHERE maNV=?";
        List<NhanVien> list = getdata(selectId, maNV);
        // SỬA LỖI: Kiểm tra xem list có rỗng không trước khi lấy phần tử đầu tiên
        if (list.isEmpty()) {
            return null; // Trả về null nếu không tìm thấy nhân viên
        }
        return list.get(0);
    }

    // getUser gần như giống hệt getId, có thể bạn chỉ cần một trong hai
    public NhanVien getUser(String user) {
        String getuser = "SELECT * FROM " + NhanVien.TB_NAME + " WHERE maNV=?";
        List<NhanVien> list = getdata(getuser, user);
        // SỬA LỖI: Kiểm tra tương tự như trên
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    // check taikhoan
    public int getUserName(String user) {
        String dl = "SELECT maNV FROM " + NhanVien.TB_NAME + " WHERE maNV=? ";
        List<NhanVien> list = getdata(dl, user);
        // Cải tiến: Dùng isEmpty() để code dễ đọc hơn
        if (list.isEmpty()) {
            return -1; // Không tồn tại
        } else {
            return 1; // Tồn tại
        }
    }

    public List<NhanVien> GETNV() {
        String select = "SELECT * FROM " + NhanVien.TB_NAME;
        return getdata(select);
    }

    // PHẦN QUAN TRỌNG NHẤT: ĐÃ SỬA LỖI CURSOR
    private List<NhanVien> getdata(String sql, String... selection) {
        List<NhanVien> list = new ArrayList<>();
        // Sử dụng try-with-resources để đảm bảo Cursor luôn được đóng tự động
        try (Cursor cursor = sqlite.rawQuery(sql, selection)) {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    NhanVien nhanVien = new NhanVien();
                    // Sử dụng getColumnIndexOrThrow để tránh lỗi -1 và phát hiện sai tên cột sớm
                    nhanVien.setMaNV(cursor.getString(cursor.getColumnIndexOrThrow(NhanVien.COL_MANV)));
                    nhanVien.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow(NhanVien.COL_TENNV)));
                    nhanVien.setMaKhau(cursor.getString(cursor.getColumnIndexOrThrow(NhanVien.COL_MK)));
                    list.add(nhanVien);
                }
            }
        } catch (Exception e) {
            // Ghi lại lỗi ra Logcat để dễ dàng gỡ rối khi có vấn đề
            Log.e(TAG, "Lỗi khi lấy dữ liệu NhanVien: " + e.getMessage());
        }
        return list;
    }

    public int getlogin(String user, String pass) {
        String dl = "SELECT * FROM " + NhanVien.TB_NAME + " WHERE maNV=? AND matKhau=?";
        List<NhanVien> list = getdata(dl, user, pass);
        // Cải tiến: Dùng isEmpty()
        if (list.isEmpty()) {
            return -1; // Đăng nhập thất bại
        } else {
            return 1; // Đăng nhập thành công
        }
    }
}