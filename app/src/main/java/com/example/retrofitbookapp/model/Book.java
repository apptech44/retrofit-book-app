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
