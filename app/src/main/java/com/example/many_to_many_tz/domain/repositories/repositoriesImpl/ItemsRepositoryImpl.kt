package com.example.many_to_many_tz.domain.repositories.repositoriesImpl

import com.example.many_to_many_tz.data.mappers.toItem
import com.example.many_to_many_tz.data.mappers.toItems
import com.example.many_to_many_tz.data.network.ItemsApi
import com.example.many_to_many_tz.data.network.responses.AllItemsResponse
import com.example.many_to_many_tz.data.network.responses.ChosenItemResponse
import com.example.many_to_many_tz.domain.entities.AllItems
import com.example.many_to_many_tz.domain.entities.ChosenItem
import com.example.many_to_many_tz.domain.repositories.ItemsRepository
import retrofit2.Response

class ItemsRepositoryImpl(
    private val itemsApi: ItemsApi
) : ItemsRepository {
    override suspend fun getItems(): Response<AllItemsResponse> {
        return itemsApi.getItems()
    }

    override suspend fun getItemFromId(itemId: String): Response<ChosenItemResponse> {
        return itemsApi.getItemFromId(itemId)
    }
}