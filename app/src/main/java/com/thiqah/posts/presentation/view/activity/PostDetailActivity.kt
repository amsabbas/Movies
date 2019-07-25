package com.thiqah.posts.presentation.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.thiqah.posts.R
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.item_post.tvPostTitle


class PostDetailActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
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
        val title = intent.extras.getString(TITLE)
        val desc = intent.extras.getString(DESC)
        tvPostTitle.text = title
        tvPostBody.text = desc
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {

        private const val TITLE = "title"
        private const val DESC = "desc"

        fun startActivity(context: Context, title: String, desc: String) {
            var intent = Intent(context, PostDetailActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(DESC, desc)
            context.startActivity(intent)
        }

    }
}
