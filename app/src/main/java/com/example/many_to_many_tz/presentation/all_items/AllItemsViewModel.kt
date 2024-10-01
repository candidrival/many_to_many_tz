package com.example.many_to_many_tz.presentation.all_items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.many_to_many_tz.data.mappers.toItems
import com.example.many_to_many_tz.domain.entities.AllItems
import com.example.many_to_many_tz.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllItemsViewModel(
    private val repository: ItemsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AllItemsScreenState>(Initial)
    val state = _state.asStateFlow()

    suspend fun getItems() {
        _state.value = Initial
        try {
            val execute = repository.getItems()
            if (execute.isSuccessful) {
                val body = execute.body()
                if (body != null)
                    _state.value = Downloaded(body.toItems())
                else
                    _state.value = Error
            } else {
                _state.value = Error
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _state.value = Error
        }
    }

    sealed interface AllItemsScreenState
    data object Initial : AllItemsScreenState
    data class Downloaded(val items: AllItems) :
        AllItemsScreenState

    data object Error : AllItemsScreenState
}