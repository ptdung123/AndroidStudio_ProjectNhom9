package vn.edu.tlu.nhom9.qltv.model.data;

public class Book {
    private String id;
    private String tenSach;
    private String tacGia;
    private String loaiSach;

    public Book(String id, String tenSach, String tacGia, String loaiSach) {
        this.id = id;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.loaiSach = loaiSach;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTenSach() {
        return tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public String getLoaiSach() {
        return loaiSach;
    }
}