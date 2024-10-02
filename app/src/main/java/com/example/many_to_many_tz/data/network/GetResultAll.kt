package com.example.many_to_many_tz.data.network

import com.example.many_to_many_tz.domain.entities.AllItems


sealed class GetResultAll {

    data class Success(
        val items: AllItems
    ) : GetResultAll()

    data object Failure : GetResultAll()
}