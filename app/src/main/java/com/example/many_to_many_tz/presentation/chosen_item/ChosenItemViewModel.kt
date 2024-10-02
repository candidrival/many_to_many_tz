package com.example.many_to_many_tz.presentation.chosen_item

import androidx.lifecycle.ViewModel
import com.example.many_to_many_tz.data.network.GetResultChosen
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
        when (val result = repository.getItemFromId(itemId)) {
            GetResultChosen.Failure -> _state.value = Error
            is GetResultChosen.Success -> _state.value = Downloaded(result.item)
        }
    }

    sealed interface ChosenItemScreenState
    data object Initial : ChosenItemScreenState
    data class Downloaded(val item: ChosenItem) :
        ChosenItemScreenState

    data object Error : ChosenItemScreenState
}