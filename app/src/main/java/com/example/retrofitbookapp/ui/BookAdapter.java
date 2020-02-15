package com.example.retrofitbookapp.ui;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitbookapp.R;
import com.example.retrofitbookapp.model.Book;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    private final Context context;
    private final List<Book> bookList;
    private final LayoutInflater inflater;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.book_item_layout, null);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {

        Book book = bookList.get(position);

        holder.names.setText(book.getBookName());
        holder.authors.setText(book.getAuthorName());
        holder.subjects.setText(book.getSubjectName());

        Picasso.get().load(book.getBookImage())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .resize(140, 180)
                .into(holder.images);

        holder.setListener(position);

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {

        private TextView names, authors, subjects;
        private ImageView images;

        public BookHolder(@NonNull View itemView) {
            super(itemView);

            names = itemView.findViewById(R.id.book_name_text_view);
            authors = itemView.findViewById(R.id.author_name_text_view);
            subjects = itemView.findViewById(R.id.subject_text_view);
            images = itemView.findViewById(R.id.book_image_view);

        }

        private void setListener(final Integer position) {

            names.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Book book = bookList.get(position);

                    Dialog dialog = new Dialog(context, R.style.appDialog);
                    dialog.setContentView(R.layout.book_detail_item_layout);
                    dialog.setCanceledOnTouchOutside(true);

                    TextView book_name = dialog.findViewById(R.id.detail_book_name);
                    TextView author_name = dialog.findViewById(R.id.detail_author_name);
                    TextView subject_name = dialog.findViewById(R.id.detail_subject_name);
                    TextView book_description = dialog.findViewById(R.id.detail_book_description);
                    book_description.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                    ImageView book_image = dialog.findViewById(R.id.book_images);

                    book_name.setText(book.getBookName());
                    author_name.setText(book.getAuthorName());
                    subject_name.setText(book.getSubjectName());
                    book_description.setText(book.getBookDescription());

                    Picasso.get().load(book.getBookImage())
                            .placeholder(R.drawable.ic_launcher_background)
                            .error(R.drawable.ic_launcher_background)
                            .into(book_image);

                    dialog.show();

                }

            });

        }
    }
}
