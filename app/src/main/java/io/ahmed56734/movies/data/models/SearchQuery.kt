package io.ahmed56734.movies.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_queries")
class SearchQuery(
    @PrimaryKey
    val searchValue: String,
    val timeStamp: Long = System.currentTimeMillis()
)