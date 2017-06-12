package com.example.beaubo.liveat500px.manager.http;


import com.example.beaubo.liveat500px.dao.PhotoItemCollectionDao;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by beaubo on 6/10/2017 AD.
 */

public interface ApiService {

    @POST("list")
    Call<PhotoItemCollectionDao> loadPhotoList();

    @POST("list/after/(id)")
    Call<PhotoItemCollectionDao> loadPhotoListAfterId(@Part("id") int id);


}
