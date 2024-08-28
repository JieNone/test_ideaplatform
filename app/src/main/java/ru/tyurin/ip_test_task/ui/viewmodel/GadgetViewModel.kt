package ru.tyurin.ip_test_task.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.tyurin.ip_test_task.data.db.entities.GadgetEntity
import ru.tyurin.ip_test_task.data.repository.GadgetRepository
import javax.inject.Inject


@HiltViewModel
class GadgetViewModel @Inject constructor(
    private val repository: GadgetRepository
) : ViewModel() {

    val gadgets: LiveData<List<GadgetEntity>> = repository.getALl().asLiveData()

//    fun findGadget(name: String) {
//        viewModelScope.launch {
//            repository.findGadget(name)
//        }
//    }

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