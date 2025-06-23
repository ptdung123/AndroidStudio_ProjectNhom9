package vn.edu.tlu.nhom9.qltv.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import vn.edu.tlu.nhom9.qltv.R;
import vn.edu.tlu.nhom9.qltv.model.data.Book;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> bookList;
    private OnItemClickListener listener;

    // Interface để gửi sự kiện click ra bên ngoài (Activity)
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.tvBookId.setText("Mã Sách: " + book.getId());
        holder.tvBookTitle.setText("Tên Sách: " + book.getTenSach());
        holder.tvBookAuthor.setText("Tác giả: " + book.getTacGia());
        holder.tvBookType.setText("Loại sách: " + book.getLoaiSach());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookId, tvBookTitle, tvBookAuthor, tvBookType;
        ImageButton btnEdit, btnDelete;

        public BookViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvBookId = itemView.findViewById(R.id.tv_book_id);
            tvBookTitle = itemView.findViewById(R.id.tv_book_title);
            tvBookAuthor = itemView.findViewById(R.id.tv_book_author);
            tvBookType = itemView.findViewById(R.id.tv_book_type);
            btnEdit = itemView.findViewById(R.id.btn_edit_book);
            btnDelete = itemView.findViewById(R.id.btn_delete_book);

            btnEdit.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onEditClick(getAdapterPosition());
                }
            });

            btnDelete.setOnClickListener(v -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(getAdapterPosition());
                }
            });
        }
    }
}