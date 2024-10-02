package com.example.many_to_many_tz.domain.repositories.repositoriesImpl

import com.example.many_to_many_tz.data.mappers.toItem
import com.example.many_to_many_tz.data.mappers.toItems
import com.example.many_to_many_tz.data.network.GetResultChosen
import com.example.many_to_many_tz.data.network.GetResultAll
import com.example.many_to_many_tz.data.network.ItemsApi
import com.example.many_to_many_tz.domain.repositories.ItemsRepository

class ItemsRepositoryImpl(
    private val itemsApi: ItemsApi
) : ItemsRepository {
    override suspend fun getItems(): GetResultAll {
        return try {
            val items = itemsApi.getItems()
            if (items.isSuccessful) {
                val body = items.body()
                if (body != null)
                    GetResultAll.Success(body.toItems())
                else
                    GetResultAll.Failure
            } else {
                GetResultAll.Failure
            }
        } catch (e: Exception) {
            e.printStackTrace()
            GetResultAll.Failure
        }
    }

    override suspend fun getItemFromId(itemId: String): GetResultChosen {
        return try {
            val items = itemsApi.getItemFromId(itemId)
            if (items.isSuccessful) {
                val body = items.body()
                if (body != null)
                    GetResultChosen.Success(body.toItem())
                else
                    GetResultChosen.Failure
            } else {
                GetResultChosen.Failure
            }
        } catch (e: Exception) {
            e.printStackTrace()
            GetResultChosen.Failure
        }
    }
}