package lv.chernishenko.chucknorrisfacts.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import lv.chernishenko.chucknorrisfacts.dao.ChuckNorrisFactsDao
import lv.chernishenko.chucknorrisfacts.database.ChuckNorrisDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ChuckNorrisDatabase {
        return Room.databaseBuilder(
            context,
            ChuckNorrisDatabase::class.java,
            "chuck_norris_database"
        ).build()
    }

    @Provides
    fun provideDao(database: ChuckNorrisDatabase): ChuckNorrisFactsDao {
        return database.chuckNorrisFactsDao()
    }
}