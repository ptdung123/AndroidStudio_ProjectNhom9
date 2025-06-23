package vn.edu.tlu.nhom9.qltv.view.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import vn.edu.tlu.nhom9.qltv.R;
import vn.edu.tlu.nhom9.qltv.controller.BookController;
import vn.edu.tlu.nhom9.qltv.model.data.Book;
import vn.edu.tlu.nhom9.qltv.view.adapter.BookAdapter;

public class BookListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookController bookController;
    private List<Book> bookList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish()); // Nút back

        recyclerView = findViewById(R.id.recycler_view_books);
        FloatingActionButton fab = findViewById(R.id.fab_add_book);

        bookController = new BookController();
        setupRecyclerView();

        // Lấy dữ liệu sách từ Controller
        bookController.fetchBooks(new BookController.BookDataListener() {
            @Override
            public void onDataLoaded(List<Book> books) {
                bookList.clear();
                bookList.addAll(books);
                bookAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(BookListActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(v -> {
            // TODO: Mở màn hình thêm sách mới
            Toast.makeText(this, "Mở màn hình thêm sách", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupRecyclerView() {
        bookAdapter = new BookAdapter(bookList);
        recyclerView.setAdapter(bookAdapter);

        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(int position) {
                Book book = bookList.get(position);
                // TODO: Mở màn hình sửa sách với thông tin của 'book'
                Toast.makeText(BookListActivity.this, "Sửa sách: " + book.getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                Book book = bookList.get(position);
                // TODO: Hiển thị dialog xác nhận xóa
                Toast.makeText(BookListActivity.this, "Xóa sách: " + book.getTenSach(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}