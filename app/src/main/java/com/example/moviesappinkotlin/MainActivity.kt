package com.example.moviesappinkotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import retrofit2.Callback

import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var currentPageNumber = 1
    lateinit var moviesAdapter: MoviesAdapter
    lateinit var llm: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesAdapter = MoviesAdapter(mutableListOf(),this)
        llm = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        rv_movies.adapter = moviesAdapter
        rv_movies.layoutManager = llm
        getPopularMovies()
        // This is in development branch
    }

            fun getPopularMovies(){
                Log.d("Popular Movies",
            "here" )
        MoviesClient.fetchPopularMovies(
            currentPageNumber,
        ::onPopularMoviesFetched,
        ::onError)

    }

    private fun onError() {
        Toast
            .makeText(this, "Failed to fetch movies", Toast.LENGTH_SHORT)
            .show()
    }

    private fun onPopularMoviesFetched(list: MutableList<Movie>) {
        moviesAdapter.appendMovies(list)
        attachOnScrollListener()

    }

    fun attachOnScrollListener(){
        rv_movies.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItems = llm.itemCount
                val visibleItemsCount = llm.childCount
                val firstVisibleItem = llm.findLastVisibleItemPosition()

                if(firstVisibleItem + visibleItemsCount >= totalItems/2){
                    rv_movies.removeOnScrollListener(this)
                    currentPageNumber++
                    getPopularMovies()
                }
            }
        })
    }

    fun opendetails(view : View){
        var position = llm.getPosition(view)
        var movie : Movie = moviesAdapter.moviesList!![position]
        val intent = Intent(this,detailsofmovie::class.java)
        var extras=Bundle()
        extras.putString("summary",movie.overview)
        extras.putString("poster",movie.backdropPath)
        extras.putString("posterpath",movie.posterPath)
        extras.putString("title",movie.title)
        extras.putFloat("rating",movie.rating)
        extras.putString("releasedate",movie.releaseDate)
        extras.putLong("id",movie.id)
        extras.putString("summary",movie.overview)
        intent.putExtras(extras)
        startActivity(intent)
    }

}