package lv.chernishenko.chucknorrisfacts.database

import androidx.room.Database
import androidx.room.RoomDatabase
import lv.chernishenko.chucknorrisfacts.dao.ChuckNorrisFactsDao
import lv.chernishenko.chucknorrisfacts.model.ChuckNorrisFact

@Database(entities = [ChuckNorrisFact::class], version = 1, exportSchema = false)
abstract class ChuckNorrisDatabase : RoomDatabase() {

    abstract fun chuckNorrisFactsDao(): ChuckNorrisFactsDao
}