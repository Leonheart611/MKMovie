package mikaoctofrentzen.com.mkmovie.ui.main

import androidx.collection.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import mikaoctofrentzen.com.mkmovie.data.Result
import mikaoctofrentzen.com.mkmovie.data.response.Genre
import mikaoctofrentzen.com.mkmovie.data.response.MovieResult
import mikaoctofrentzen.com.mkmovie.domain.MovieDataSource
import mikaoctofrentzen.com.mkmovie.util.io
import mikaoctofrentzen.com.mkmovie.util.ui
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MovieDataSource) : ViewModel() {
    val viewState: LiveData<MainViewState> by lazy { _viewState }
    private val _viewState = MutableLiveData<MainViewState>()

    private val tempData = ArrayMap<String, MutableList<MovieResult>>()
    fun getMovieData() {
        viewModelScope.launch {
            io {
                repository.getDiscoveryMovie().collect {
                    when (it) {
                        is Result.Error -> ui {
                            _viewState.value =
                                MainViewState.ErrorGetMovie(it.exception.localizedMessage)
                        }
                        Result.Loading -> {
                        }
                        is Result.Success -> ui {
                            tempData["Discover"] = it.data
                            _viewState.value =
                                MainViewState.SuccessGetMovie(tempData)
                        }
                    }
                }
            }
        }
    }

    fun getAllGenre() {
        viewModelScope.launch {
            io {
                repository.getAllGenreMovie().collect {
                    when (it) {
                        is Result.Error -> ui {
                            _viewState.value =
                                MainViewState.ErrorGetMovie(it.exception.localizedMessage)
                        }
                        Result.Loading -> {
                        }
                        is Result.Success -> {
                            getAllMovieByGenre(it.data)
                        }
                    }
                }
            }
        }
    }

    private fun getAllMovieByGenre(genres: MutableList<Genre>) {
        viewModelScope.launch {
            for (genre in genres) {
                io {
                    repository.getDiscoveryMovie(genreId = genre.id).collect {
                        when (it) {
                            is Result.Error -> ui {
                                MainViewState.ErrorGetMovie(it.exception.localizedMessage)
                            }
                            Result.Loading -> {
                            }
                            is Result.Success -> ui {
                                tempData[genre.name] = it.data
                                _viewState.value =
                                    MainViewState.SuccessGetMovie(tempData)
                            }
                        }
                    }
                }
            }
        }
    }


    sealed class MainViewState {
        class SuccessGetMovie(val data: ArrayMap<String, MutableList<MovieResult>>) :
            MainViewState()

        class ErrorGetMovie(val error: String) : MainViewState()
    }

}