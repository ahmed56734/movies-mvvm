package io.ahmed56734.movies.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.ahmed56734.movies.data.models.SearchQuery

@Dao
interface SearchQueryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(query: SearchQuery)

    @Query("select * from search_queries")
    fun getRecentQueries(): LiveData<List<SearchQuery>>

    @Query("select count() from search_queries")
    suspend fun getCount(): Int

    @Query("delete from search_queries where searchValue in (select searchValue from search_queries order by timeStamp desc limit 1)")
    suspend fun deleteOldest()
}