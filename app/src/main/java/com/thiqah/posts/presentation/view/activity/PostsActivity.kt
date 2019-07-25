package com.thiqah.posts.presentation.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.thiqah.posts.R
import com.thiqah.posts.data.source.remote.model.post.Post
import com.thiqah.posts.presentation.model.ViewModelFactory
import com.thiqah.posts.presentation.view.adapter.PostsAdapter
import com.thiqah.posts.presentation.viewmodel.PostsViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_posts.*
import kotlinx.android.synthetic.main.item_add_post.*
import kotlinx.android.synthetic.main.item_edit_post.edtPostTitle
import java.util.*
import javax.inject.Inject

class PostsActivity : AppCompatActivity() {

    @Inject
    lateinit var postsAdapter: PostsAdapter

    @Inject
    lateinit var postsFactoryViewModel: ViewModelFactory<PostsViewModel>

    private val postsViewModel by lazy {
        ViewModelProviders.of(this, postsFactoryViewModel)
            .get(PostsViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_posts)
        init()
    }

    private fun init() {
        initPostsRecyclerView()
        loadPosts()
        observeOnPostClickListener()
        observeOnPostEditClickListener()
        observeOnPostDeleteClickListener()
        observeOnAddPostClickListener()
    }

    private fun initPostsRecyclerView() {
        rcPosts.adapter = postsAdapter
        rcPosts.layoutManager = LinearLayoutManager(this)
    }

    private fun observeOnPostClickListener() {
        postsAdapter.onPostClickListener.observe(this, Observer { post ->
            post?.let {
                PostDetailActivity.startActivity(this, post.title, post.body)
            }
        })
    }

    private fun observeOnAddPostClickListener() {
        fabPost.setOnClickListener {

            val dialog = MaterialDialog(this)
                .customView(R.layout.item_add_post)
            dialog.show()

            dialog.positiveButton {
                val title = dialog.edtPostTitle.text.toString()
                val description = dialog.edtPostDesc.text.toString()
                if (title.isNotEmpty())
                    postsAdapter.addPost(Post(-1, title, description))
            }
        }
    }

    private fun observeOnPostEditClickListener() {
        postsAdapter.onPostEditClickListener.observe(this, Observer { post ->
            post?.let {
                val dialog = MaterialDialog(this)
                    .customView(R.layout.item_edit_post)
                dialog.edtPostTitle.setText(post.title)
                dialog.show()

                dialog.positiveButton {
                    val title = dialog.edtPostTitle.text.toString()
                    if (title.isNotEmpty()) {
                        post.title = title
                        postsAdapter.notifyDataSetChanged()
                    }
                }

            }
        })
    }

    private fun observeOnPostDeleteClickListener() {
        postsAdapter.onPostDeleteClickListener.observe(this, Observer { post ->
            post?.let {
                postsAdapter.removePost(it)
            }
        })
    }


    private fun loadPosts() {
        postsViewModel.getPosts()
        postsViewModel.postsObservableResource.observe(this,
            successObserver = Observer {
                postsAdapter.apply {
                    this.posts = ArrayList(it)
                    notifyDataSetChanged()
                }
            },
            loadingObserver = Observer {
                it?.let {
                    if (it)
                        pbPosts.visibility = View.VISIBLE
                    else
                        pbPosts.visibility = View.GONE
                }
            },
            commonErrorObserver = Observer {
                postsViewModel.getLocalPosts()
            })

        postsViewModel.localPostsObservableResource.observe(this, Observer {
            postsAdapter.apply {
                this.posts = ArrayList(it)
                notifyDataSetChanged()
            }
        })
    }


}
