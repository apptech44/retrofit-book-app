package com.example.retrofitbookapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofitbookapp.R;
import com.example.retrofitbookapp.apiservice.APIUtils;
import com.example.retrofitbookapp.apiservice.ApiServices;
import com.example.retrofitbookapp.model.Book;
import com.example.retrofitbookapp.ui.BookAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private ApiServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = new ArrayList<>();
        apiServices = APIUtils.getApiService();

        recyclerView = findViewById(R.id.book_recycler_view);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        displayBooks();
    }

    private void displayBooks() {

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Call<List<Book>> call = apiServices.displays();

        call.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {

                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {
                    bookList = response.body();
                    bookAdapter = new BookAdapter(MainActivity.this, bookList);
                    recyclerView.setAdapter(bookAdapter);
                }

            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.setting_menu:
                Toast.makeText(MainActivity.this, "Setting Menu",
                        Toast.LENGTH_LONG).show();
                break;

            case R.id.insert_menu:
                startActivity(new Intent(MainActivity.this, AddBooks.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
