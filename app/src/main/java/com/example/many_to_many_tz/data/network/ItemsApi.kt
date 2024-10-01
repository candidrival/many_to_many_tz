package com.example.many_to_many_tz.data.network

import com.example.many_to_many_tz.data.network.responses.AllItemsResponse
import com.example.many_to_many_tz.data.network.responses.ChosenItemResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Part
import retrofit2.http.Path

interface ItemsApi {
    @Headers(
        "Content-Type: application/json"
    )
    @GET("items/random")
    suspend fun getItems() : Response<AllItemsResponse>

    @Headers(
        "Content-Type: application/json"
    )
    @GET("texts/{itemId}")
    suspend fun getItemFromId(
        @Path("itemId") itemId: String
    ) : Response<ChosenItemResponse>
}