package vn.edu.tlu.nhom9.qltv.controller;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tlu.nhom9.qltv.model.data.Book;

public class BookController {

    // Interface để trả dữ liệu về cho View
    public interface BookDataListener {
        void onDataLoaded(List<Book> books);
        void onError(String message);
    }

    // Tạm thời tạo dữ liệu giả (dummy data)
    // Sau này bạn sẽ thay thế bằng cách gọi Firebase Realtime/Firestore
    public void fetchBooks(BookDataListener listener) {
        List<Book> dummyBooks = new ArrayList<>();
        dummyBooks.add(new Book("001", "Đắc Nhân Tâm", "Dale Carnegie", "Kỹ Năng Sống"));
        dummyBooks.add(new Book("002", "Nhà Giả Kim", "Paulo Coelho", "Tiểu thuyết"));
        dummyBooks.add(new Book("003", "Lịch sử loài người", "Yuval Noah Harari", "Lịch sử – Triết học"));
        dummyBooks.add(new Book("004", "7 Thói Quen Hiệu Quả", "Stephen R. Covey", "Kỹ Năng Sống"));
        dummyBooks.add(new Book("005", "Những Người Khốn Khổ", "Victor Hugo", "Tiểu Thuyết"));

        if (listener != null) {
            listener.onDataLoaded(dummyBooks);
        }
    }

    // Các hàm thêm, sửa, xóa sẽ được viết ở đây
}