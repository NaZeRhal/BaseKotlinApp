package com.example.basekotlinapp.data.api

import com.example.basekotlinapp.model.ItemModel
import retrofit2.Response
import retrofit2.http.*

interface ItemRestService {

    @GET("items")
    suspend fun fetchItems(): Response<List<ItemModel>>

    @GET("items/{id}")
    suspend fun fetchItemById(@Path("id") id: String): Response<ItemModel>

    @POST("items")
    suspend fun addItem(itemModel: ItemModel): Response<String>

    @PUT("items/{id}")
    suspend fun update(
        @Path("id") id: String,
        @Body itemModel: ItemModel
    ): Response<ItemModel>

    @DELETE("items/{id}")
    suspend fun deleteItemById(@Path("id") id: String): Response<ItemModel>
}