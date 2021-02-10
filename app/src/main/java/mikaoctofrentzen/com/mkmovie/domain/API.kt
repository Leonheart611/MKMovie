package mikaoctofrentzen.com.mkmovie.domain

import mikaoctofrentzen.com.mkmovie.data.response.DiscoveryMovie
import mikaoctofrentzen.com.mkmovie.data.response.GenreMovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("4/discover/movie")
    suspend fun getDiscoveryMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int,
        @Query("sort_by") sort_by: String = "popularity.desc",
        @Query("with_genres") genreId: Int? = null
    ): DiscoveryMovie

    @GET("3/genre/movie/list")
    suspend fun getAllGenreMovie(@Query("api_key") apiKey: String): GenreMovieList
}