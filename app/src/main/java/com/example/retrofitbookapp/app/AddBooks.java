package com.example.retrofitbookapp.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofitbookapp.R;
import com.example.retrofitbookapp.apiservice.APIUtils;
import com.example.retrofitbookapp.apiservice.ApiServices;
import com.example.retrofitbookapp.model.Book;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBooks extends AppCompatActivity {

    private ApiServices apiServices;
    private TextInputEditText name, author, description, images;
    private Spinner subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_books);

        apiServices = APIUtils.getApiService();

        name = findViewById(R.id.book_name_edit_text);
        author = findViewById(R.id.author_name_edit_text);
        subject = findViewById(R.id.subject_spinner);
        description = findViewById(R.id.book_description_edit_text);
        images = findViewById(R.id.image_url_edit_text);
    }

    private void insertBooks() {

        String book_name = name.getText().toString().trim();
        String author_name = author.getText().toString().trim();
        String subject_name = subject.getSelectedItem().toString();
        String book_description = description.getText().toString().trim();
        String book_image = images.getText().toString().trim();

        if (book_name.isEmpty()) {
            Toast.makeText(AddBooks.this, "enter book name here...",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (author_name.isEmpty()) {
            Toast.makeText(AddBooks.this, "enter author name here...",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (subject_name.isEmpty()) {
            Toast.makeText(AddBooks.this, "enter subject name here...",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (book_description.isEmpty()) {
            Toast.makeText(AddBooks.this, "enter book description here...",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (book_image.isEmpty()) {
            Toast.makeText(AddBooks.this, "enter book image here...",
                    Toast.LENGTH_LONG).show();
            return;
        }

        Map<String, String> map = new HashMap<>();

        map.put(APIUtils.KEY_BOOK_NAME, book_name);
        map.put(APIUtils.KEY_AUTHOR_NAME, author_name);
        map.put(APIUtils.KEY_BOOK_SUBJECT, subject_name);
        map.put(APIUtils.KEY_BOOK_DESCRIPTION, book_description);
        map.put(APIUtils.KEY_BOOK_IMAGE_URL, book_image);

        Call<Book> call = apiServices.insert(map);

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(AddBooks.this, "Insert Record Successfully", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                t.printStackTrace();
            }
        });

        name.setText("");
        author.setText("");
        description.setText("");
        images.setText("");

    }

    public void addBooks(View view) {
        insertBooks();
    }
}
