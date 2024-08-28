package ru.tyurin.ip_test_task.data.repository

import kotlinx.coroutines.flow.Flow
import ru.tyurin.ip_test_task.data.db.dao.GadgetDao
import ru.tyurin.ip_test_task.data.db.entities.GadgetEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GadgetRepository @Inject constructor(private val gadgetDao: GadgetDao) {

    fun getALl(): Flow<List<GadgetEntity>> {
        return gadgetDao.all()
    }

//    suspend fun findGadget(name: String): GadgetEntity {
//        return gadgetDao.findGadget(name)
//    }

    suspend fun editGadget(gadgetEntity: GadgetEntity) {
        return gadgetDao.editGadget(gadgetEntity)
    }

    suspend fun removeGadget(gadgetEntity: GadgetEntity) {
        return gadgetDao.removeGadget(gadgetEntity)
    }
}
