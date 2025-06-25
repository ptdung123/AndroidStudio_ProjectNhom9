package vn.edu.tlu.nhom9.qltv.LOPDAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; // Thêm import này

import vn.edu.tlu.nhom9.qltv.LOPPRODUCT.PhieuMuon;
import vn.edu.tlu.nhom9.qltv.SQLopenhelper.CreateData;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDao {
    private final CreateData createData;
    private final SQLiteDatabase liteDatabase;
    private static final String TAG = "PhieuMuonDao"; // Tag để lọc log

    public PhieuMuonDao(Context context) {
        createData = new CreateData(context);
        liteDatabase = createData.getWritableDatabase();
    }

    public long ADDPM(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        values.put(PhieuMuon.COL_NAME_MATVPM, phieuMuon.getMaTVpm());
        values.put(PhieuMuon.COL_NAME_MANVPM, phieuMuon.getMaNVpm());
        values.put(PhieuMuon.COL_NAME_MASPM, phieuMuon.getMaSpm());
        values.put(PhieuMuon.COL_NAME_NGAYMUON, phieuMuon.getNgaymuon());
        values.put(PhieuMuon.COL_NAME_TIENTHUE, phieuMuon.getTienthue());
        values.put(PhieuMuon.COL_NAME_TRASACH, phieuMuon.getTrasach());
        // Sử dụng hằng số tên bảng từ lớp PhieuMuon để đảm bảo tính nhất quán
        return liteDatabase.insert(PhieuMuon.TB_NAME_PM, null, values);
    }

    public int DELETEPM(PhieuMuon maPM) {
        // Nên truyền vào mã PM thay vì cả đối tượng để xóa
        return liteDatabase.delete(PhieuMuon.TB_NAME_PM, "maPM=?", new String[]{String.valueOf(maPM)});
    }

    public int UPDATEPM(PhieuMuon phieuMuon) {
        ContentValues values = new ContentValues();
        // Không nên cập nhật Primary Key (mã phiếu mượn)
        // values.put(PhieuMuon.COL_NAME_MAPM, phieuMuon.getMaPM());
        values.put(PhieuMuon.COL_NAME_MATVPM, phieuMuon.getMaTVpm());
        values.put(PhieuMuon.COL_NAME_MANVPM, phieuMuon.getMaNVpm());
        values.put(PhieuMuon.COL_NAME_MASPM, phieuMuon.getMaSpm());
        values.put(PhieuMuon.COL_NAME_NGAYMUON, phieuMuon.getNgaymuon());
        values.put(PhieuMuon.COL_NAME_TIENTHUE, phieuMuon.getTienthue());
        values.put(PhieuMuon.COL_NAME_TRASACH, phieuMuon.getTrasach());
        return liteDatabase.update(PhieuMuon.TB_NAME_PM, values, "maPM=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    public PhieuMuon getID(String id) {
        String sql = "SELECT * FROM " + PhieuMuon.TB_NAME_PM + " WHERE maPM=?";
        List<PhieuMuon> list = getData(sql, id);
        // SỬA LỖI: Kiểm tra list có rỗng không trước khi lấy phần tử đầu tiên
        if (list.isEmpty()) {
            return null; // Trả về null nếu không tìm thấy
        }
        return list.get(0);
    }

    public List<PhieuMuon> GETPM() {
        String sql = "SELECT * FROM " + PhieuMuon.TB_NAME_PM;
        return getData(sql);
    }

    // PHẦN QUAN TRỌNG NHẤT: ĐÃ SỬA LỖI CURSOR
    private List<PhieuMuon> getData(String sql, String... selectionArgs) {
        List<PhieuMuon> list = new ArrayList<>();
        // Sử dụng try-with-resources để đảm bảo Cursor luôn được đóng tự động
        try (Cursor cursor = liteDatabase.rawQuery(sql, selectionArgs)) {
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    PhieuMuon phieuMuon = new PhieuMuon();
                    // Sử dụng getColumnIndexOrThrow để tránh lỗi -1 và phát hiện sai tên cột sớm
                    // Sử dụng cursor.getInt() cho các cột INTEGER, cursor.getString() cho TEXT
                    phieuMuon.setMaPM(cursor.getInt(cursor.getColumnIndexOrThrow(PhieuMuon.COL_NAME_MAPM)));
                    phieuMuon.setMaNVpm(cursor.getString(cursor.getColumnIndexOrThrow(PhieuMuon.COL_NAME_MANVPM)));
                    phieuMuon.setMaTVpm(cursor.getInt(cursor.getColumnIndexOrThrow(PhieuMuon.COL_NAME_MATVPM)));
                    phieuMuon.setMaSpm(cursor.getInt(cursor.getColumnIndexOrThrow(PhieuMuon.COL_NAME_MASPM)));
                    phieuMuon.setNgaymuon(cursor.getString(cursor.getColumnIndexOrThrow(PhieuMuon.COL_NAME_NGAYMUON)));
                    phieuMuon.setTienthue(cursor.getInt(cursor.getColumnIndexOrThrow(PhieuMuon.COL_NAME_TIENTHUE)));
                    phieuMuon.setTrasach(cursor.getInt(cursor.getColumnIndexOrThrow(PhieuMuon.COL_NAME_TRASACH)));
                    list.add(phieuMuon);
                }
            }
        } catch (Exception e) {
            // Ghi lại lỗi ra Logcat để dễ dàng gỡ rối
            Log.e(TAG, "Lỗi khi lấy dữ liệu PhieuMuon: " + e.getMessage());
        }
        return list;
    }

    // SỬA LỖI & BẢO MẬT: Dùng tham số '?' để tránh SQL Injection và quản lý cursor đúng cách
    public int laygiatritheongay(String tungay, String dengay) {
        int doanhThu = 0;
        String sql = "SELECT SUM(tienThue) FROM " + PhieuMuon.TB_NAME_PM + " WHERE ngaymuon BETWEEN ? AND ?";

        try (Cursor cursor = liteDatabase.rawQuery(sql, new String[]{tungay, dengay})) {
            // Kiểm tra xem cursor có di chuyển tới dòng đầu tiên thành công không
            if (cursor.moveToFirst()) {
                // Lấy giá trị từ cột đầu tiên (index 0)
                doanhThu = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "Lỗi khi tính tổng doanh thu: " + e.getMessage());
        }
        return doanhThu;
    }
}