package com.example.many_to_many_tz.domain.entities


data class AllItems(
    val title: String,
    val items: List<Item>
)
