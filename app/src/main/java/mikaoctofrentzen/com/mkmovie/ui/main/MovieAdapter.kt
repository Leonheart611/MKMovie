package mikaoctofrentzen.com.mkmovie.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mikaoctofrentzen.com.mkmovie.BuildConfig
import mikaoctofrentzen.com.mkmovie.R
import mikaoctofrentzen.com.mkmovie.data.response.MovieResult
import mikaoctofrentzen.com.mkmovie.databinding.HomeCircularItemsBinding
import mikaoctofrentzen.com.mkmovie.util.inflate

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieListHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean =
            oldItem.title == oldItem.title
    }

    private val diffResult = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder =
        MovieListHolder(parent.inflate(R.layout.home_circular_items))

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        diffResult.currentList[position].let { holder.bind(it) }
    }

    override fun getItemCount(): Int = diffResult.currentList.size

    fun updateMovieList(dataMovie: MutableList<MovieResult>) {
        diffResult.submitList(dataMovie)
    }

    class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(value: MovieResult) {
            with(HomeCircularItemsBinding.bind(itemView)) {
                Glide.with(itemView).load(BuildConfig.BASE_IMG_URL + value.posterPath)
                    .into(ivPoster)
            }
        }
    }
}