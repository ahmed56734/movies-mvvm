package io.ahmed56734.movies.data.models

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("adult")
    val adult: Boolean, // false

    @SerializedName("backdrop_path")
    val backdropPath: String, // /5A2bMlLfJrAfX9bqAibOL2gCruF.jpg

    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any?, // null

    @SerializedName("budget")
    val budget: Int, // 160000000

    @SerializedName("genres")
    val genres: List<Genre>,

    @SerializedName("homepage")
    val homepage: String, // http://www.aquamanmovie.com

    @SerializedName("id")
    val id: Int, // 297802

    @SerializedName("imdb_id")
    val imdbId: String, // tt1477834

    @SerializedName("original_language")
    val originalLanguage: String, // en

    @SerializedName("original_title")
    val originalTitle: String, // Aquaman

    @SerializedName("overview")
    val overview: String, // Arthur Curry learns that he is the heir to the underwater kingdom of Atlantis, and must step forward to lead his people and be a hero to the world.

    @SerializedName("popularity")
    val popularity: Double, // 580.411

    @SerializedName("poster_path")
    val posterPath: String, // /i2dF9UxOeb77CAJrOflj0RpqJRF.jpg

    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>,

    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry>,

    @SerializedName("release_date")
    val releaseDate: String, // 2018-12-07

    @SerializedName("revenue")
    val revenue: Int, // 846317079

    @SerializedName("runtime")
    val runtime: Int, // 143

    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>,

    @SerializedName("status")
    val status: String, // Released

    @SerializedName("tagline")
    val tagline: String, // Protector of the Deep

    @SerializedName("title")
    val title: String, // Aquaman

    @SerializedName("video")
    val video: Boolean, // false

    @SerializedName("vote_average")
    val voteAverage: Double, // 6.9

    @SerializedName("vote_count")
    val voteCount: Int // 1678
) {
    data class Genre(
        @SerializedName("id")
        val id: Int, // 12
        @SerializedName("name")
        val name: String // Adventure
    )

    data class ProductionCompany(
        @SerializedName("id")
        val id: Int, // 112611
        @SerializedName("logo_path")
        val logoPath: Any?, // null
        @SerializedName("name")
        val name: String, // Panoramic Pictures
        @SerializedName("origin_country")
        val originCountry: String // CA
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val iso31661: String, // US
        @SerializedName("name")
        val name: String // United States of America
    )

    data class SpokenLanguage(
        @SerializedName("iso_639_1")
        val iso6391: String, // en
        @SerializedName("name")
        val name: String // English
    )
}