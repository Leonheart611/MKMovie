package mikaoctofrentzen.com.mkmovie.data.response

import com.google.gson.annotations.SerializedName


data class GenreMovieList(
    @SerializedName("genres")
    val genres: List<Genre>
)

data class Genre(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)