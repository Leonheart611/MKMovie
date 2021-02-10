package mikaoctofrentzen.com.mkmovie.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mikaoctofrentzen.com.mkmovie.BuildConfig
import mikaoctofrentzen.com.mkmovie.data.Result
import mikaoctofrentzen.com.mkmovie.data.response.Genre
import mikaoctofrentzen.com.mkmovie.data.response.MovieResult
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
interface MovieDataSource {
    suspend fun getDiscoveryMovie(
        apiKey: String = BuildConfig.API_KEY,
        page: Int = 1,
        genreId: Int? = null
    ): Flow<Result<MutableList<MovieResult>>>

    suspend fun getAllGenreMovie(apiKey: String = BuildConfig.API_KEY): Flow<Result<MutableList<Genre>>>
}

class MovieDataSourceImpl @Inject constructor(private val apiService: API) : MovieDataSource {

    override suspend fun getDiscoveryMovie(
        apiKey: String,
        page: Int,
        genreId: Int?
    ): Flow<Result<MutableList<MovieResult>>> =
        flow {
            try {
                apiService.getDiscoveryMovie(
                    apiKey = apiKey,
                    page = page,
                    genreId = genreId
                ).results.let {
                    emit(Result.Success(it.toMutableList()))
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }

    override suspend fun getAllGenreMovie(apiKey: String): Flow<Result<MutableList<Genre>>> =
        flow {
            try {
                apiService.getAllGenreMovie(apiKey).genres.let {
                    emit(Result.Success(it.toMutableList()))
                }
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
}