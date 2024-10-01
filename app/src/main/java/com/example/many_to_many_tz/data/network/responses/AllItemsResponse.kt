package com.example.many_to_many_tz.data.network.responses

import com.google.gson.annotations.SerializedName

data class AllItemsResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("items")
    val items: List<ItemResponse>
)
