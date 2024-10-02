package com.example.many_to_many_tz.presentation.all_items

import androidx.lifecycle.ViewModel
import com.example.many_to_many_tz.data.network.GetResultAll
import com.example.many_to_many_tz.domain.entities.AllItems
import com.example.many_to_many_tz.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AllItemsViewModel(
    private val repository: ItemsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AllItemsScreenState>(Initial)
    val state = _state.asStateFlow()

    suspend fun getItems() {
        _state.value = Initial
        when (val result = repository.getItems()) {
            GetResultAll.Failure -> _state.value = Error
            is GetResultAll.Success -> _state.value = Downloaded(result.items)
        }
    }

    sealed interface AllItemsScreenState
    data object Initial : AllItemsScreenState
    data class Downloaded(val items: AllItems) :
        AllItemsScreenState

    data object Error : AllItemsScreenState
}