package com.example.melanoma.Rest;

import com.example.melanoma.Model.modelMelanoma;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APInterface {
    @GET("/kanker")
    Observable<List<modelMelanoma>> getMelanoma(@Query("tipe") String data);

}
