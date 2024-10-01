package com.example.many_to_many_tz.data.network.responses

import com.google.gson.annotations.SerializedName


data class ItemResponse (
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("color")
    val color: String
)