package ru.tyurin.ip_test_task.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.tyurin.ip_test_task.data.db.entities.GadgetEntity

@Dao
interface GadgetDao {

    @Update suspend fun editGadget(gadget: GadgetEntity)
    @Delete suspend fun removeGadget(gadget: GadgetEntity)


    @Query("SELECT * FROM item")
    fun all(): Flow<List<GadgetEntity>>

    @Query("SELECT * FROM item WHERE name == :name")
    suspend fun findGadget(name: String): GadgetEntity


}