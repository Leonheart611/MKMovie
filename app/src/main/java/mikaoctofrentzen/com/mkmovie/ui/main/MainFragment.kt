package mikaoctofrentzen.com.mkmovie.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.collection.arrayMapOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import mikaoctofrentzen.com.mkmovie.R
import mikaoctofrentzen.com.mkmovie.databinding.MainFragmentBinding
import mikaoctofrentzen.com.mkmovie.util.showToast

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.main_fragment) {
    private var _binding: MainFragmentBinding? = null
    private val binding by lazy { _binding!! }
    private val movieAdapter = HomeAdapter(arrayMapOf())

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getAllGenre()
        viewModel.getMovieData()
        setupView()
        setupObseverable()
    }

    private fun setupView() {
        with(binding.rvMovieList) {
            layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
            adapter = movieAdapter
        }
    }

    private fun setupObseverable() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            when (it) {
                is MainViewModel.MainViewState.SuccessGetMovie -> {
                    movieAdapter.updateMovie(it.data)
                }
                is MainViewModel.MainViewState.ErrorGetMovie -> {
                    context?.showToast(it.error)
                }
            }
        })
    }
}