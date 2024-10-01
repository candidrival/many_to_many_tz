package com.example.many_to_many_tz.di

import com.example.many_to_many_tz.data.network.ItemsApi
import com.example.many_to_many_tz.data.network.RetrofitBuilder
import com.example.many_to_many_tz.domain.repositories.ItemsRepository
import com.example.many_to_many_tz.domain.repositories.repositoriesImpl.ItemsRepositoryImpl
import com.example.many_to_many_tz.presentation.all_items.AllItemsViewModel
import com.example.many_to_many_tz.presentation.chosen_item.ChosenItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule
    get() = module {
        viewModel { AllItemsViewModel(get()) }
        viewModel { ChosenItemViewModel(get()) }

        single {
            RetrofitBuilder(
                ItemsApi::class.java,
                "https://test-task-server.mediolanum.f17y.com/"
            ).build()
        }

        single<ItemsRepository> { ItemsRepositoryImpl(get()) }
    }