package com.example.wallpaperapp.API;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;

public interface Service {

    //Post Request & Get Response AS String
    @POST(" ")
    Call<String> postRequest(@Body RequestBody Body);

    @POST
    Call<String> postRequestWithUrl(@Url String url, @Body RequestBody Body);


    @POST("/oauth/token")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    Call<String> postRequestGetOathToken(@Header("Authorization") String Authorization, @Body RequestBody Body);

    @POST
    Call<String> postRequestWithUrlWithAuth(@Header("Authorization") String Authorization, @Url String url, @Body RequestBody Body);

    @PUT
    Call<String> putRequestWithUrlWithAuth(@Header("Authorization") String Authorization, @Url String url, @Body RequestBody Body);

    @PATCH
    Call<String> patchRequestWithUrlWithAuth(@Header("Authorization") String Authorization, @Url String url, @Body RequestBody Body);

    @GET
    Call<String> getRequestWithUrlWithAuthANdBody(@Header("Authorization") String Authorization, @Url String url, @Body RequestBody Body);

    @GET
    Call<String> getRequestWithUrlWithAuth(@Header("Authorization") String Authorization, @Url String url);

    @GET
    Call<String> getRequestWithUrlWithoutAuth(@Url String url);

    //Get getRequestResponse AS String
    @GET(" ")
    Call<String> getRequestResponse();

    // previous code for single description
    @Multipart
    @POST
    Call<String> uploadFile(
            @Header("Authorization") String Authorization,
            @Url String url,
            @Part("idNumber") RequestBody idNumber,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part MultipartBody.Part file);

    @Multipart
    @Headers({"Accept: application/json"})
    @POST
    Call<String> save(
            @Header("Authorization") String authorization,
            @Url String url,
            @PartMap Map<String, RequestBody> map,
            @Part MultipartBody.Part file);

    @Multipart
    @POST
    Call<ResponseBody> savePdf(
            @Part MultipartBody.Part bookFile,
            @Part("title") RequestBody title
    );
}