package ru.tyurin.ip_test_task.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tyurin.ip_test_task.data.db.GadgetDatabase
import ru.tyurin.ip_test_task.data.db.dao.GadgetDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Volatile
    private var INSTANCE: GadgetDatabase? = null
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GadgetDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                GadgetDatabase::class.java,
                "item"
            )
                .createFromAsset("data.db")
                .build()
            INSTANCE = instance
            instance
        }
    }
    @Provides
    fun providesGadgetDao(database: GadgetDatabase): GadgetDao {
        return database.items()
    }
}