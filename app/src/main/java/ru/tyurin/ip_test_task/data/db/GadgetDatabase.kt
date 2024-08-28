package ru.tyurin.ip_test_task.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.tyurin.ip_test_task.data.db.dao.GadgetDao
import ru.tyurin.ip_test_task.data.db.entities.GadgetEntity
import ru.tyurin.ip_test_task.utils.TagsConverter

@Database(entities = [GadgetEntity::class], version = 1, exportSchema = false)
@TypeConverters(TagsConverter::class)
abstract class GadgetDatabase : RoomDatabase() {
    abstract fun items(): GadgetDao
}