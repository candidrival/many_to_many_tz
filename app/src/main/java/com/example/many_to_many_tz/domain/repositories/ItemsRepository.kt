package com.example.many_to_many_tz.domain.repositories

import com.example.many_to_many_tz.data.network.GetResultChosen
import com.example.many_to_many_tz.data.network.GetResultAll

interface ItemsRepository {
    suspend fun getItems(): GetResultAll
    suspend fun getItemFromId(itemId: String): GetResultChosen
}