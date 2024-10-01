package com.example.many_to_many_tz.presentation.chosen_item

import androidx.lifecycle.ViewModel
import com.example.many_to_many_tz.data.mappers.toItem
import com.example.many_to_many_tz.domain.entities.ChosenItem
import com.example.many_to_many_tz.domain.repositories.ItemsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ChosenItemViewModel(
    private val repository: ItemsRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ChosenItemScreenState>(Initial)
    val state = _state.asStateFlow()

    suspend fun getItems(itemId: String) {
        _state.value = Initial
        try {
            val execute = repository.getItemFromId(itemId)
            if (execute.isSuccessful) {
                val body = execute.body()
                if (body != null)
                    _state.value = Downloaded(body.toItem())
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

    sealed interface ChosenItemScreenState
    data object Initial : ChosenItemScreenState
    data class Downloaded(val item: ChosenItem) :
        ChosenItemScreenState

    data object Error : ChosenItemScreenState
}