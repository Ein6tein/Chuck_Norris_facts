package lv.chernishenko.chucknorrisfacts.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lv.chernishenko.chucknorrisfacts.model.ChuckNorrisFact

@Dao
interface ChuckNorrisFactsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFact(chuckNorrisFact: ChuckNorrisFact)

    @Query("SELECT * FROM facts")
    fun localFacts() : PagingSource<Int, ChuckNorrisFact>
}