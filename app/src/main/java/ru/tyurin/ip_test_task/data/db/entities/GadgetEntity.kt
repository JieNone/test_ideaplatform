package ru.tyurin.ip_test_task.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.tyurin.ip_test_task.utils.TagsConverter


@Entity(tableName = "item")
data class GadgetEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "time")
    val time: Int,
    @TypeConverters(TagsConverter::class) val tags: List<String>,
    @ColumnInfo(name = "amount")
    var amount: Int,
)