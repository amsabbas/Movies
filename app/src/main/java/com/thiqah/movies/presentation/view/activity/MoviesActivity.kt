package com.thiqah.movies.presentation.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.thiqah.movies.R
import com.thiqah.movies.presentation.view.adapter.MoviesAdapter
import com.thiqah.movies.presentation.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_movies.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MoviesActivity : AppCompatActivity() {

    private val moviesAdapter: MoviesAdapter by inject { parametersOf(this) }

    private val moviesViewModel : MoviesViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        init()
    }

    private fun init() {
        initRecyclerView()
        loadMovies()
        observeOnMovieClickListener()
    }

    private fun initRecyclerView() {
        rcMovies.adapter = moviesAdapter
        rcMovies.layoutManager = LinearLayoutManager(this)
    }

    private fun observeOnMovieClickListener() {
        moviesAdapter.onMovieClickListener.observe(this, Observer { post ->
            post?.let {
                MovieDetailActivity.startActivity(this, post.title, post.body)
            }
        })
    }

    private fun loadMovies() {
        moviesViewModel.getMovies()
        moviesViewModel.moviesObservableResource.observe(this,
            successObserver = Observer { posts->
                moviesAdapter.apply {
                    this.movies = posts
                    notifyDataSetChanged()
                }
            },
            loadingObserver = Observer {
                it?.let {
                    if (it)
                        pbMovies.visibility = View.VISIBLE
                    else
                        pbMovies.visibility = View.GONE
                }
            },
            commonErrorObserver = Observer {
                moviesViewModel.getLocalMovies()
            })

        moviesViewModel.localMoviesObservableResource.observe(this, Observer {
            moviesAdapter.apply {
                this.movies =it
                notifyDataSetChanged()
            }
        })
    }


}
