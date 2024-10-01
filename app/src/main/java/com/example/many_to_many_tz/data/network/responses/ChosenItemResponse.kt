package com.example.many_to_many_tz.data.network.responses

import com.google.gson.annotations.SerializedName

data class ChosenItemResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("text")
    val text: String
)
