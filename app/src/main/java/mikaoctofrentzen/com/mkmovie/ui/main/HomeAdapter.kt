package mikaoctofrentzen.com.mkmovie.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.collection.ArrayMap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mikaoctofrentzen.com.mkmovie.R
import mikaoctofrentzen.com.mkmovie.data.response.MovieResult
import mikaoctofrentzen.com.mkmovie.databinding.HomeListItemWithTitleBinding
import mikaoctofrentzen.com.mkmovie.util.inflate


class HomeAdapter(var dataHashMap: ArrayMap<String, MutableList<MovieResult>>) :
    RecyclerView.Adapter<HomeAdapter.HomeItemHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeItemHolder =
        HomeItemHolder(
            parent.inflate(
                R.layout.home_list_item_with_title
            )
        )

    override fun onBindViewHolder(holder: HomeItemHolder, position: Int) {
        dataHashMap.valueAt(position).let { data ->
            holder.bind(dataHashMap.keyAt(position), data)
        }
    }

    override fun getItemCount(): Int = dataHashMap.size


    fun updateMovie(dataMovie: ArrayMap<String, MutableList<MovieResult>>) {
        this.dataHashMap = dataMovie
        notifyDataSetChanged()
    }

    class HomeItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(title: String, data: MutableList<MovieResult>) {
            with(HomeListItemWithTitleBinding.bind(itemView)) {
                val adapterMovie = MovieAdapter()
                tvCategory.text = title
                with(rvFilmData) {
                    layoutManager =
                        LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
                    adapter = adapterMovie
                    adapterMovie.updateMovieList(data)
                }

            }
        }
    }
}
