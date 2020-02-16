# Retrofit
A type-safe HTTP client for Android and Java

### Dependencies

```gradle

dependencies{
   
   implementation 'com.squareup.retrofit2:retrofit:2.6.2'
   implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
}

```

### RetrofitClient 

```java

package com.example.retrofitbookapp.apiservice;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String url) {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}

```

### APIUtils 

```java

package com.example.retrofitbookapp.apiservice;

public class APIUtils {

    private APIUtils() {

    }

    private static final String API_URL = "http://10.0.2.2";

    public static final String KEY_BOOK_ID = "id";
    public static final String KEY_BOOK_NAME = "name";
    public static final String KEY_AUTHOR_NAME = "author";
    public static final String KEY_BOOK_SUBJECT = "subject";
    public static final String KEY_BOOK_DESCRIPTION = "description";
    public static final String KEY_BOOK_IMAGE_URL = "images";


    public static ApiServices getApiService() {
        return RetrofitClient.getClient(API_URL).create(ApiServices.class);
    }
}


```

### Model Book 

```java

package com.example.retrofitbookapp.model;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("id")
    private Integer bookId;
    @SerializedName("name")
    private String bookName;
    @SerializedName("author")
    private String authorName;
    @SerializedName("subject")
    private String subjectName;
    @SerializedName("description")
    private String bookDescription;
    @SerializedName("images")
    private String bookImage;

    public Book() {
    }

    public Book(String bookName, String authorName, String subjectName, String bookDescription, String bookImage) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.subjectName = subjectName;
        this.bookDescription = bookDescription;
        this.bookImage = bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }
}


```

### ApiService

```java

package com.example.retrofitbookapp.apiservice;

import com.example.retrofitbookapp.model.Book;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {

    @FormUrlEncoded
    @POST("/volleyapi/v1/insert.php")
    Call<Book> insert(@FieldMap Map<String, String> map);

    @GET("/volleyapi/v1/display.php")
    Call<List<Book>> displays();
}


```

### BookAdapter 

```java

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


```

### AddBooks Activity

```java

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


```

### MainActivity 

```java
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

```

### activity_main.xml

```xml

<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/divider"
    tools:context=".app.MainActivity">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/book_recycler_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"
        android:indeterminate="true"
        android:minWidth="50dp"
        android:minHeight="50dp" />

</RelativeLayout>

```

### activity_add_book.xml

```xml

<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:scrollbars="vertical"
    tools:context=".app.AddBooks">


    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="12dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="22sp"
            android:gravity="center"
            android:text="Add Books Using Retrofit Library"
            android:textSize="22sp"
            android:textStyle="bold" />

        <com.google.android.material.textfield.TextInputLayout
            style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="30dp"
            android:hint="Book Name">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/book_name_edit_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />

        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:hint="Author Name">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/author_name_edit_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />

        </com.google.android.material.textfield.TextInputLayout>


        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="15dp"
            android:layout_marginTop="30dp"
            android:text="Subject"
            android:textStyle="bold" />

        <Spinner
            android:id="@+id/subject_spinner"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="15dp"
            android:layout_marginTop="30dp"
            android:background="@color/divider"
            android:padding="5dp"
            android:entries="@array/subjects" />

        <com.google.android.material.textfield.TextInputLayout
            style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:hint="Description">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/book_description_edit_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />

        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.textfield.TextInputLayout
            style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="20dp"
            android:hint="Image URL">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/image_url_edit_text"
                android:layout_width="match_parent"
                android:layout_height="wrap_content" />

        </com.google.android.material.textfield.TextInputLayout>

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="25dp"
            android:onClick="addBooks"
            android:text="Add Books"
            android:layout_gravity="center"
            android:textStyle="bold" />

    </LinearLayout>


</ScrollView>

```

### book_item_layout.xml

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/secondary_text"
    app:cardCornerRadius="4dp"
    app:cardElevation="4dp"
    app:cardMaxElevation="4dp"
    app:cardPreventCornerOverlap="false"
    app:cardUseCompatPadding="true">


    <RelativeLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/my_relative_layout"
        android:padding="5dp">

        <ImageView
            android:id="@+id/book_image_view"
            android:layout_width="140dp"
            android:layout_height="200dp"
            android:layout_alignParentLeft="true"
            android:layout_gravity="start"
            android:adjustViewBounds="true"
            android:contentDescription="TODO"
            android:src="@drawable/kotlin" />

        <TextView
            android:id="@+id/book_name_text_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="150dp"
            android:layout_marginTop="16dp"
            android:layout_marginBottom="5dp"
            android:fontFamily="@font/roboto_bold"
            android:scrollbars="vertical"
            android:text="Book Name"
            android:textColor="@color/primary_text"
            android:textStyle="bold" />

        <TextView
            android:id="@+id/author_name_text_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="150dp"
            android:layout_marginTop="70dp"
            android:fontFamily="@font/roboto_medium"
            android:text="Author Name"
            android:layout_alignParentRight="true"
            android:textColor="@color/primary_text" />

        <TextView
            android:id="@+id/subject_text_view"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="150dp"
            android:layout_marginTop="95dp"
            android:layout_marginRight="8dp"
            android:layout_alignParentRight="true"
            android:fontFamily="@font/roboto_medium"
            android:text="Subject"
            android:textColor="@color/primary_text" />

    </RelativeLayout>



</androidx.cardview.widget.CardView>

```

### book_detail_item_layout.xml

```xml

<?xml version="1.0" encoding="utf-8"?>
<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@color/secondary_text"
    app:cardCornerRadius="4dp"
    app:cardElevation="3dp">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="8dp">


        <TextView
            android:id="@+id/detail_book_name"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="8dp"
            android:layout_marginTop="10dp"
            android:layout_marginBottom="15dp"
            android:fontFamily="@font/roboto_medium"
            android:text="Product Name"
            android:textColor="@color/primary_text"
            android:textStyle="bold" />


        <ImageView
            android:id="@+id/book_images"
            android:layout_width="200dp"
            android:layout_height="300dp"
            android:contentDescription="TODO"
            android:src="@drawable/kotlin" />

        <View
            android:id="@+id/my_view1"
            android:layout_width="match_parent"
            android:layout_height="2dp"
            android:layout_below="@id/book_images"
            android:layout_marginTop="12dp"
            android:background="@color/primary_dark"
            android:padding="5dp" />

        <TextView
            android:id="@+id/detail_author_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="8dp"
            android:layout_marginTop="12dp"
            android:fontFamily="@font/roboto_medium"
            android:text="Author Name"
            android:textColor="@color/primary_text"
            android:textStyle="bold" />

        <View
            android:id="@+id/my_view2"
            android:layout_width="match_parent"
            android:layout_height="2dp"
            android:layout_below="@id/book_images"
            android:layout_marginTop="12dp"
            android:background="@color/primary_dark"
            android:padding="5dp" />

        <TextView
            android:id="@+id/detail_book_description"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="8dp"
            android:layout_marginTop="12dp"
            android:layout_marginRight="8dp"
            android:fontFamily="@font/roboto_regular"
            android:text="Description"
            android:textColor="@color/primary_text"
            android:textStyle="bold" />

        <View
            android:id="@+id/my_view3"
            android:layout_width="match_parent"
            android:layout_height="2dp"
            android:layout_below="@id/book_images"
            android:layout_marginTop="12dp"
            android:background="@color/primary_dark"
            android:padding="5dp" />

        <TextView
            android:id="@+id/detail_subject_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="right"
            android:layout_marginTop="10dp"
            android:layout_marginRight="8dp"
            android:layout_marginBottom="10dp"
            android:fontFamily="@font/roboto_medium"
            android:text="Subject"
            android:textColor="@color/primary_text"
            android:textStyle="bold" />

    </LinearLayout>

</androidx.cardview.widget.CardView>

```

### App Images

<img src="https://github.com/apptech44/retrofit-book-app/blob/master/retrofit-home-activity.png" 
width="180" height="380" />

<img align="center" src="https://github.com/apptech44/retrofit-book-app/blob/master/retrofit-add-activity.png" 
width="180" height="380" />

<img align="right" src="https://github.com/apptech44/retrofit-book-app/blob/master/retrofit-detail-activity.png" 
width="180" height="380" />
