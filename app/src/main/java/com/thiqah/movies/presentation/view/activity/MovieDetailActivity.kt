package com.thiqah.movies.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thiqah.movies.R
import kotlinx.android.synthetic.main.activity_movie_detail.*


class MovieDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        init()
    }

    private fun init() {
        initToolbar()
        initPostViews()
    }

    private fun initToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPostViews() {
        val title = intent.extras?.getString(TITLE)
        val desc = intent.extras?.getString(DESC)
        tvMovieTitle.text = title
        tvMovieBody.text = desc
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {

        private const val TITLE = "title"
        private const val DESC = "desc"

        fun startActivity(context: Context, title: String, desc: String) {
            var intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(DESC, desc)
            context.startActivity(intent)
        }

    }
}
