package com.example.many_to_many_tz.domain.repositories

import com.example.many_to_many_tz.data.network.responses.AllItemsResponse
import com.example.many_to_many_tz.data.network.responses.ChosenItemResponse
import com.example.many_to_many_tz.domain.entities.AllItems
import com.example.many_to_many_tz.domain.entities.ChosenItem
import retrofit2.Response

interface ItemsRepository {
    suspend fun getItems(): Response<AllItemsResponse>
    suspend fun getItemFromId(itemId: String): Response<ChosenItemResponse>
}