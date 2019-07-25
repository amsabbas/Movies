package com.thiqah.posts

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.thiqah.posts.core.db.ThiqahDatabase
import com.thiqah.posts.data.source.local.model.PostsDao
import com.thiqah.posts.data.source.remote.model.post.Post
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class RoomUnitTest {

    @Inject
    lateinit var database: ThiqahDatabase

    private lateinit var context: Context

    private lateinit var postDao: PostsDao

    @Before
    @Throws(Exception::class)
    fun setUp() {

        context = InstrumentationRegistry.getTargetContext()

        database = Room.inMemoryDatabaseBuilder(
            context,
            ThiqahDatabase::class.java
        ).allowMainThreadQueries().build()

        postDao = database.postsDao()
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        database.close()
    }


    @Test
    fun testDatabaseCreated() {
        assert(database.isOpen)
    }

    @Test
    fun testInsertPost() {

        val post1 = Post(1, "title1", "body1")
        val post2 = Post(2, "title2", "body2")

        val posts = ArrayList<Post>()
        posts.add(post1)
        posts.add(post2)


        postDao.insertPosts(posts)

        val observer = postDao.getAllPosts().test()

        observer.assertNoErrors()
        observer.assertValueCount(1)
        observer.assertValue { posts: List<Post> -> posts[0].title.equals(post1.title, true) }
    }

}
