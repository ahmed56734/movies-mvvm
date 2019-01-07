package io.ahmed56734.movies.data.models

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id")
    val castId: Int, // 75

    @SerializedName("character")
    val character: String, // Professor James

    @SerializedName("credit_id")
    val creditId: String, // 5c285079c3a3683a7ad3a36e

    @SerializedName("gender")
    val gender: Int, // 2

    @SerializedName("id")
    val id: Long, // 79416

    @SerializedName("name")
    val name: String, // Robert Longstreet

    @SerializedName("order")
    val order: Int, // 33

    @SerializedName("profile_path")
    val profilePath: String // /kOPNnIzXzE2VbGlDBXLzOf2juwu.jpg
)

data class CreditsResponse(
    @SerializedName("id")
    val movieId: Long, // 297802
    @SerializedName("cast")
    val cast: List<Cast>

)