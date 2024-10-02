package com.example.many_to_many_tz.data.network

import com.example.many_to_many_tz.domain.entities.ChosenItem


sealed class GetResultChosen {
    data class Success(
        val item: ChosenItem
    ) : GetResultChosen()

    data object Failure : GetResultChosen()
}