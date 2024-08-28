package ru.tyurin.ip_test_task.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.tyurin.ip_test_task.data.db.dao.GadgetDao
import ru.tyurin.ip_test_task.data.db.entities.GadgetEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GadgetRepository @Inject constructor(private val gadgetDao: GadgetDao) {

    private fun getALl(): Flow<List<GadgetEntity>> {
        return gadgetDao.all()
    }
    fun searchGadgets(query: String): Flow<List<GadgetEntity>> {
        return if (query.isEmpty()) {
            getALl()
        } else {
            getALl().map { gadgets ->
                gadgets.filter { it.name.contains(query, ignoreCase = true) }
            }
        }
    }

    suspend fun editGadget(gadgetEntity: GadgetEntity) {
        return gadgetDao.editGadget(gadgetEntity)
    }

    suspend fun removeGadget(gadgetEntity: GadgetEntity) {
        return gadgetDao.removeGadget(gadgetEntity)
    }
}
