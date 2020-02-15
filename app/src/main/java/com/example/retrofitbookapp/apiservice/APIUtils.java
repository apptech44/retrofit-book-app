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
