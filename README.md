# Retrofit
A type-safe HTTP client for Android and Java

### Dependencies

```gradle

dependencies{
   
   implementation 'com.squareup.retrofit2:retrofit:2.6.2'
   implementation 'com.squareup.retrofit2:converter-gson:2.6.2'
}

```

### Insert Records

```java

@FormUrlEncoded
@POST("/volleyapi/v1/insert.php")
Call<Book> insert(@FieldMap Map<String, String> map);

```

### Display Records

```java

@GET("/volleyapi/v1/display.php")
Call<List<Book>> displays();

```
