package com.example.r2d.requestresponse

import com.example.r2d.LoginResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


public interface ApiService {

    @POST("login")
    fun loginResponse(@Header("Content-Type") contentType: String, @Header("Cache-Control") cache: String, @Body params: RequestBody): retrofit2.Call<LoginResponseModel>
/*
    @POST("user/registration/")
    fun registerResponse(@Header("Content-Type") contentType: String, @Header("Cache-Control") cache: String, @Body params: RequestBody): retrofit2.Call<RegisterResponseModel>

    @GET("levels/")
    fun getLevelResponse(@Query (Const.KEY_ORGANIZATION_ID) organizationId : String): retrofit2.Call<LevelResponseModel>

    @Multipart
    @POST("addPost/")
    fun postAdd(
        @Part(Const.KEY_TITLE) title: RequestBody,
        @Part(Const.KEY_DESCRIPTION) description: RequestBody,
        @Part(Const.KEY_TYPE) type: RequestBody,
        @Part images: Array<MultipartBody.Part?>,
        @Part video: MultipartBody.Part?,
        @Part(Const.KEY_COMPANY_ID) KEY_COMPANY_ID: RequestBody
    ):retrofit2.Call<PostAddResponseModel>*/


}