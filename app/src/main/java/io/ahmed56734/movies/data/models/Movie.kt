package io.ahmed56734.movies.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movies")
@Parcelize
data class Movie(
    @PrimaryKey
    @SerializedName("id")
    val id: Long, // 297802

    @SerializedName("vote_count")
    val voteCount: Int, // 1683

    @SerializedName("video")
    val video: Boolean, // false

    @SerializedName("vote_average")
    val voteAverage: Double, // 6.9

    @SerializedName("title")
    val title: String, // Aquaman

    @SerializedName("popularity")
    val popularity: Double, // 580.411

    @SerializedName("poster_path")
    val posterPath: String, // /i2dF9UxOeb77CAJrOflj0RpqJRF.jpg


    @SerializedName("original_language")
    val originalLanguage: String, // en

    @SerializedName("original_title")
    val originalTitle: String, // Aquaman

    @SerializedName("backdrop_path")
    val backdropPath: String, // /5A2bMlLfJrAfX9bqAibOL2gCruF.jpg

    @SerializedName("adult")
    val adult: Boolean, // false

    @SerializedName("overview")
    val overview: String, // Arthur Curry learns that he is the heir to the underwater kingdom of Atlantis, and must step forward to lead his people and be a hero to the world.

    @SerializedName("release_date")
    val releaseDate: String, // 2018-12-07

    var isFavorite: Boolean = false
) : Parcelable {

    @Ignore
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null


}

class MoviesResponse(
    @SerializedName("page")
    val page: Int,

    @SerializedName("total_results")
    val totalResults: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("results")
    val movies: List<Movie>
)