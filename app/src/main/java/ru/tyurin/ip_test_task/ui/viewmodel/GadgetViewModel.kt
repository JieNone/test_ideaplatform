package ru.tyurin.ip_test_task.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.tyurin.ip_test_task.data.db.entities.GadgetEntity
import ru.tyurin.ip_test_task.data.repository.GadgetRepository
import javax.inject.Inject


@HiltViewModel
class GadgetViewModel @Inject constructor(
    private val repository: GadgetRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    val gadgets: StateFlow<List<GadgetEntity>> = _searchQuery
        .flatMapLatest { query -> repository.searchGadgets(query) }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _selectedGadgetForEdit = MutableStateFlow<GadgetEntity?>(null)
    val selectedGadgetForEdit: StateFlow<GadgetEntity?> = _selectedGadgetForEdit

    private val _selectedGadgetForDelete = MutableStateFlow<GadgetEntity?>(null)
    val selectedGadgetForDelete: StateFlow<GadgetEntity?> = _selectedGadgetForDelete

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getSearchQuery(): String = _searchQuery.value

    fun setSelectedGadgetForEdit(gadget: GadgetEntity?) {
        _selectedGadgetForEdit.value = gadget
    }

    fun setSelectedGadgetForDelete(gadget: GadgetEntity?) {
        _selectedGadgetForDelete.value = gadget
    }

    fun removeGadget(gadgetEntity: GadgetEntity) {
        viewModelScope.launch {
            repository.removeGadget(gadgetEntity)
        }
    }

    fun editGadget(gadgetEntity: GadgetEntity) {
        viewModelScope.launch {
            repository.editGadget(gadgetEntity)
        }
    }
}
