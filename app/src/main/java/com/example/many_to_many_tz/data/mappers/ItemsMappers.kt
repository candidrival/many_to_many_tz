package com.example.many_to_many_tz.data.mappers

import com.example.many_to_many_tz.data.network.responses.AllItemsResponse
import com.example.many_to_many_tz.data.network.responses.ChosenItemResponse
import com.example.many_to_many_tz.data.network.responses.ItemResponse
import com.example.many_to_many_tz.domain.entities.AllItems
import com.example.many_to_many_tz.domain.entities.ChosenItem
import com.example.many_to_many_tz.domain.entities.Item

fun ItemResponse.toItem() = Item(
    id = id,
    name = name,
    image = image ?: "",
    color = color
)

fun AllItemsResponse.toItems() = AllItems(
    title = title,
    items = items.map {
        it.toItem()
    }
)

fun ChosenItemResponse.toItem() = ChosenItem(
    id = id,
    text = text
)